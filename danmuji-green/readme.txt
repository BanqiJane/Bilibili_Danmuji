该版本为window绿色版本，
运行方法：
直接运行run.bat（window下）记得允许网络
其他系统或方法：解压完成 直接在本目录打开控制台 或者 控制台cd本目录命令执行jdk8u282-b08-jre\bin\java -jar -Xms64m -Xmx128m  BiliBili_Danmuji-$(steps.prepare.outputs.file_name).jar --server.port=23333 即可运行
--server.port=23333为端口 想修改端口可以修改run.bat里面的这个参数
如何设置？打开浏览器地址栏输入 http://127.0.0.1:23333进行设置；
如何退出？直接关闭命令行窗口即可退出
如何获取更新？建议持续关注github项目官页
不会使用？有问题反馈？ GitHub上开个issue提问 或者 主页 点击联系我 邮件发送
