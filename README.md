# 运行环境
**可在所有主要操作系统上运行，并且仅需要安装Java JDK或JRE版本8或更高版本。要检查，请运行java -version：**
```bash
$ java -version
java version "1.8.0_121"
```

**！！！：1.0.3+版本增加window64绿色版本，再也不用配置安装java环境了**<br/><br/><br/>
**Bootstrap4 放弃了对 IE8 以及 iOS 6 的支持，现在仅仅支持 IE9 以上 以及 iOS 7 以上版本的浏览器。**<br/>
**以最新版本浏览器示例:**
<table>
    <thead>
        <tr>
            <td></td>
            <th>Chrome</th>
            <th>Firefox</th>
            <th>Internet Explorer</th>
            <th>Microsoft Edge</th>
            <th>Opera</th>
            <th>Safari</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th>Mac</th>
            <td>支持</td>
            <td>支持</td>
            <td>N/A</td>
            <td>N/A</td>
            <td>支持</td>
            <td>支持</td>
        </tr>
        <tr>
            <th>Windows</th>
            <td>支持</td>
            <td>支持</td>
            <td>支持</td>
            <td>支持</td>
            <td>支持</td>
            <td>不支持</td>
        </tr>
        <tr>
            <th>Linux</th>
            <td>支持</td>
            <td>支持</td>
            <td>N/A</td>
            <td>N/A</td>
            <td>支持</td>
            <td>支持</td>
        </tr>
    </tbody>
</table>

# 简介

