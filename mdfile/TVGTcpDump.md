# 抓包教程

## 需要

### WireShark

sudo apt-get install wireshark

### .pcap文件

* 对电视果root
* adb shell tcpdump -i wlan0 -s 0 -w /data/1.pcap 开始抓包，内容存储在/data/1.pcap中
* 操作电视果，使电视果发生请求和响应，操作完成后ctrl+c停止抓包
* adb pull /data/1.pcap 将文件拉取出，到当前计算机目录

## 分析

* wireshark 1.pcap 打开文件内容
* 输入过滤规则
* Follow TCP/UDP Stream查看协议内容

## wireshak的使用

### 常用捕获过滤器
* tcp[13]&32==32 设置了URG位的tcp数据包
* tcp[13]&16==16 设置了ACK位的tcp数据包
* tcp[13]&8==8 设置了PSH位的tcp数据包
* tcp[13]&4==4 设置了RST位的tcp数据包
* tcp[13]&2==2 设置了SYN位的tcp数据包
* tcp[13]&1==1 设置了FIN位的tcp数据包
* tcp[13]==18 tcp syn-ack数据包
* ether host 00:00:00:00:00:00(MAC地址) 流入流出你Mac地址的流量
* !ether host 00:00:00:00:00:00(MAC地址) 不流入流出你Mac地址的流量
* broadcast 仅广播流量
* ICMP 仅ICMP流量
* ICMP[0:2] ICMP目标不可达，主机不可达
* ip 仅IPv4流量
* ip6 仅IPv6流量
* udp 仅UDP流量

### 常用显示过滤器
* !tcp.port==3389 排除RDP流量
* tcp.flags.syn==1 具有SYN标志位的TCP数据包
* tcp.flags.rst==1 具有RST标志位的TCP数据包
* !arp 排除ARP流量
* http 所有HTTP流量
* tcp.port==23 || tcp.port==21 文本管理流量（Telnet或FTP）
* smtp||pop||imap 文本email流量

## 抓包方式,可借用的第三方
手机要root,手机要支持tcpdump.
但是一般root手机也是不支持文件copy的,也就是tcpdump没法push到/system/usr/bin目录下,(个别手机上是可以的)

用GT,腾讯出的测试工具
http://gt.tencent.com/

本周工作总结:
1 熟悉smartconfig模块,验证蓝牙配置30+位Wifi密码无响应问题;
2 优化proxy server,提升开播速度.目前有一定的提升,理论上可以更快,代码并行控制还需要分析优化.
3 分析第三方dlna投屏问题.
腾讯切换VIP剧集没有继续播放问题,原因是切换后再没有收到SoapRequest消息,ServerThread一直等待接收.
音量调节连续增加问题,原因是在按物理音量键时收到了100的volume值,并不是执行了多次音量调整.