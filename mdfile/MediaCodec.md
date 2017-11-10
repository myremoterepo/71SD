# MediaCodec调研

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

