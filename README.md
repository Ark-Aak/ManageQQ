# ManageQQ
ManageQQ是一款和QQ进行互通的类似群管的插件。  
# 使用方法  
## 前置插件
~~本插件依赖MiraiMC，推荐使用MiraiMC-1.7版本作为前置~~  
由于MiraiMC并未更新其上游Mirai，现改用Mirai-HttpApi  
依赖Mirai Console Loader  
## 插件版本命名规则
版本格式为[D/S/R]+版本号+build  
D->开发版  
S->快照版  
R->正式版  
build越大越新  
## 配置文件
```yaml
Database:
  Enabled: false #是否启用数据库
  Url: "mongodb://localhost:27017" #MongoDB的连接字符串
  Db: "cave" #数据库名称
MCL:
  Url: "http://127.0.0.1" #MCL-HttpApi的URL
  Port: 8080 #MCL的端口
  VerifyKey: "fill your key here" #MCL的verifyKey
  QueryDelay: 40 #两次请求的延迟（刻为单位）
  QueryPeriod: 40 #第一次请求前的等待
Bot:
  Enable: true #是否启用机器人模块
  Debug: false #调试模式
  BotId: 114514 #机器人的ID
  #屏蔽词，格式为 词语/是否删除特殊符号/禁言时长（秒）
  BanWord: |-
    傻逼/true/120
  #调试功能，无需更改
  BanPeople: |-
    1919810
  BanLimit: 500 #Spam Score分数到多少会被禁言
  DetectLimit: 0.75 #两条消息最低被检测到的相似度（0~1）
  CountDown: 10 #发一次正常的消息减少的分数
  SpamMute: 600 #被禁言的时长
  TimeLimit: 1500 #两次消息最小间隔，若不满间隔会加（TimeLimit-实际间隔）分
  SignInTimeLimit: 64800000 #签到的间隔时长
  CommandPrefix: "." #指令前缀
  PutCaveCost: 500 #投稿回声洞需要的W币
  BindTimeOut: 2400 #绑定超时时间（刻）
  ForceBind: true #是否开启强制绑定
Message:
  Help: |-
    测试下帮助QWQ
    这个也是QWQ
  NoPermission: |-
    你没有权限QWQ！
    试试其他的命令吧QWQ
  BotNoPermission: |-
    QAQ，我是个卑微的机器人，不敢禁言这个人呢QAQ
  NoCommand: |-
    啊哦，貌似没找到这个命令呢QWQ？
    也许你可以尝试下.help来查看命令
  NotANumber: |-
    啊哦，貌似输入的不是个数字呢QAQ
  InDeveloping: |-
    貌似这个功能正在开发呢QWQ！
    再等等吧QWQ！
  Recall: |-
    貌似你的发言触发了群主设置的规则QWQ？
    所以被撤回啦QWQ！
  OK: |-
    操作成功完成QWQ！
  Disabled: |-
    功能未开启QAQ...
  BindFailed: |-
    &c貌似绑定失败了QAQ...再检查下你的&6玩家名&c和&6Token&c吧QWQ！
  BindSuccessful: |-
    &a绑定成功QWQ！
  Requested: |-
    申请成功QWQ！
    请在游戏中使用{command}来完成绑定！
  HasBind: |-
    你的QQ已经绑定了{player}！
    不能再绑定了QAQ！
  DontSpam: |-
    不要刷屏哦QWQ!
  Balance: |-
    你的余额：{eco}个W币QWQ！
  Failed: |-
    啊哦，貌似失败了QAQ！
  SignInSucceed: |-
    签到成功QWQ！你获得了{eco}个W币！
  SignInFailed: |-
    签到失败QAQ...再等等吧QWQ...
  HasNoMoney: |-
    余额不足QAQ...
  HasNoBind: |-
    你没有绑定账号QAQ...
  NoBind: |-
    这个人没有绑定账号QWQ！
  QueryBind: |-
    这个人绑定的账号是：{player}
  BindNotice: |-
    &c服务器开启了强制绑定账号，请先绑定
```
