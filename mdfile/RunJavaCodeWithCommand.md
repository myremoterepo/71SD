# 命令行运行Java代码

## 需要引用第三方的jar
1 编译：
* javac -cp ../workspace_myrepo/LearnNetty/app/libs/netty-3.10.1.Final.jar -d ../workspace_class/ *.java 
* netty-3.10.1.Final.jar是需要的第三方jar包，多个jar包用：分割
* -d 指定class文件的目录

2 运行:
* java -cp ../workspace_myrepo/LearnNetty/app/libs/netty-3.10.1.Final.jar:../workspace_class/ EchoServer