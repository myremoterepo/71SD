# 搜狐DLNA和AirPlay

## DLNA

### 分析
从log看到手机先向电视果发送了一条SOAP Request,之后电视果向手机发送了一条SOAP Response,至此就没有了后文.
安装了搜狐6.8.7,发现这个过程会有多次,action是不同的.

怀疑电视果的response没有被手机(搜狐app)正确处理,这种情况的原因就是搜狐app改动了业务处理逻辑,应该调整response让搜狐app可以正确处理.

在小米盒子上是好的,抓取小米盒子和手机上的request和reponse过程,模拟响应和请求过程.

需要小米盒子,root手机.

搜狐投屏到小米盒子时,抓取小米盒子的收的request和发出的response.

### 搜狐投屏小米盒子抓包对比
对比发现,第一条SOAP Request的Response不同
这是一条连接协议信息的请求,ConnectionManager,getProtocolInfo.按照Device(httpPostRequestRecieved-soapActionRecieved-deviceControlRequestRecieved-deviceActionReceived)-Action(performActionListener)-ConnectionManager(actionControlReceived)
逻辑,一直到匹配到getProtocolInfo,在这里生成Response.
电视果在Response中,给sourceProtocol赋值no_implement;小米的这个字段是空的.

### 修改后,问题解决


## AirPlay

抓包
分析到,推送时电视果收到了来自搜狐的/play请求,但是没有响应,猜测在airplay的实现中没有处理这种请求.

定位到代码VideoHandler,在messageReceived中匹配uri,匹配到/play则调用playReceived,在这个方法判断了请求的header内容,没有处理到搜狐发过来的请求header.
增加判断条件,匹配搜狐的请求header内容,获取到请求的内容,后面的逻辑就通了.