#临时文档

## Bug：镜像后投屏本地视频，点击Home键，电视果端黑屏一分多钟后恢复正常到等待投屏界面

### 场景复现

分析：
* 录制视频
* 镜像投屏
* 进入相册，播放录制视频

结果重复操作n(>10)次没有出现黑屏问题

### 另外：

相册中之前的一个视频，播放一分部后会停住，拖动后电视果端显示了黑屏

分析：

出现黑屏时日志中显示，电视果已经接收不到视频流了，说明手机没有向电视果传输视频。
停止镜像投屏，手机上播放视频，发现从电视果停住的画面开始往后，视频进度上一直在走，但没有画面，显示一片空白。

### 10月12
走到了Mirror，openH264Player成功，但是没有decode了
怀疑是queueThread死掉了

queueThread没有死掉，一直在运行，是frameQueue出问题了



## Bug：奇魔投屏到电视果，打开earphone播放中关闭earphone开关，mediacenter crash

### 分析：

* 没有复现
* 代码行亦对应不上


## Netty

通用协议或者它的实现存在一些不足，无法完成：
* 超大文件
* 超大邮件信息
* 实时信息，如财务信息，多玩家的游戏数据
需要一种具有特殊功能的协议

如何在保证稳定和表现的同时快速实现一个协议？

Netty是一个NIO客户端服务器框架，可以快捷开发一个客户端或服务器网络应用。

ChannelHandlerContext可以用来获取pipeline中的handler,使用对应的string key


## MediaCodec

MediaCodec class can be used to access low-level media codecs, i.e. encoder/decoder components. 
It is part of the Android low-level multimedia support infrastructure (normally used together with MediaExtractor, MediaSync, MediaMuxer, MediaCrypto, MediaDrm, Image, Surface, and AudioTrack.)

MediaCodec 用来访问底层的媒体编解码器，例如编码/解码元素。
是Android底层多媒体支持基础的一部分，通常与其他的类一起用，如MediaExtractor, MediaSync, MediaMuxer, MediaCrypto, MediaDrm, Image, Surface, AudioTrack。

In broad terms, a codec processes input data to generate output data. 
It processes data asynchronously and uses a set of input and output buffers. 
At a simplistic level, you request (or receive) an empty input buffer, fill it up with data and send it to the codec for processing. 
The codec uses up the data and transforms it into one of its empty output buffers. 
Finally, you request (or receive) a filled output buffer, consume its contents and release it back to the codec.

SDK Version
* 从16开始，使用MediaCodec
* 18 通过 Surface 提供输入的方法，允许输入来自于相机的预览或者是经过OpenGL ES呈现，
MediaMuxer 允许将AVC编解码器（原始H.264基本流）的输出转换为.MP4​​格式，可以和音频流一起转码也可以单独转换
* 21 异步模式，允许应用程序提供一个回调方法，在缓冲区可用时执行

* 电视果支持到19
如何让19之前的Android可以安装电视果，可运行AirPlay服务，给提示版本太低不支持镜像。

结论：
* 目前电视果要安装到Android4.0系统上（API15），只能降低API版本。在app安装时提示解析包时出现错误。
* Android4.0系统上不支持MediaCodec，发现项目中的AirPlay的镜像功能，EarPhone的密听功能需要MediaCodec来实现。
* 在功能被触发时，判断当前系统版本号，低于16的弹窗提醒用户盒子的系统版本过低，高于16（包含16）的正常工作。
* 如果降到15还会有其他问题吗。对其他功能模块是否有影响？

MediaCodec使用
数据类型:压缩过的数据,原始的音频数据,原始的视频数据.这三类数据都可以被ByteBuffers处理,原始的视频数据使用Surface可以有更好的表现.
Surface使用native的视频缓冲,效率更高.

压缩过的缓冲
输入输出的缓冲区包含的压缩数据与格式类型有关,对于视频:单个压缩过的视频帧;对音频:单个可存取单元(包含通过格式类型记录的几秒钟的音频),通常一个缓冲区包含多个加密的存取单元;
缓冲区的开始和结束都是以帧或者存取单元为界,但是带BUFFER_FLAG_PARTIAL_FRAME标志的是例外.



IPv4网络地址

32位地址，点分四组
分四组原因：
IP地址包含网络地址和主机地址，网络地址和主机地址的划分由子网掩码决定。
子网掩码：32位地址，与IP地址对应，设为１的IP属于网络地址，设为0的属于主机地址
网络地址：表示设备所连接到的局域网
主机地址：这个网络中的设备本身

IP
经过一次路由TTL减一

周报-2017.10.20

