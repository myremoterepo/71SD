# 蓝牙通信

## 发现
蓝牙要两个角色,一个client,一个server.client负责扫描远程蓝牙设备并显示.server负责广播自身.

## client
定义ScanFilter,扫描的filter.
定义Scanner,封装扫描的行为.
scan,并监听扫描回调
展示ScanResult

## Server
定义data,消息的内容.
定义BlueLeAdvertiser,封装广播的行为.
广播,并监听广播回调

在局部区域同时有client扫描和server广播才能发现蓝牙设备.

## use GATT transmit data
发现device
直接连接,并监听回调
处理事件,连接,断开连接,发现GATT_SERVICES,接收到数据