**Bilibili Live Barrage Kyi in Springboot,基于Springboot的Bilibili直播弹幕姬(使用websocket协议)，java版B站弹幕姬.**<br/>
**本软件基于控制台 项目控制台运行，弹幕在控制台显示(也可以在网页查看弹幕).**<br/>
**本软件基于web设置 必须在浏览器上设置.**<br/>
**关于绿色版本：使用为开源的openjdk8 jre。[官网](https://jdk.java.net/java-se-ri/8-MR3 "官网")**

## 现有功能
- **弹幕显示**
1. 舰爷房管勋章ul的设置显示
2. 禁言欢迎信息显示
3. 礼物显示
4. 新关注显示
- **弹幕保存本地**
- **网页弹幕显示**
- **扫码登录**
- **自动获取弹幕长度及颜色**
- **发送弹幕长度超出上限自动分条发送**
- **礼物感谢**
1. **延迟感谢(可统计延时内赠送的礼物[包括礼物数量]合并感谢)！注意：延迟设置过高礼物持续赠送可能会造成刷屏**
2. 是否仅在直播中开启
3. **4种感谢屏蔽模式(1自定义礼物名称，2屏蔽所有免费礼物，3低价值礼物，4自定义规则)**
4. 可屏蔽天选时刻下的礼物
5. **3种礼物感谢模式(单人单种，单人多种，多人多种)**
6. 可调节每次感谢人数，礼物数
7. 触发直播间有人上舰长后发送私信(用于发送舰长群)
8. 感谢舰队和留言
- **关注感谢**
1. 是否仅在直播中开启
2. 可调节每次感谢人数
- **定时发送弹幕(用于发送广告)**
1. 是否仅在直播中开启
2. 可调节时间
3. 目前两种模式 随机和按顺序发送
- **上舰私信**
1. 发送私信
2. 并可以设置发送私信成功后直播间发送提醒弹幕
3. 选择可不可以重复发送，即舰长信息保存本地(注意该功能仅统计弹幕姬开启时候的舰长，本地文件夹为guardFile.txt；格式为 uid,uname 亦可以按按照格式手动添加，注意不要为空)
- **模拟在线(老爷可增加在线经验)**
- **断线自动重连(实验性)**
<br/><br/>
# 如何运行<br/>
### 发布版本(环境配置好的前提下）<br/>
项目目录下控制台执行：
```bash
$ java -jar BiliBili_Danmuji-1.0.2beta.jar
```
如果Window系统还可以：<br/>
运行目录下run.bat<br/>
### 1.0.3+新增win64绿色版本（不用配置环境)<br/>
解压java-se-8u41-ri.zip（注意解压后不要里面还是java-se-8u41-ri名字的文件夹）<br/>
解压完成确定文件结构没问题直接运行run.bat就行啦（win64下）记得允许网络<br/>
其他方法：解压完成 直接在本目录打开控制台 或者 控制台cd本目录命令执行java-se-8u41-ri\bin\java -jar  BiliBili_Danmuji-1.0.3beta.jar 即可运行<br/><br/>
### 注意<br/>
- **首次运行会在项目目录下生成 log日志文件夹 DanmujiProfile配置文件**<br/>
- **关于DanmujiProfile配置文件，配置信息和用户cookie已加密存放在此，注意不要泄露给任何人**<br/>
- **本地运行浏览器输入localhost:23333(主页地址) 打开配置页面 远程请输入：远程主机ip:23333**<br/>
- **首次开启日志 会在项目目录下生成Danmuji_log文件夹 用来存放弹幕**<br/>
- **生成的用于存放弹幕文件名命名规则为 当天时间+房间号**<br/>
- **localhost:23333/connect 连接房间地址**<br/>
- **localhost:23333/login 扫码登录地址**<br/><br/>

## 项目提示或教程
- **光标悬浮在相应位置出现提示**<br/><br/>
![提示](https://images.acproject.xyz/dm_tips.gif "提示")

## 项目UI
- **主页(未登录)**<br/><br/>
![主页(未登录)](https://images.acproject.xyz/dm_index.PNG "主页(未登录)")
- **连接房间**<br/><br/>
![连接房间](https://images.acproject.xyz/dm_connect.PNG "连接房间")
- **登录**<br/><br/>
![连接房间](https://images.acproject.xyz/dm_login.PNG "登录")
- **主页设置示例(未登录)**<br/><br/>
![主页设置示例(未登录)](https://images.acproject.xyz/dm_setn.PNG "主页设置示例(未登录)")
- **主页弹幕显示(已登录)**<br/><br/>
![主页弹幕显示(已登录)](https://images.acproject.xyz/dm_danmu.PNG "主页弹幕显示(已登录)")
- **主页设置示例(已登录)**<br/><br/>
![主页设置示例(已登录)](https://images.acproject.xyz/dm_sety.PNG "主页设置示例(已登录)")

## 开源

**基于MIT License开源协议.**

## 项目线上实战展示（用户名：坂崎简）

[破站坂本叔直播间](https://live.bilibili.com/1000 "破站坂本叔直播间")<br/><br/>
**本项目已在坂本叔直播间经过2年多的风吹雨打 最近才想起来开源,请放心使用**<br/>
**如果您使用了本软件，最好能在破站私聊我吱一下呢**
# 版本更新<br/>
**版本更新直接删除旧版本jar包 把新版本jar包复制进去就行 run.bat同样**
# 版本详细<br/>
<blockquote>
<h2>Beta1.0.6(大约7月31号下午发布)</h2>
<p>修改了部分title提示，更加易懂？(luo suo)</p>
<p>增加了上舰后舰长信息保存本地的选项用于判断重复发送私信 只统计uid uname(注意：这个功能只统计弹幕姬开启时间的上舰，详细看上面教程)</p>
<p>增加了保存最后一次连接房间的房间(下次打开就不用麻烦输入直播间了)</p>
</blockquote>
<blockquote>
<h2>Beta1.0.5</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>加强关注姬的逻辑</p>
<p>改变勋章等级显示ul</p>
</blockquote>
<blockquote>
<h2>Beta1.0.4</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>已知bug：</p>
<p>用户排名低于50000时登录失败，从而导致软件启动不了</p>
</blockquote>
<blockquote>
<h2>Beta1.0.3</h2>
<p>修复了若干bug(ke neng zhi zao le geng duo bug)</p>
<p>新功能：</p>
<p>增加了感谢关注姬感谢人数上限，以防止感谢刷屏 和 有效屏蔽天选时刻下的感谢关注</p>
<p>留言加入感谢  格式嵌套进入感谢格式里面 礼物名称为 second秒醒目留言，屏蔽建议：60秒醒目留言，替换秒数屏蔽相应秒数的留言感谢</p>
 <p>已知bug：</p>
 <p>当送礼用户名字为房间关键字k，发送的感谢语超过弹幕长度上线，切割时候不会发送第一条，但是会发送第二条(删掉测试代码时候不小心把它删了)，在1.0.4以后版本得到解决</p>
</blockquote>
<blockquote>
<h2>Beta1.0.2</h2>
<p>首发公开版本发出</p>
<p>已知bug：</p>
<p>自定义屏蔽礼物名称当只有一个时候只能后面加中文逗号才能保存设置，在1.0.3以后版本得到解决</p>
<p>自定义规则不显示提示，在1.0.3以后版本得到解决</p>
<p>多人多种礼物感谢模式当单人多种礼物时并不会合并多种礼物，在1.0.3以后版本得到解决</p>
<p>重要问题：弹幕姬登录后，在不开启广告姬的情况下保存设置会出现致命错误，导致弹幕姬开启不了，在1.0.3以后版本得到解决</p>
<p></p>
</blockquote>

