# 进行中的任务

## ControlPoint

设备添加/移除
devices-add/remove/get

deviceChangeListener-add/remove/perform

notifyReceived
通知监听的添加/移除-监听UDP消息SSDP Packet
addNotifyListener
removeNotifyListener
performNotifyListener-notifyReceived

设备发现响应监听的添加/移除
addSearchResponseListener
removeSearchResponseListener
performSearchResponseListener-searchResponseReceived

发现消息
search

事件监听的添加/移除-监听http
eventListener-add/remove/perform-httpRequestReceived

订阅
subscribe
resubscribe
unSubscribe
renewSubscriber

入口和出口
start-准备工作
stop-销毁工作

## MediaServer-是个什么角色?

对外提供标准服务接口?

initialize
finalize

文件目录
ContentDirectory-add/remove

连接管理
ConnectionManager-add/remove

getInterfaceAddress
setInterfaceAddress

addPlugin
initPlugin

httpRequestReceived

setServerRootDir
getServerRootDir

startWebServer
stopWebServer

getWorkingState
setWorkingState

入口和出口
start
restart
stop

文档上说有三个服务:ContentDirectory/ConnectionManager/AvTransport,但是AVTransport没看到有实例化啊?


## MediaRender

初始化,网络初始化,服务初始化
initialize

服务
ConnectionManager-get
RenderingControl-get
AVTransport-get
PrivateService-get
QPlayService-get

监听
StandardDLNAListener-get/set -标准的接口
QiyiDLNAListener-get/set -默认接口
LastChangeListener-get -变量的通知接口
ActionListener-get/set -SOAP协议接口
QPlayListener-get/set -自定义接口

入口和出口
start
stop


## QimoHelper入口和出口
start
stop

NotifyMessage-电视果向手机发送通知消息的方法

createMediaRender

MediaRender(Device)

QimoMessageListener-自定义消息接口
事件接收,事件处理,事件分发
QimoQuicklyMessageListener-自定义快速消息接口
QimoConnectionListener-设备连接的接口
建立连接并且连接数大于1时回调
断开连接并且连接数为0时回调
QimoCallbackListener-自定义的奇摩业务接口

DLNACallbackListener-标准DLNA的业务接口
QPlayCallbackListener-qq音乐的业务接口
AvTransportListener-标准DLNA的业务接口

本周工作总结:

1 学习Proxy Server实现,将返回数据的逻辑由串行改为并行.待验证方案的可行和结果.
2 修复搜狐投屏失败问题.
  目前AirPlay投屏存在一些控制问题.如播放/暂停失效,获取不到播放位置.小米盒子也没有实现这些控制.
3 学习总结DLNA标准服务的处理流程.

下周工作计划:

1 Proxy Server给出结论和方案.
2 学习总结投屏的处理流程.

## 心跳包

分为header和data:

### 首先介绍header的个字节的作用
header-128字节

0~3字节,存放data的length

4字节是flag位
取值和意义如下:

0-video消息
1-需要设置解码信息的video消息
2-audio消息
3-心跳消息

剩余的header都空着.

### 介绍data个字节的作用:
0~3字节存放data中内容的长度,剩余的字节存放内容.

单独介绍心跳包
0~1字节存放tag值,表示心跳包携带数据的作用.
目前0x01表示这个心跳包用来传输网络延迟/时钟差值的.
当tag为0x01时,2~5字节存放心跳包的send time,6~9存放心跳包的网络耗时,10~13存放时钟差值.


## Failed register mDNS services
问题:局域网设备较多时,adb kill mediacenter导致Failed register mDNS services.

        
        11-16 16:18:40.448 W/System.err(13945): java.lang.IllegalStateException: waitForAnnounced failure 1.
        11-16 16:18:40.448 W/System.err(13945): 	at javax.jmdns.impl.JmDNSImpl.registerService(JmDNSImpl.java:1050)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvguo.airplay.AirReceiver.start(AirReceiver.java:409)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.multiscreen.airplay.AirplayService.startAirplayService(AirplayService.java:83)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.multiscreen.service.MultiScreenService.startMultiScreenServices(MultiScreenService.java:207)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.mediacenter.MediaCenterServiceStateMachine$MediaCenterInitiatingState.processMessage(MediaCenterServiceStateMachine.java:158)
        11-16 16:18:40.448 W/System.err(13945): 	at com.android.util.StateMachine$SmHandler.processMsg(StateMachine.java:684)
        11-16 16:18:40.448 W/System.err(13945): 	at com.android.util.StateMachine$SmHandler.handleMessage(StateMachine.java:507)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.Handler.dispatchMessage(Handler.java:110)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.Looper.loop(Looper.java:193)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.HandlerThread.run(HandlerThread.java:61)

分析:
start后调用JmDNSImpl的registerService,在方法中等待announced状态,并且设置了等待的超时时间,发生超时将跑出上面的异常.等待前打印状态是probing,在超时异常时的状态是probing/announcing中的一个.
判断为该环境下需要更大的超时时间才能到announced状态.

后面将超时时间改为了0,问题必现,验证了问题.
解决:将等待的超时时间从原来的6000ms增大到15000ms.

## mDNS


## 删除源
删除一个PPA源
1,到源的 目 录:cd  /etc/apt/sources.list.d/
2,可以看 到 关 于 源的 文件,删除即可 .

## NetworkInterface

包含一个名字和一个IP地址列表
用来标识组播组加入的local interface
pointer to pointer interface-PPP connection throw a modem.
 virtual interface 也是 subinterface 一个物理interface可以绑定多个虚拟的interface
 
 ## InetAddress
 包含一个IP address和他的host name
 未指定地址:0.0.0.0,例如作为绑定的目标,允许server可在任意一个interface接受client的连接.一定不能作为目的IP使用

## Android Deep Link

Android web uri intent的处理:
1 打开一个可以处理这个uri的指定app
2 打开唯一一个可以处理这个uri的app
3 从dialog中选择一个app可以处理

创建步骤：
在manifest添加intent-filter：
<action>=ACTION_VIEW
<data>=android:scheme
<category>=BROWSABLE&DEFAULT

同一个intent-filter可有多个data，但可能会造成混合。如第一个的sheme和第二个的host混合成一个不想要的uri。
最好写成多个intent-filter

## RTSP
实时流协议，流媒体控制协议。RTSP基于TCP协议。

控制命令：
SETUP rtsp://.... RTSP/1.0
PLAY rtsp://... RTSP/1.0
PAUSE rtsp://... RTSP/1.0
AUNNOUNCE
RECORD
TEARDOWN
GET_PARAMeter
SET_PARAMETER

## iOS video

# android gradle:

compile & provide

compile 将内容打包到apk内
provide 编译时使用，但是不会打包到apk中


