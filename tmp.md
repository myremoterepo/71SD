#临时文档 进行中的任务

## Bug镜像模式下,iphone5s相册的推片拉伸

1 解码出错,一帧的长度太大了
2 状态机的控制出错,跑出了illegalStateException
3 img400143有问题,但是,变了最新的MediaCenter安装后,没有问题出现,说明问题已经修复了

## 本地投屏

VideoModule-proxyOfflineQsvIfNeed中启动Proxy-ProxyChannel
write(HttpResponse)

ProxyRequestHandler-channelOpen中启动RemoteChannel
ProxyRequestHandler-接收播放器的文件下载请求,是否有缓存 ? 读取缓存, write(response) : 通过RemoteChannel请求数据, outboundChannel.write(request);

RemoteResponseHandler-接收remote的数据,写到缓存,并且通过mInboundChannel(ProxyChannel).write(response);返回响应到播放器

VideoModule-onMediaPlayed中启动DownloadChannel
writeFile(buffer)


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
