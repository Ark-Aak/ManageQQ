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
Database:                             #MongoDB
  Enabled: false                      #是否启用
  Url: "mongodb://localhost:27017"    #MongoDB连接Url，可以百度格式
  Db: "cave"                          #数据库名称
MCL:                                  #Mirai Console Loader配置
  Url: "http://127.0.0.1"             #MCL Url
  Port: 8080                          #MCL 端口
  verifyKey: "fill your key here"     #MCL verifyKey
  queryDelay: 40                      #Http第一次轮询前的等待（以刻为单位，20Tick=1s）
  queryPeriod: 40                     #Http轮询间隔（以刻为单位，20Tick=1s）
Bot:                                  #机器人配置
  Debug: false                        #调试模式（无需要不用开启，会输出大量调试信息）
  BotId: 114514                       #机器人的QQ号
  BanWord: |-                         #敏感词，一行一个，格式为word/mode/muteTime
    傻逼/true/120                      #word->敏感词，mode->true为忽略特殊符号，muteTime->禁言时长(秒)，为0禁用
  BanPeople: |-                       #测试用的，填写QQ号
    1919810                           #如果无必要请勿开启！
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
```
