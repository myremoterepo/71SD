# Log输出

## 正常输出日志

adb logcat -v time | tee log.txt

## 设置属性，获取协议请求和返回内容日志

* adb shell setprop log.tag.DLNA D 电视果端会开启debug日志输出，打印请求和返回的内容
* adb shell setprop sys.airplay.debug [任意的字符串] 设置airplay的debug属性，会将镜像视频写到sd卡的dump.pcap文件内,dump.pcap就是电视果接收到的视频内容。
* ffplay dump.pcap 用ffplay直接播放/sdcard/dum.pcap。

## ffplay的安装

sudo add-apt-repository ppa:kirillshkrogalev/ffmpeg-next
sudo apt-get update
sudo apt-get install ffmpeg
