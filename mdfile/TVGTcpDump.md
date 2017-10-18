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

### 常用过滤规则

1 