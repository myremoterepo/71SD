## Failed register mDNS services
问题:局域网设备较多时,adb kill mediacenter导致Failed register mDNS services.

        
        11-16 16:18:40.448 W/System.err(13945): java.lang.IllegalStateException: waitForAnnounced failure 1.
        11-16 16:18:40.448 W/System.err(13945): 	at javax.jmdns.impl.JmDNSImpl.registerService(JmDNSImpl.java:1050)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvguo.airplay.AirReceiver.start(AirReceiver.java:409)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.multiscreen.airplay.AirplayService.startAirplayService(AirplayService.java:83)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.multiscreen.service.MultiScreenService.startMultiScreenServices(MultiScreenService.java:207)
        11-16 16:18:40.448 W/System.err(13945): 	at com.tvos.mediacenter.MediaCenterServiceStateMachine$MediaCenterInitiatingState.processMessage(MediaCenterServiceStateMachine.java:158)
        11-16 16:18:40.448 W/System.err(13945): 	at com.android.util.StateMachine$SmHandler.processMsg(StateMachine.java:684)
        11-16 16:18:40.448 W/System.err(13945): 	at com.android.util.StateMachine$SmHandler.handleMessage(StateMachine.java:507)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.Handler.dispatchMessage(Handler.java:110)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.Looper.loop(Looper.java:193)
        11-16 16:18:40.449 W/System.err(13945): 	at android.os.HandlerThread.run(HandlerThread.java:61)

分析:
start后调用JmDNSImpl的registerService,在方法中等待announced状态,并且设置了等待的超时时间,发生超时将跑出上面的异常.等待前打印状态是probing,在超时异常时的状态是probing/announcing中的一个.
判断为该环境下需要更大的超时时间才能到announced状态.

后面将超时时间改为了0,问题必现,验证了问题.
解决:将等待的超时时间从原来的6000ms增大到15000ms.