# SSDP的过程

## QimoHelper-createMediaRenderer

在MediaRender构造时添加所有的服务
DLNA标准服务:
RenderingControl
ConnectionManager
AVTransport
奇摩服务
PrivateService
QPlay服务
QPlayService(this);

MediaRender是Device的子类.

Device里面有announce,byebye,searchResponse消息,发现消息的宣告/离线/搜索响应

发现消息的内容Device-DeviceData-SSDPPacket

## 电视果notify消息
Device-...-HTTPMUSocket-两个消息广播和组播
广播-239.255.255.250(1900)
组播-255.255.255.255(39390)

## 手机和电视果都有一个HTTPServer

这两个HTTPServer是奇摩协议进行交互的途径

json-字符串-soap封装-http封装(tcp)

解析也是一样的过程

