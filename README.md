# 运行环境
**可在所有主要操作系统上运行，并且仅需要安装Java JDK或JRE版本8或更高版本。要检查，请运行java -version：**
```bash
$ java -version
java version "1.8.0_121"
```
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
    </tbody>
</table>

# 简介

**Bilibili Live Barrage Kyi in Springboot,基于Springboot的Bilibili直播弹幕姬(使用websocket协议)，java版B站弹幕姬.**<br/>
**本软件基于控制台 项目控制台运行，弹幕在控制台显示(也可以在网页查看弹幕).**<br/>
**本软件基于web设置 必须在浏览器上设置.**

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
- **礼物感谢**
1. 延迟感谢
2. 是否仅在直播中开启
3. 4种感谢屏蔽模式
4. 可屏蔽天选下的礼物
5. 3种礼物感谢模式
6. 可调节每次感谢人数，礼物数
7. 触发直播间有人上舰长后发送私信(用于发送舰长群)
- **关注感谢**
1. 是否仅在直播中开启
2. 可调节每次感谢人数
3. 是否仅在直播中开启
- **定时发送(用于发送广告)**
1. 是否仅在直播中开启
2. 可调节时间
3. 目前两种模式 随机和按顺序发送
- **上舰私信**
1. 发送私信
2. 并可以设置发送私信发送成功弹幕
- **模拟在线**

## 后续功能

- **优化上舰私信**
- **留言加入感谢**

## 如何运行(环境配置好的前提下)
#### 发布版本
项目目录下控制台执行：
```bash
$ java -jar BiliBili_Danmuji-1.0.2beta.jar
```
Window下：<br/>
双击执行项目目录下run.bat
#### 注意
- **首次运行会在项目目录下生成 log日志文件夹 DanmujiProfile配置文件**<br/>
- **本地运行浏览器输入localhost:23333(主页地址) 打开配置页面 远程请输入：远程主机ip:23333**<br/>
- **首次开启日志 会在项目目录下生成Danmuji_log文件夹 用来存放弹幕**<br/>
- **生成的用于存放弹幕文件名命名规则为 当天时间+房间号**<br/>
- **localhost:23333/connect 连接房间地址**<br/>
- **localhost:23333/login 扫码登录地址**<br/>

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
