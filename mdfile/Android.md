# Android Deep Link

Android web uri intent的处理:
1 打开一个可以处理这个uri的指定app
2 打开唯一一个可以处理这个uri的app
3 从dialog中选择一个app可以处理

创建步骤：
在manifest添加intent-filter：
<action>=ACTION_VIEW
<data>=android:scheme
<category>=BROWSABLE&DEFAULT

同一个intent-filter可有多个data，但可能会造成混合。如第一个的sheme和第二个的host混合成一个不想要的uri。
最好写成多个intent-filter

# android gradle:

添加依赖：
compile 将内容打包到apk内
provide 编译时使用，但是不会打包到apk中