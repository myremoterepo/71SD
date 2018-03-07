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


# 本地播放视频，seek到很大的位置，电视过开播很慢。

        12-14 15:23:18.298 I/[ProxyServer] DownloadResponseHandler( 9798): httpChunkReceived contentLength=8192
        12-14 15:23:18.298 I/[ProxyServer] DownloadResponseHandler( 9798): Content not cached.
        12-14 15:23:18.298 I/[ProxyServer] CacheBlockList( 9798): start write....8665987
        12-14 15:23:18.298 I/[ProxyServer] CacheBlockList( 9798): end write....8665987
        12-14 15:23:18.298 I/[ProxyServer] RemoteResponseHandler( 9798): HttpChunk len=4344
        12-14 15:23:18.298 I/[ProxyServer] DownloadResponseHandler( 9798): httpChunkReceived contentLength=3392
        12-14 15:23:18.298 I/[ProxyServer] CacheBlockList( 9798): start write....8665987
        
        12-14 15:23:52.408 I/[ProxyServer] CacheBlockList( 9798): end write....1340795581
        12-14 15:23:52.408 I/[ProxyServer] DownloadResponseHandler( 9798): Content not cached.
        12-14 15:23:52.408 I/[ProxyServer] CacheBlockList( 9798): start write....1340795581
        12-14 15:23:52.408 I/[ProxyServer] CacheBlockList( 9798): end write....1340795581

卡在了mFile.write(content)方法上，RandomAccessFile的写入操作，一直没有写成功。

1 将DownloadChannel的逻辑注掉，问题还在，说明与同步无关。
2 如果写到一个每次都新new的文件中，没有问题，说明接收的内容无关，与文件操作有关,之后抓包分析后也确认了返回没有问题。
3 把mFile.write(content)之前的mFile.seek(start)注掉，没有问题,说明与RandomAccessFile的seek操作有关。
4 写demo，开两个线程执行mFile.seek(start) mFile.write(content)，没有出现问题，耗时几/几十毫秒，说明通常情况下先seek再write并不会卡住很久。
5 用FileChannel方式写文件，问题还在，卡在map(FileChannel.MapMode.READ_WRITE, start, content);
6 用单独线程执行mFile.write(content)，问题还在，卡在enqueue的锁上，因为runnable里面的mFile.write(content)会卡住，占着锁。
7 在startSeek和endSeek关闭和打开文件写操作，问题还在，说明是seek结束之后的行为产生的问题。

Proxy模块的总结

最后得到了结果。
接到结果的依据是3代可以播放而4代播放有问题，我对设置缓存路的代码印象还蛮深的，知道一个是/cache目录，另一个是/sdcard目录。
改变缓存目录后，就不会再卡顿了。
这跟/sdcard的文件格式有关系，cache是linux系统文件格式ext，而sdcard是windows支持的fat32格式， ext文件文件读写快过fat32。

# Jmdns
https://github.com/jmdns/jmdns/issues/96
加log 打印serviceinfo的内容
service name ip 唯一性。

JmDNS 名字自动加1

1 git上源码issues里面的相似问题，有一个检查service name和ip唯一的解决思路，但是没有验证。
2 读注解，看到在执行registerService方法时能会更改名字。简单的注释掉更改名字的逻辑会不会有其他问题，要验证？
3 DNS 与一个interface绑定？有一个DNS Cache存放映射信息。

# Platium编译和运行

1 platium sdk下载[URL](https://sourceforge.net/projects/platinum/?source=typ_redirect),有两个文件Neptune和Platium，都需要。Neptune在后面编译so文件时需要。 
2 下载ndk，配置环境变量ANDROID_NDK_ROOT，ndk的安装目录
3 编辑vim Build/Targets/arm-android-linux/Config.scons，修改ANDROID_TOOLCHAIN的版本号，和ndk的toolchain版本号要对应上。另外要在
```
ANDROID_HOST_SYSTEM = linux-x86
ANDROID_TOOLCHAIN   = arm-linux-androideabi-4.4.3
ANDROID_PLATFORM    = android-9
ANDROID_ARCH        = arm
```
再加一行
```
ANDROID_HOST_SYSTEM = 'linux-x86_64'
```
不然会报错 找不到arm-linux-androideabi++
4 scons target=arm-android-linux build_config=Release
5 cd Platinum/Source/Platform/Android/module/platinum/jni
  ndk-build NDK_DEBUG=0
6 Platinum\Source\Platform\Android 目录下的两个工程导入 Eclipse
7 将编译生成的so手动copy到module工程libs/armeabi/目录下
8 参考文章[1](http://blog.csdn.net/zhbpd/article/details/50299961)
  [2](http://blog.csdn.net/lancees/article/details/8789678)
  
# android gradle:

compile & provide

compile 将内容打包到apk内
provide 编译时使用，但是不会打包到apk中
