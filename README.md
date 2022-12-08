# ManageQQ
ManageQQ是一款和QQ进行互通的类似群管的插件。  
# 使用方法  
## 前置插件
~~本插件依赖MiraiMC，推荐使用MiraiMC-1.7版本作为前置~~  
由于MiraiMC并未更新其上游Mirai，现改用Mirai-HttpApi  
依赖Mirai Console Loader  
## 配置文件
```yaml
Database:                             #MongoDB
  Enabled: false                      #是否启用
  Url: "mongodb://localhost:27017"    #MongoDB连接Url，可以百度格式
MCL:                                  #Mirai Console Loader配置
  Url: "http://127.0.0.1"             #MCL Url
  Port: 8080                          #MCL 端口
  verifyKey: "fill your key here"     #MCL verifyKey
  queryDelay: 40                      #Http第一次轮询前的等待（以刻为单位，20Tick=1s）
  queryPeriod: 40                     #Http轮询间隔（以刻为单位，20Tick=1s）
Bot:                                  #机器人配置
  Debug: false                        #调试模式（无需要不用开启，会输出大量调试信息）
  BotId: 114514                       #机器人的QQ号
```
