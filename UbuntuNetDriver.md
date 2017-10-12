# 网卡驱动
1 下载相应的驱动程序压缩包 e1000xxxxx.tar.gz

2 解压包文件 tar zxvf e1000xxxxx.tar.gz

3 切换目录 cd e1000xxxxx/src

4 编译make install 

5 加载模块 modprobe e1000

6 修改文件 /etc/modprobe.d/alias, 添加 alias eth0 e1000 alias eth1 e1000

7 重启