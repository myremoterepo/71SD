#编译电视果

## 需要
###设置android环境变量
1 动态设置android环境变量,每次关闭终端后重新设置

export ANDROID_HOME=~/android-sdk-linux
export PATH=$ANDROID_HOME/tools:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH

2 对登录用户设置android环境变量

* vi /home/fzf/.profile
* 在文档最后加入export PATH="$PATH:/home/fzf/androiddevtools/adt-bundle-linux-x86_64-20140702/sdk/tools"
* 重启系统使生效

###脚本
ant build脚本和apk签名脚本

## 操作
1 cd workspace_repo/MediaCenter/

2 ./gen_antbuild.sh 运行build脚本

3 ant clean release 使用ant构建apk

4 cd bin/

5 /home/fzf/remote_sign_apk.sh QiyiMediaCenter-release-unsigned.apk 签名成功后生成Signed_20171011160133_QiyiMediaCenter-release-unsigned.apk

6 mv Signed_20171011160133_QiyiMediaCenter-release-unsigned.apk QiyiMediaCenter.apk 重命名

7 adb push QiyiMediaCenter.apk /system/app/ 将apk文件推到电视果上，这一步需要电视果的root权限；/system/app/目录下时电视果上运行的所有app。
 