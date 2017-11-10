# Bug：镜像后投屏本地视频，点击Home键，电视果端黑屏一分多钟后恢复正常到等待投屏界面

## 场景复现

分析：
* 录制视频
* 镜像投屏
* 进入相册，播放录制视频

结果重复操作n(>10)次没有出现黑屏问题

## 另外：

相册中之前的一个视频，播放一分部后会停住，拖动后电视果端显示了黑屏

分析：

出现黑屏时日志中显示，电视果已经接收不到视频流了，说明手机没有向电视果传输视频。
停止镜像投屏，手机上播放视频，发现从电视果停住的画面开始往后，视频进度上一直在走，但没有画面，显示一片空白。

## 10月12
走到了Mirror，openH264Player成功，但是没有decode了
怀疑是queueThread死掉了

queueThread没有死掉，一直在运行，是frameQueue出问题了

## 解决

从log中分析到,黑屏时MirrorH264FrameDecoder: H264 frame length的值会变得非常大或者直接时一个负值,
当值很大时,程序判断视频数据长度一直达不到这个帧长，不输出到队列，视频黑屏,
这是用错误的AESKey解码造成的,说明AESKey的计算有问题,在这做了一个workaround,当这个值的大小异常时退出镜像,避免等待黑屏的现象出现.