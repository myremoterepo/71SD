# AirPlay镜像投屏问题

## 黑屏

### Bug
镜像后投屏本地视频，点击Home键，电视果端黑屏一分多钟后恢复正常到等待投屏界面
### 分析
1 分析问题log。
* 看输出日志 adb logcat -v time | tee log.txt
* 看视频内容 adb shell setprop sys.airplay.debug [任意的字符串] 设置airplay的debug属性，会将镜像视频写到sd卡的dump.pcap文件内,dump.pcap就是电视果接收到的视频内容。
2 对比正常情况下的log日志
* adb logcat -v time | tee log.txt
* 解码时，正常情况下计算一帧的长度，输出到队列中；当计算的长度时负值时，退出了镜像没事；当计算的长度非常大（相对于正常时的长度），出现黑屏。
* 问题就是解码时计算长度出错了，与解码相关的问题在AirPlay的实现里做不了，可想办法规避。
### 解决
根据分析，提前判断解码时视频计算的长度，正常18999之内的五位数，因此加判断条件当长度超过了99999，就认为解码出错，退出镜像。
