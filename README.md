# ManageQQ
ManageQQ是一款和QQ进行互通的类似群管的插件。  
# 使用方法  
## 前置插件
本插件依赖MiraiMC，推荐使用MiraiMC-1.7版本作为前置  
本插件依赖Vault作为经济前置  
本插件依赖PlaceholderAPI作为变量前置  
## 配置文件
```yaml
enabled-groups:               #启用机器人的群聊
  - 764136404
enabled-bots:                 #启用的机器人
  - 3566096221
admin:                        #认定的管理
  - 1265723427
Action:
  pay: true                   #经济模块
  server2qq: true             #服务器到QQ消息互通
  qq2server: true             #QQ到服务器信息互通
  info: true                  #玩家信息
  command: true               #群内执行指令
  bind: true                  #绑定模块
  joinMessage: false          #服务器加入消息
  leaveMessage: false         #服务器退出消息
  joinGroupMessage: true      #加入群聊消息(未完成)
  leaveGroupMessage: true     #退出群聊消息(未完成)
  cave: true                  #回声洞(依赖MongoDB数据库)
Config:
  server2qq:
    prefix:
      enable: true            #是否开启服务器到QQ消息互通前缀，若值为false则转发所有消息
      string: "[GR]"          #若值为true，转发以这个值为前缀的消息
  qq2server:
    prefix:
      enable: true            #是否开启QQ到服务器消息互通前缀，若值为false则转发所有消息
      string: "[GA]"          #若值为true，转发以这个值为前缀的消息
  pay:
    max: 20000                #单次金币转账最大额度
    tax: 1                    #转账税(百分比)(未完成)
    bank_max: 2000000         #银行余额最大值
    interest: 0.1             #银行利息(百分比)(未完成)
  info:
    text: |-                  #玩家信息格式，支持PAPI
      ====================
      玩家名%player_name%
      ====================
  bind:
    bindTokenLength: 15       #绑定Token的长度
    allowRebind: false        #允许重复绑定
    allowUnbind: false        #允许解除绑定(未完成)
    forceBind: false          #强制绑定，若为true则未绑定的玩家会在120秒内被踢出
Database:                     #MongoDB数据库信息
  Enabled: false              #是否启用，若不启用则回声洞功能无法使用
  Url: "localhost"            #地址
  Port: 27017                 #端口，默认27017
  Username: "user"            #用户名
  Password: "password"        #密码
  Database: "db"              #数据库名称
Message:
  JoinServer: "玩家%player_name%加入了服务器！"
  QuitServer: "玩家%player_name%退出了服务器！"
  JoinGroup: "欢迎新人{at}加入EOS服务器！"
  QuitGroup: "{nick}退出了群聊！"
  help: |-                    #执行.help时输出的内容
    可用命令列表：
    .online-players |获取在线玩家列表
    .bind 玩家名 |申请绑定账号
    .execute 指令 |执行命令
    .bank balance 玩家名 |查看银行余额
    .bank deposit 金额 |存入金币
    .bank withdraw 金额 |取出金币
    .info |玩家信息
    .pay 玩家名 金额 |支付给玩家金币
    .cave |获取一条回声洞
    .cave put 内容 |投稿一条回声洞
BindData:                     #勿动

Index:                        #勿动

BankData:                     #勿动

```