本周工作总结:
1 学习Netty框架,掌握了用来实现Client和Server的方法;
2 解决电视果安装到Android4.0系统的盒子上问题,结论:
    1) 要安装到Android4.0上,只能降低minSDKVersion=15;
    2) API低于16的Android没有MediaCodec相关类,不能支持镜像,因此在镜像连接时判断当前的API版本,低于16关闭镜像;
3 看问题TVGUOBUG-2592,正在进行中.

下周工作计划:
1 解决问题TVGUOBUG-2592;
2 熟悉代码细节.


Bug镜像模式下,iphone5s相册的推片拉伸.

分析:

1 解码出错,一帧的长度太大了
2 状态机的控制出错,跑出了illegalStateException
3 img400143有问题,但是,变了最新的MediaCenter安装后,没有问题出现,说明问题已经修复了

debug占用:

1 没有开启
内存占用14.9M
延迟时间16ms

2 开启
内存占用17.16M
延迟时间40ms


镜像,画面延迟的时间,需要计算网络延迟时间,时钟同步,画面的延时时间
计算网络延迟

周报-2017.10.27

本周工作总结

1 遗留的问题TVGUOBUG-2592和TVGUOBUG-2433的分析,照片拉长问题在改分辨率的时候已经解决;密听关闭造成crash的问题无法重现.
2 调研AirPlay镜像运行时log输出对画面延时的影响,发现有log打印比没有log打印的延迟时间大22ms.尝试对这部分log做动态控制.
    数据:
    
    没有log,直接注释掉代码          有log                           优化后
    次数	延迟时间				序号	延迟时间				序号	延迟时间
    1	        0.18				1	        0.18				1	        0.16
    2	        0.19				2	        0.19				2	        0.19
    3	        0.16				3	        0.19				3	        0.18
    4	        0.14				4	        0.18				4	        0.13
    5	        0.14				5	        0.16				5	        0.13
    6	        0.14				6	        0.17				6	        0.15
    7	        0.15				7	        0.14				7	        0.15
    8	        0.13				8	        0.19				8	        0.14
    9	        0.13				9	        0.16				9	        0.16
    10	        0.18				10	        0.2				    10	        0.17
    均值	    0.154				均值	    0.176				均值        0.156

    优化后可以提升20ms的画面延时.
    
3 Android投屏时网络延时/时钟同步/画面延时等数据的计算,已经完成网络延时的计算,下一步调研始终同步和画面延时的计算.



下周工作计划:
1 Android投屏时的始终同步/画面延时计算.
2 熟悉Proxy Server模块代码逻辑.


## 字节数组
编码解码
LTV-length tag value

一个字节8位,可表示一位十六进制数字.
        
        byte a = (byte) 0x1;

## 镜像投屏的画面延时调研
Android投屏时网络耗时/时钟同步/画面延时等数据的计算,已经完成网络延时的计算,下一步调研始终同步和画面延时的计算.

公式:画面延迟时间=接收时间-时钟差值-发送时间-网络耗时

网络往返时间,时钟差值都计算出来,最后异步计算画面延迟遇到一个问题,

发送时间放在消息的header中未使用的部分中,但是header部分在视频解码前就被丢弃了,导致发送时间无法到达视频解码后数据入队的那一刻.

现在是将网络耗时的单次和20次均值作为log打印出来了,之后调研如何计算出画面延时.

## 本地投屏

VideoModule-proxyOfflineQsvIfNeed中启动Proxy-ProxyChannel
write(HttpResponse)

ProxyRequestHandler-channelOpen中启动RemoteChannel
ProxyRequestHandler-接收播放器的文件下载请求,是否有缓存 ? 读取缓存, write(response) : 通过RemoteChannel请求数据, outboundChannel.write(request);

RemoteResponseHandler-接收remote的数据,写到缓存,并且通过mInboundChannel(ProxyChannel).write(response);返回响应到播放器

VideoModule-onMediaPlayed中启动DownloadChannel
writeFile(buffer)

##

本周工作总结:

1 镜像投屏的画面延时调研,公式:画面延迟时间=接收时间-时钟差值-发送时间-网络耗时
  结论:网络往返时间,时钟差值都计算出来,最后异步计算画面延迟遇到一个问题,发送时间放在消息的header中未使用的部分中,但是header部分在视频解码前就被丢弃了,导致发送时间无法到达视频解码后数据入队的那一刻.现在是将网络耗时的单次和20次均值作为log打印出来了,之后调研如何计算出画面延时.
2 dlna-sdk增加pcab字段
3 熟悉Proxy Server模块代码逻辑.

下周工作计划:

1 Proxy Server模块
