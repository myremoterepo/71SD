WebSocket on Android

总结：

两种方案，okhttp，Java-WebSocket
okhttp里可以写client，可以用mockwebserver模拟server测试用。okhttp不能写server。
https://github.com/square/okhttp
Java-WebSocket
可以写Client和Server
https://github.com/TooTallNate/Java-WebSocket

参考 
1 https://stackoverflow.com/questions/28099656/websocket-server-on-android
2 https://medium.com/@ssaurel/learn-to-use-websockets-on-android-with-okhttp-ba5f00aea988
3 http://www.websocket.org/echo.html (从[2]文章中找来的),一个js的demo，可以通过浏览器发送websocket消息