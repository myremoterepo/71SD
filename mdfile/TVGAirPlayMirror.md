# AirPlay镜像

## 镜像是怎么触发开始的？
* MirrorChannelMonitor-channelOpen// 开启信道
* MirrorKeyDecryptor-prepare // 启动镜像模式，解码相关，触发MirrorOutputQueue开始
* MirrorOutputQueue-start // 开启队列线程
* MirrorOutputQueue.EnQueuer-run // 先检查frameQueue，队列为空则将当前线程加入等待池，当前线程休眠；frameQueue有内容或者接收到数据，取到buffer，写入player
* 在run方法中，while循环读取frameQueue中的数据写入Player

## frameQueue中的数据怎么来的？
* MirrorKeyDecryptor-messageReceived-streamReceived //接收到请求，从请求获取流数据，添加AirplayH264FrameDecoder
* AirplayH264FrameDecoder-decode //解析数据成数据帧，回调给MirrorOutputQueue
* MirrorOutputQueue-enqueue //将数据帧添加到frameQueue，并且唤醒休眠中的线程
* frameQueue.add

## 流程

服务启动后，信道开启，开始监听messageReceived的触发，当接收到视频流时触发播放。

## 镜像数据流

* 首先是MirrorFrameDecoder,将数据的整合成MirrorPack(去掉header,将header携带的信息放到payloadLen和payloadType,将data放到payload中);
* 之后数据流入MirrorDataDecryptor,根据payloadType,将数据分类处理:给MirrorH264FrameDecoder;心跳包处理;其他处理
* 最后,视频数据流入MirrorH264FrameDecoder中,接收到的数据只有payload了,解码