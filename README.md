[![Github](https://img.shields.io/github/stars/BanqiJane/Bilibili_Danmuji?logo=github&style=flat)](https://github.com/BanqiJane/Bilibili_Danmuji)
[![GitHub release](https://img.shields.io/github/v/release/BanqiJane/Bilibili_Danmuji.svg)](https://github.com/BanqiJane/Bilibili_Danmuji/releases)
[![License](https://img.shields.io/badge/License-GPL--3.0-green.svg)](https://opensource.org/licenses/GPL-3.0)


# 运行环境

**可在所有主要操作系统上运行，并且仅需要安装Java JDK或JRE版本8或更高版本。要检查，请运行java -version：**

```bash
$ java -version
java version "1.8.0_121"
```

## 项目操作基于WebUI 即是浏览器网页操作
## 关于浏览器网页操作面板
**Bootstrap5 放弃了对 IE 的支持。**<br/>
**以最新版本浏览器示例:**
<table class="table">
  <thead>
    <tr>
      <th scope="col"></th>
      <th scope="col">Chrome</th>
      <th scope="col">Firefox</th>
      <th scope="col">Microsoft Edge</th>
      <th scope="col">Opera</th>
      <th scope="col">Safari</th>
    </tr>
  </thead>
  <tbody>
  <tr>
      <th scope="row">Linux</th>
      <td>支持</td>
      <td>支持</td>
      <td class="text-muted">—</td>
      <td class="text-muted">—</td>
     <td class="text-muted">—</td>
    </tr>
    <tr>
      <th scope="row">Mac</th>
      <td>支持</td>
      <td>支持</td>
      <td>支持</td>
      <td>支持</td>
      <td>支持</td>
    </tr>
    <tr>
      <th scope="row">Windows</th>
      <td>支持</td>
      <td>支持</td>
      <td>支持</td>
      <td>支持</td>
      <td class="text-muted">—</td>
    </tr>
  </tbody>
</table>

# 版本2.7.0.5

- **danmuji(常规版本 需要java环境) [地址](https://github.com/BanqiJane/Bilibili_Danmuji/releases/tag/2.7.0.5 "下载地址")**<br/><br/>
- **danmuji-green(
  window64绿色版本，不用配置安装java环境) [地址](https://github.com/BanqiJane/Bilibili_Danmuji/releases/tag/2.7.0.5 "下载地址")**<br/><br/>
- ~~**danmuji-docker(docker版本,由B站用户[西凉君君](https://live.bilibili.com/101937 "B站主页地址")提供
  在此感谢) [地址](https://registry.hub.docker.com/r/xilianghe/danmuji "地址")**~~<br/><br/>
- **danmuji-docker(全框架docker镜像构建版本,由github用户[zzcabc](https://github.com/zzcabc "GitHub主页")提供
  在此感谢) 1.[docker地址](https://hub.docker.com/repository/docker/zzcabc/danmuji "地址") 2.[github项目页面](https://github.com/zzcabc/Docker_Buildx_Danmuji "github项目页面")**<br/><br/>
- ~~**danmuji-heroku(heroku一键部署,警告:目前测试阶段) [地址](https://github.com/BanqiJane/BiliBili_Danmuji_Heroku "地址")**~~<br/><br/>

# 简介

**Bilibili Live Barrage Kyi in Springboot,基于Springboot的Bilibili直播弹幕姬(使用websocket协议)，java版B站弹幕姬.**<br/>
**本软件基于控制台 项目控制台运行，弹幕在控制台显示(也可以在网页查看弹幕).**<br/>
**本软件基于web设置 必须在浏览器上设置.**<br/>
**关于绿色版本：使用为开源的openjdk8。1.[官网](https://jdk.java.net/java-se-ri/8-MR3 "官网")
2.[红帽安装版官网](https://developers.redhat.com/products/openjdk/download?sc_cid=701f2000000RWTnAAO "红帽openjdk")**

## 现有功能

- **弹幕显示**

1. 舰爷房管勋章ul的设置显示
2. 禁言欢迎信息显示
3. 礼物显示
4. 新关注显示

- **弹幕保存本地**
- **网页弹幕显示**
- **扫码登录**
- **cookie登录**
- **自动获取弹幕长度及颜色**
- **发送弹幕长度超出上限自动分条发送**
- **礼物感谢**
- **黑名单过滤屏蔽**

1. **延迟感谢(可统计延时内赠送的礼物[包括礼物数量]合并感谢,一旦延时内有新礼物或数量变动就会重新刷新延时)！**
   **注意：现在破站能发重复弹幕的时间貌似为3秒 为了避免大多数感谢弹幕消失，通常建议这个设置为3秒以上 还有延迟设置过高礼物持续赠送可能会造成刷屏**
2. 是否仅在直播中开启
3. **4种感谢屏蔽模式(1自定义礼物名称，2屏蔽所有免费礼物，3低价值礼物，4自定义规则)** 自定义礼物名称可白名单或黑名单
4. 可屏蔽天选时刻下的礼物
5. **3种礼物感谢模式(单人单种，单人多种，~~多人多种~~)**
6. 可调节每次感谢人数，礼物数
7. 触发直播间有人上舰长后发送私信(用于发送舰长群)
8. 感谢舰队和留言和红包
9. 可选择是否输出礼物数量
10. 可设置多条感谢弹幕模板
11. 人员过滤屏蔽（本房间勋章，不限本房间的全部舰长）

- **关注实时感谢(1.0.6+变为真正的实时感谢)**

1. 是否仅在直播中开启
2. 可调节每次感谢人数
3. 可屏蔽天选时刻下的关注
4. **延迟感谢(可统计延时内关注,一旦延时内有新关注就会重新刷新延时)！**
5. 可设置多条感谢弹幕模板

- **进入直播间欢迎感谢姬**

1. 是否仅在直播中开启
2. 可调节每次感谢人数
3. 可屏蔽天选时刻下的感谢
4. **延迟感谢(可统计延时内关注,一旦延时内有新进入直播间观众就会重新刷新延时)！**
5. 可设置多条感谢弹幕模板
6. 人员过滤屏蔽（本房间勋章，不限本房间的全部舰长）

- **定时发送弹幕(用于发送广告)**

1. 是否仅在直播中开启
2. 可调节时间
3. 目前两种模式 随机和按顺序发送

- **上舰私信**

1. 发送私信
2. 并可以设置发送私信成功后直播间发送提醒弹幕
3. 选择可不可以重复发送，即舰长信息保存本地，打开此设置需要重启弹幕姬，读取现有舰长数量，读取过程可能引起弹幕姬连接房间较慢<br/>
   (注意该功能仅统计弹幕姬开启时候的舰长，本地文件夹为guardFile，文件格式为guards（房间号）；格式为 uid,uname 亦可以按按照格式自己手动修改文件添加，注意不要为空)<br/>
4. 礼品码模式 可以发送礼品码（规则：回车换行为分割不同礼品码 礼品码发送出去会自动删除 请用户刷新页面查看最新礼品码列表 礼品码参数为%giftCode%）
5. 礼品码要细致区分为 舰长 提督 总督 就必须设置指定开头 例如礼品码为456 要赠送给提督 那就`提督-456` 以此类推） 什么开头都没有表示可以赠送全部舰长等级
6. 私信姬合计参数:
   - 用户名称：`%uName%`
   - 舰长等级名称：`%guardLevel%`
   - 礼品码：`%giftCode%`

- **自动回复姬**

1. 是否仅在直播中开启
2. 可调节多少秒间隔才能识别弹幕并自动回复
3. 可设置多个关键字与屏蔽词，以及多条随机回复
4. 人员过滤屏蔽（本房间勋章，本房间舰长）
5. 基础回复替换参数：
   - 提问人的用户名称（触发回复姬的人）：`%NAME%`
   - 实时获取关注数：`%FANS%`
   - 实时获取多少人观看过：`%WATHER%`
   - 实时获取直播间点赞数：`%LIKE%`
   - 当前直播时长：`%LIVETIME%`
   - 当前人气值：`%HOT%`
   - 北京时间：`%TIME%`
   - 推荐天气：`%WEATHER%`
6. 含关键字封禁功能 提供 `%BLOCK%` 参数表示当前是一个封禁功能和 `{{time}}` 参数表示封禁的时长单位小时 如果超过720或低于1 则默认为1小时 不设置也默认为1小时 如果有弹幕在里面 禁言成功则发送弹幕,失败则不发送 例如 `%BLOCK%{{1}}` 表示禁言一小时;
7. 可设置精确匹配
8. 关键字可有满足条件A或条件B 发送内容C的功能(该功能多关键字也能用)
9. 天气接口：
   + **↓教程↓**
   + 固定参数：提问弹幕里面必须为`@或#号开头`+`时间`+`中文的"天气"`两字(实际种没有双引号)结尾 
   + 中间的时间取数为：`昨天，明天，后天，后两天(大后天)，后三天，后四天，后五天，后六天`
   + 提问弹幕示例： `#广州天气` 或者 `#广州天气` 或者 `#广州后天天气` 或者 `#广州昨天天气` ;
   + 推荐关键字配置：  `#||@，天气`
   + **↓配置↓**
   + 推荐配置参数（懒人）：`%WEATHER%`
   + 城市名称：`%W_CITY%`
   + 时间（yyyy年MM月dd日）：`%W_DATE%`
   + 星期几：`%W_WEEK%`
   + 湿度：`%SHIDU%` (仅当天有效)
   + 温度：`%WENDU%` (仅当天有效)
   + 最低温度：`%L_WENDU%`
   + 最高温度：`%H_WENDU%`
   + 温度区间：`%WENDU_RANGE%`
   + 风向：`%W_FX%`
   + 白天风向：`%W_FX_D%`
   + 晚上风向：`%W_FX_N%`
   + 天气类型：`%W_TYPE%`
   + 白天天气类型：`%W_TYPE_D%`
   + 晚上天气类型：`%W_TYPE_N%`
   + 风力：`%W_FL%`
   + 白天风力：`%W_FL_D%`
   + 晚上风力：`%W_FL_N%`
   + 日出：`%RICHU%`
   + 日落：`%RILUO%`
   + 小提示：`%W_TIP%` (仅当天有效)
   + 小提示-洗车：`%W_TIP_XICHE%` (仅当天有效)
   + 小提示-出游：`%W_TIP_CHUYOU%` (仅当天有效)
   + 小提示-化妆：`%W_TIP_HUAZHUANG%` (仅当天有效)
   + 小提示-穿衣：`%W_TIP_CHUANYI%` (仅当天有效)
   + 小提示-感冒：`%W_TIP_GANMAO%` (仅当天有效)
10. apex英雄数据接口:
    - 轮换（即复制器）（天）：`%MAKER_DAY1% `
    - 轮换（天）：`%MAKER_DAY2%`
    - 轮换（周）：`%MAKER_WEEK1%`
    - 轮换（周）：`%MAKER_WEEK2%`  推荐4个轮换一起用
    - 赛季通行证结束时间（yyyy年MM月dd日HH时mm分ss秒）：`%PASS_END% `
    - 商店刷新时间（yyyy年MM月dd日HH时mm分ss秒）：`%SHOP_REFRESH%`
    - PC排位大逃杀当前轮换地图：`%PW_RP_NOWMAP%`
    - PC排位大逃杀下一轮或者上一轮地图（根据赛季返回，需自行判断赛季）：`%PW_RP_OTHERMAP%`
    - PC排位大逃杀结束时间（yyyy年MM月dd日HH时mm分ss秒）：`%PW_RP_ENDTIME%`
    - PC排位竞技场当前轮换地图：`%PW_AP_NOWMAP%`
    - PC排位竞技场下一轮地图：`%PW_AP_NEXMAP%`
    - PC排位竞技场结束时间：`%PW_AP_ENDTIME%`
    - PC匹配大逃杀当前地图：`%PP_RP_NOWMAP%`
    - PC匹配大逃杀下一轮地图：`%PP_RP_NEXMAP%`
    - PC匹配大逃杀结束时间（yyyy年MM月dd日HH时mm分ss秒）：`%PP_RP_ENDTIME%`
    - PC匹配竞技场当前地图：`%PP_AP_NOWMAP%`
    - PC匹配竞技场下一轮地图：`%PP_AP_NEXMAP%`
    - PC匹配竞技场结束时间（yyyy年MM月dd日HH时mm分ss秒）：`%PP_AP_ENDTIME%`
    - PC大逃杀猎杀底分：`%PC_RP_DFEN%`
    - PC大逃杀大师总数：`%PC_RP_MTOTAL%`
    - PC竞技场猎杀底分：`%PC_AP_DFEN%`
    - PC竞技场大师总数：`%PC_AP_MTOTAL%`
    - PS4大逃杀猎杀底分：`%PS4_RP_DFEN%`
    - PS4大逃杀大师总数：`%PS4_RP_MTOTAL%`
    - PS4竞技场猎杀底分：`%PS4_AP_DFEN%`
    - PS4竞技场大师总数：`%PS4_AP_MTOTAL%`

- **礼物自动赠送姬**

1. 自定义每日赠送时间
2. 多个房间赠送(注意：多房间以中文逗号隔开，尽量用短号,这样可以减少请求)

- **黑名单姬**

1. 可供 感谢礼物姬 欢迎姬 感谢关注姬 自动回复姬 使用
2. 根据用户uid和模糊名称 组合单独多个屏蔽

- **网页高级弹幕显示**

1. 可以手动禁言
2. 可以查看用户个人空间
3. 包含房间管理 可以撤销禁言

- **关键字禁言(内嵌于自动回复姬里)**
- **房间管理(可以撤销禁言)**
- **模拟在线(老爷可增加在线经验)**
- **断线自动重连(实验性)**
- ~~**在线小心心(实验性)**~~
- **直播自动签到(实验性)**
- **每日打卡-每日按现有勋章列表完成首日+100亲密度打卡(实验性)**
- **弹幕姬所发出的任意弹幕 都可以完成首日+100亲密度任务**
- **手动修改cookie(最低参数:bili_jct和SESSDATA)**
- **设置导入导出**

<br/><br/>

# 如何运行<br/>

### 发布版本(java环境配置好的前提下）<br/>

项目目录下控制台执行：

```bash
$ java -jar BiliBili_Danmuji-2.7.0.0beta.jar
```

如果Window系统还可以：<br/>
运行目录下run.bat<br/>

### 1.0.3+新增win64绿色版本（不用配置环境)<br/>

~~解压java-se-8u41-ri.zip（注意解压后不要里面还是java-se-8u41-ri名字的文件夹）~~<br/>
~~解压完成确定文件结构没问题直接运行run.bat就行啦（win64下）记得允许网络~~<br/>
~~其他方法：解压完成 直接在本目录打开控制台 或者 控制台cd本目录命令执行java-se-8u41-ri\bin\java -jar BiliBili_Danmuji-2.0beta.jar 即可运行~~<br/>
弹幕姬从2.4.4绿色版本开始更换java环境包 可能造成兼容问题 有兼容问题请到上面官网自行下载8以上的java版本<br/>
新版绿色版直接运行run.bat文件就可以开启弹幕姬拉（更小的jre环境包）<br/><br/>

### 运行完后就可以打开浏览器 地址栏输入http://127.0.0.1:23333 打开设置页面进行设置 <br/>**或者点击这里[设置](http://127.0.0.1:23333 "设置")**<br/><br/>

### 注意<br/>

- **首次运行会在项目目录下生成 log日志文件夹 DanmujiProfile配置文件**<br/>
- **关于DanmujiProfile配置文件，配置信息和用户cookie已加密存放在此，注意不要泄露给任何人**<br/>
- **本地运行浏览器输入localhost:23333(主页地址) 打开配置页面 远程请输入：远程主机ip:23333**<br/>
- **首次开启本地 会在项目目录下生成Danmuji_log文件夹 用来存放弹幕**<br/>
- **首次开启舰长保存本地 会在项目目录下生成guardFile文件夹 用来存放舰长uid和名称**<br/>
- **生成的用于存放弹幕文件名命名规则为 当天时间+房间号**<br/>
- **localhost:23333/connect 连接房间地址**<br/>
- **localhost:23333/cookie_set 自定义cookie地址**<br/>
- **localhost:23333/login 扫码登录地址**<br/><br/>
  
# 关于修改端口<br/>

- **自从2.4.5增加说明文件readme.txt和启动配置run.bat的端口参数 旧版本也可以自己加上去**<br/>
- **--server.port=23333 修改即可修改端口启动**<br/>
- **完整命令行：java -jar BiliBili_Danmuji-2.7.0.0beta.jar --server.port=23333**<br/>
- **以上方法均兼容旧版本**
  <br/><br/>
  <br/>

# 关于内存过大(限制内存)<br/>

- **自从2.4.7增加说明文件readme.txt和启动配置run.bat的内存参数 旧版本也可以自己加上去**<br/>
- **-Xms64m -Xmx128m**<br/>
- **完整命令行：java -jar -Xms64m -Xmx128m BiliBili_Danmuji-2.7.0.0beta.jar**<br/>
- **加上修改端口的完整命令行：java -jar -Xms64m -Xmx128m BiliBili_Danmuji-2.7.0.0beta.jar --server.port=23333**<br/>
- **以上方法均兼容旧版本**
  <br/><br/>
  <br/>

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

**基于GPL-3.0 License开源协议.**

## 项目线上实战展示<br/><br/>

## [破站-Elysian绿豆直播间](https://live.bilibili.com/26445 "破站ELysian绿豆直播间-豆子哥")(用户名:Elysian绿豆)<br/><br/>

## [破站-坂本叔直播间](https://live.bilibili.com/1000 "破站坂本叔直播间") (用户名:坂崎简)<br/><br/>

## [破站-西凉君君直播间](https://live.bilibili.com/101937 "破站西凉君君直播间") (用户名:西凉君君)<br/><br/>

**本项目已在坂本叔直播间经过2年多的风吹雨打 最近才想起来开源,请放心使用**<br/>
**如果您使用了本软件，最好能在破站私聊我吱一下呢**

# **我的另一个弹幕姬项目**

**[BiliLiveChat](https://github.com/BanqiJane/BiliLiveChat "BiliLiveChat")**

# 版本更新<br/>

**版本更新直接删除旧版本jar包 把新版本jar包复制进去就行 run.bat同样**

# 版本详细<br/>

<blockquote>
<blockquote>
<h2>Beta2.7.0.5</h2>
<p>修复弹幕姬欢迎不了舰长问题</p>
<p>欢迎姬欢迎舰长模式现在变成仅欢迎本房间舰长了</p>
<p>更换公告地址</p>
</blockquote>
<blockquote>
<h2>Beta2.7.0.4</h2>
<p>可以保存弹幕网页连接websocket地址了</p> 
<p>广告姬添加随机时间</p> 
<p>修复禁言不生效的问题</p> 
<p>修复部分新直播间关注欢迎等看不到的问题</p> 
<p>修改部分说明</p> 
<p>修复部分已知bug</p>
</blockquote>
<blockquote>
<h2>Beta2.7.0.0</h2>
<p>小改弹幕姬控制面板ui</p>
<p>添加mac系统打开和unix系系统弹幕姬启动打开浏览器控制面板</p>
<p>自动回复新增屏蔽自己的选项</p>  
<p>稍微调整一下弹幕姬的自动回复姬自动保存逻辑</p>  
<p>修改扫码登录接口为破站最新</p>  
<p>修复部分已知bug</p>
</blockquote>
      <blockquote>
<h2>Beta2.6.5</h2>
<p>未登录带*号问题待解决</p>
<p>修改网页弹幕为弹窗模式</p>
<p>自动回复新增屏蔽自己的选项</p>  
<p>新增红包时期的关注 欢迎屏蔽</p>  
<p>自动回复新增多条随机回复</p>
<p>现在网页弹幕可以查看进入直播间消息了</p>
<p>修复部分已知bug</p>
</blockquote>
    <blockquote>
<h2>Beta2.6.41</h2>
<p>临时修复未登录状态下用户名带*号的问题（破站目前灰度未登录用户隐私功能）</p>
<p>尝试使网页端弹幕置底问题更符合使用</p>
<p>改用协议3 brotli解压</p>    
<p>修复部分已知bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.6.4</h2>
<p>更改部分文字提示</p>
<p>修复黑名单姬bug，黑名单姬添加全局包含模式</p>
<p>添加https至wss适应</p>
<p>增加web操作面板与弹幕姬版本检测</p>
<p>禁言窗口和禁言列表窗口修改为自适应，进一步适配手机端</p>
<p>导出设置json文件 添加时间戳命名</p>
<p>日志文件添加时间分割，错误日志现在不会启动清空了</p>
<p>修复部分已知bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.6.3</h2>
 <p>新增黑名单姬</p>
 <p>统一文件编码（utf8），更改文件权限</p>
<p>显示模式新增弹幕显示开关和免费礼物显示开关</p>
 <p>网页弹幕部分样式修改 修复网页弹幕偶尔不会置底的问题（alpha）</p>
 <p>优化部分显示模式的ui，修复自动回复显示不全的ui问题</p>
 <p>更换公网ip获取接口</p>
 <p>新增开发调试模式 TEST_MODE</p>
<p>修复部分已知bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.6.2</h2>
 <p>礼物感谢姬新增自定义白名单黑名单切换 新增人员过滤</p>
 <p>回复姬 欢迎姬 增加人员过滤</p>
<p>新增私信姬参数 区分每个舰长等级不同礼品码</p>
<p>修复部分已知bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.6.1</h2>
 <p>回复姬天气接口回归</p>
 <p>回复姬添加新参数 点赞数和观看人数</p>
 <p>感谢姬和控制台增加红包打印和红包感谢</p>
<p>修复部分已知bug（弹幕分行问题）</p>
</blockquote>
  <blockquote>
<h2>Beta2.6.0</h2>
 <p>新增apex接口</p>
 <p>新增配置实时生效</p>
  <p>优化操作界面操作（适配手机端）</p>
  <p>移除小心心和部分不实用功能</p>
  <p>修复部分已知bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.5.0</h2>
 <p>修复自动送礼</p>
 <p>新增隐私模式</p>
  <p>新增网页配置文件下载</p>
<p>修复部分bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.4.9</h2>
 <p>修复每日勋章打卡</p>
 <p>新增勋章弹幕屏蔽</p>
<p>修复部分bug</p>
</blockquote>
  <blockquote>
<h2>Beta2.4.8</h2>
<p>修复部分bug</p>
</blockquote>
<blockquote>
<h2>Beta2.4.7</h2>
<h3>新增：</h3>
<p>1.现在支持手动修改cookie了(cookie说明请看github上说明)</p>
<p>2.支持手动修改每日打卡时间，每日自动签到时间(精确到分)</p>
<p>3.添加操作页面的toast提示 例如公告和版本更新的右下角toast提示</p>
<p>4.添加了window下弹幕姬启动是否自动打开设置页面的选项</p>
<p>5.添加每日定时礼物自动赠送(现在仅支持辣条小心心亿圆，按过期时间排序赠送)</p>
<p>6.推荐使用类似端口修改的内存启动参数 -Xms64m -Xmx128m </p>
<h3>修复(优化)：</h3>
<p>1.修复小心心偶尔出错bug(忘删请求房间信息，导致每次小心心都会去请求房间信息)</p>
<p>2.移除保存配置即是检查打卡和检查签到的设定,修复主播下播会自动打卡的bug</p>
<p>3.继续修复自动回复姬获取北京时间格式错误的问题</p>
<p>4.修复礼物感谢姬自定义礼物规则可能会出现重复礼物的问题？</p>
<p>5.修复禁言窗口关不了...</p>
<p>6.修复扫码登录去请求一遍公告导致弹幕姬扫码登录很慢（2.4.6特有）</p>
<h3>更新预告：</h3>
<p>还是麦片哥的问题 正考虑加个自动封禁</p>
</blockquote>
<blockquote>
<h2>Beta2.4.6</h2>
<p>增加弹幕姬登录验证(以暗号的形式 初始默认密码为123)</p>
<p>修改弹幕姬部分提示UI</p>
 <p>适配破站更新 金瓜子变成电池 相应的倍数也会改变</p>
 <p>修复弹幕姬服务器依赖bug</p>
</blockquote>
 <blockquote>
<h2>Beta2.4.5</h2>
<p>修复定时任务bug 该bug曾经导致重复执行定时任务(这次是真的修复了（)</p>
<p>
添加说明文件readme.txt和启动配置run.bat的端口参数<br>
--server.port=23333 修改即可修改端口启动<br>
亦或者cmd:java -jar BiliBili_Danmuji-2.4.5beta.jar --server.port=23333</p>
<p>修改弹幕姬小心心功能的提示UI</p>
 <p>重连姬添加在错误的情况下也会触发重连弹幕服务器的机制</p>
 <p>修复诺干问题</p>
</blockquote>
<blockquote>
<h2>Beta2.4.4</h2>
 <p>修复定时任务bug 该bug曾经导致重复执行定时任务</p>
  <p>私聊姬新增礼品码模式</p>
<p>新增自动欢迎姬(功能与关注姬一样)</p>
 <p>新增房间管理 （房管才能看）用于撤销禁言</p>
 <p>修复诺干问题</p>
</blockquote>
    <blockquote>
<h2>Beta2.4.3</h2>
<p>修复每日打卡bug 该bug曾经导致会向连接房间发送打卡弹幕</p>
 <p>修复定时任务bug 该bug曾经导致重复执行定时任务</p>
</blockquote>
<blockquote>
<h2>Beta2.4.2</h2>
<p>恢复部分2.4.1没有发现的覆盖代码</p>
<p>新增每日打卡</p>
 <p>新增配置导入导出</p>
</blockquote>
 <blockquote>
<h2>Beta2.4.1</h2>
<p>检查发现2.3.1以后版本的更新代码被覆盖掉了 导致回滚 该版本尝试恢复</p>
</blockquote>
 <blockquote>
<h2>Beta2.4</h2>
<p>更换了弹幕姬检查更新服务器和小心心服务器</p>
</blockquote>
 <blockquote>
<h2>Beta2.3.2</h2>
<p>优化小心心逻辑</p>
<p>修复自动签到</p>
<p>修复私信接口</p>
</blockquote>
<blockquote>
<h2>Beta2.3.1</h2>
<p>修复了小心心bug</p>
</blockquote>
<blockquote>
<h2>Beta2.3</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>优化弹幕保存本地,修复断线重连机制<p/>
<p>美化网页弹幕显示，并添加查看 禁言功能<p/>
<p>增加自动签到功能和在线获取小心心功能<p/>
<p>自动回复姬添加天气接口<p/>
</blockquote>
<blockquote>
<h2>Beta2.2</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>优化请求破站速度,优化感谢礼物关注姬,优化自动回复姬,<p/>
<p>增加启动cmd窗口打印参考进入设置页面地址<p/>
<p>增加是否信息输出cmd控制台的选项(这样就不会在不使用cmd看弹幕情况下打印弹幕 无端消耗资源了)<p/>
<p>感谢礼物关注姬增加可设置多条感谢模板弹幕,既在多条感谢模板弹幕情况下随机发送一条感谢模板弹幕<p/>
<p>自动回复姬增加若干参数,自动回复姬增加精确匹配功能(仅在在只有一个关键字且没有屏蔽词的情况下使用),自动回复姬增加满足条件A或条件B 发送内容C的功能<p/>
<p><p/>
</blockquote>
<blockquote>
<h2>Beta2.1</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>把连接网页弹幕显示变为手动连接<p/>
</blockquote>
<blockquote>
<h2>Beta2.0</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>重构了解析弹幕包关键算法，项目运行更快了</p>
<p>增加自动回复姬与关键字封禁(内嵌与回复姬里面)</p>
<p>增加window版本下开启项目 自动打开默认浏览器并进入配置页面</p>
<p>增加自动连接选项，勾选后 下次打开项目自动连接上次连接的房间</p>
<p>分离弹幕图标舰队老爷的显示</p>
<p>已知bug：</p>
<p>1.0.6~1.0.7版本出现，感谢关注姬人数调节参数不可用 与感谢礼物姬的人数重合了修改感谢礼物姬参数即可修改它，2.0fix</p>
<p>自动回复姬设置为禁言时 如果除了两禁言参数外带其他字符会使自动回复姬失效</p>
</blockquote>
<blockquote>
<h2>Beta1.0.7</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>感谢弹幕姬新增加了可屏蔽礼物数量(仅在单人多种模式和多人多种模式下使用 如果是单人单种模式请去掉%Num%参数)</p>
<p>增加了向服务器查询新版本和输送公告，新增了检查更新功能</p>
<p>鉴于破站可发送重复弹幕的时间间隔的提高，修改了初始感谢弹幕姬配置的延迟时间为3s</p>
</blockquote>
<blockquote>
<h2>Beta1.0.6</h2>
<p>修改了部分title提示，更加易懂？(luo suo)</p>
<p>增加了上舰后舰长信息保存本地的选项用于判断重复发送私信 只统计uid uname(注意：这个功能只统计弹幕姬开启时间的上舰，详细看上面教程)</p>
<p>增加了保存最后一次连接房间的房间(下次打开就不用麻烦输入直播间了)</p>
<p>鉴于破站新数据 重制关注感谢姬感谢方式及关注显示，做到真正意义上的实时感谢</p>
<p>增加控制台启动小破站logo</p>
</blockquote>
<blockquote>
<h2>Beta1.0.5</h2>
<p>修复了若干bug(maybe make more bug)</p>
<p>加强关注姬的逻辑</p>
<p>改变勋章等级显示ul</p>
<p>已知bug：</p>
<p>1.0.6版本以下出现 礼物屏蔽里面自定义规则能和其他屏蔽规则一起用 并不是独立的 这个应该算是特性</p>
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
</blockquote>  

