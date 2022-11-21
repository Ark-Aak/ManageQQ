package plugin.manageqq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.message.recall.MiraiGroupMessageRecallEvent;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.*;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.parser.JSONParser;
import plugin.manageqq.database.MongoUtil;
import plugin.manageqq.database.RedisUtil;

public final class ManageQQ extends JavaPlugin implements Listener, TabExecutor {
    public static Logger log;
    public static JavaPlugin instance;
    private HashMap<String,String> BindRecord = new HashMap<>();
    private HashMap<String,Long> NameToQQ = new HashMap<>();
    private HashMap<Player, BukkitTask> KickThread = new HashMap<>();
    private HashMap<Long, Long> LastCave = new HashMap<>();
    public static Economy econ = null;

    public static String getRandomString(long length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
        log=getLogger();
        if(Boolean.parseBoolean(Config.getDatabaseInfo("Enabled"))){
            if(!MongoUtil.Initialization("cave")){
                log.info("MongoDB初始化失败！");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            else{
                log.info("MongoDB初始化成功！");
            }
        }
        if(Boolean.parseBoolean(Config.getDatabaseInfoRedis("Enabled"))){
            RedisUtil.init();
            if(RedisUtil.getdb().ping().equals("PONG")){
                log.info("Redis初始化成功！");
            }
            else{
                log.info("Redis初始化失败！");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(Bukkit.getPluginCommand("mqq")).setExecutor(this);
        File f=new File("config.yml");
        if(!f.exists()){
            saveDefaultConfig();
        }
        Config.writeCache();
        Objects.requireNonNull(Bukkit.getPluginCommand("mqq")).setExecutor(this);
        Objects.requireNonNull(Bukkit.getPluginCommand("mqq")).setTabCompleter(this);
        if (!setupEconomy()) {
            log.info("没找到Vault！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        log.info("Plugin Enabled.");
    }

    @Override
    public void onDisable() {
        log.info("Plugin Disabled.");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = (Economy)economyProvider.getProvider();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("mqq")){
            if(args.length==1){
                if(args[0].equals("reload")){
                    Config.writeCache();
                    sender.sendMessage("已重载配置");
                    return true;
                }
            }
            else if(args.length==2){
                if(args[0].equals("bind")){
                    if(!(sender instanceof Player)){
                        sender.sendMessage("控制台无法执行！");
                        return true;
                    }
                    if(BindRecord.containsKey(args[1])){
                        if(BindRecord.get(args[1]).equals(sender.getName())){
                            PlayerData.setPlayerBindData(sender.getName(),NameToQQ.get(sender.getName()));
                            BindRecord.remove(args[1]);
                            NameToQQ.remove(sender.getName());
                            if(KickThread.containsKey(sender)){
                                KickThread.get(sender).cancel();
                                KickThread.remove(sender);
                            }
                            sender.sendMessage("绑定成功！");
                            Bukkit.broadcastMessage(ChatColor.GOLD+sender.getName()+"完成了绑定！");
                        }
                        else{
                            sender.sendMessage("你不是申请该Token的玩家！");
                        }
                    }
                    else{
                        sender.sendMessage("该Token无效！");
                    }
                    return true;
                }
            }
            else{
                sender.sendMessage("=============");
                sender.sendMessage("/mqq 显示此页面");
                sender.sendMessage("/mqq bind <token> 绑定账号");
                sender.sendMessage("/mqq reload 重载配置");
                sender.sendMessage("=============");
                return true;
            }
        }
        sender.sendMessage("参数错误，使用/mqq获取帮助");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String message=e.getMessage();
        if(Config.getServer2QQEnable()){
            if(message.startsWith(Config.getServer2QQPrefix())){
                new SendMessage(e.getPlayer().getName()+"："+message.substring(Config.getServer2QQPrefix().length())).runTaskAsynchronously(this);
            }
            else{
                if(!Config.getServer2QQPrefixEnable()) {
                    new SendMessage(e.getPlayer().getName()+"："+message).runTaskAsynchronously(this);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e){
        if(Config.getForceBindEnabled()){
            if(!PlayerData.playerHasBindData(e.getPlayer().getName())){
                e.joinMessage(null);
                KickThread.put(e.getPlayer(),new Kick(e.getPlayer(),"请绑定完成后再来！").runTaskLater(this,2400));
                e.getPlayer().sendMessage("你将在120秒后被踢出服务器，绑定即可停止计时。");
            }
        }
        if(Config.getJoinMessageEnable()){
            new SendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(),Config.getJoinMessage())).runTaskAsynchronously(this);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent e){
        if(!PlayerData.playerHasBindData(e.getPlayer().getName())){
            e.quitMessage(null);
            return;
        }
        if(Config.getQuitMessageEnable()){
            new SendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(),Config.getQuitMessage())).runTaskAsynchronously(this);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGroupMemberJoin(MiraiMemberJoinEvent e){
        String message=Config.getJoinGroupMessage();
        MiraiBot bot=MiraiBot.getBot(e.getBotID());
        MiraiGroup group=bot.getGroup(e.getGroupID());
        if(Config.getJoinGroupMessageEnable()){
            group.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGroupMemberLeave(MiraiMemberLeaveEvent e){
        String message=Config.getQuitGroupMessage();
        MiraiBot bot=MiraiBot.getBot(e.getBotID());
        MiraiGroup group=bot.getGroup(e.getGroupID());
        if(Config.getQuitGroupMessageEnable()){
            group.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){
        boolean flag = false;
        for(long i : Config.getEnabledBots()){
            if(e.getBotID()==i){
                flag=true;
            }
        }
        if(!flag){
            return;
        }
        String eMessage=e.getMessage();
        MiraiBot bot=MiraiBot.getBot(e.getBotID());
        MiraiGroup group=bot.getGroup(e.getGroupID());
        String[] args=eMessage.split(" "),argsCode=e.getMessageToMiraiCode().split(" ");
        if(eMessage.equals(".check-perm")){
            if(!PlayerData.QQIDisAdmin(e.getSenderID())){
                group.sendMessage("你不是服主认定的管理员！");
                return;
            }
            for(long j:Config.getEnabledGroups()){
                for(long k:bot.getGroupList()){
                    if(j==k){
                        int Perm = bot.getGroup(k).getBotPermission();
                        if(Perm==0){
                            bot.getGroup(k).sendMessage("机器人在群中为普通成员，有些功能可能出现故障甚至报错！");
                        }
                    }
                }
            }
        }
        if(eMessage.equals(".help")){
            group.sendMessage(Config.getHelpMessage());
            return;
        }
        if(eMessage.equals(".hitokoto")){
            //Hitokoto一言
            JSONObject hitokoto = JSON.parseObject(Network.sendGet("https://v1.hitokoto.cn","c=a&c=b&c=c&c=f"));
            String content,from;
            int id;
            content = hitokoto.getString("hitokoto");
            from = hitokoto.getString("from");
            id = hitokoto.getIntValue("id");
            group.sendMessage(String.valueOf(content + "\n" + "——" + from + "(" + id + ")"));
        }
        if(eMessage.equals(".online-players")) {
            StringBuilder message=new StringBuilder();
            message.append("当前服务器在线玩家列表：\n");
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (Player player : players) {
                message.append(player.getName());
                message.append("\n");
            }
            message.append("总共在线人数：");
            message.append(players.length);
            message.append("人");
            group.sendMessage(String.valueOf(message));
            return;
        }
        if(args[0].equals(".bind")){
            if(!Config.getBindEnable()){
                group.sendMessage("服主未开启绑定功能！");
                return;
            }
            if(args.length!=2){
                group.sendMessage("参数错误！");
            }
            else {
                if((PlayerData.DataHasPlayer(e.getSenderID())|| PlayerData.playerHasBindData(args[1])&&(!Config.getAllowRebind()))){
                    group.sendMessage("你绑定过账号了！并且服主未开启重绑定！");
                    return;
                }
                String token=getRandomString(Config.getTokenLength());
                BindRecord.put(token, args[1]);
                NameToQQ.put(args[1], e.getSenderID());
                group.sendMessage("申请成功\n请在游戏中使用 /mqq bind "+token+" 来完成绑定");
            }
            return;
        }
        if(args[0].equals(".execute")){
            if(!Config.getCommandEnable()){
                group.sendMessage("服主未开启命令执行功能！");
                return;
            }
            if(!PlayerData.QQIDisAdmin(e.getSenderID())){
                group.sendMessage("你不是服主认定的管理员！");
                return;
            }
            StringBuilder cmd=new StringBuilder();
            if(args.length>=2){
                for(int i=1;i<args.length;i++){
                    if(i<args.length){
                        cmd.append(args[i]).append(" ");
                    }
                }
                cmd.deleteCharAt(cmd.length()-1);
                new CommandDispatch(String.valueOf(cmd)).runTask(this);
                group.sendMessage("已执行命令："+cmd);
            }
            return;
        }
        if(eMessage.equals(".info")){
            if(!Config.getInfoEnable()){
                group.sendMessage("服主未开启玩家信息功能！");
                return;
            }
            if(PlayerData.DataHasPlayer(e.getSenderID())){
                group.sendMessage(PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())),Config.getInfoText()));
            }
            else{
                group.sendMessage("你没有绑定账号！请绑定账号！");
            }
            return;
        }
        if(args[0].equals(".pay")){
            if(!Config.getPayEnable()){
                group.sendMessage("服主未开启经济功能！");
                return;
            }
            if(args.length!=3){
               group.sendMessage("参数错误！");
               return;
            }
            if(PlayerData.DataHasPlayer(e.getSenderID())){
                double money;
                try{
                    money=Double.parseDouble(args[2]);
                } catch (NumberFormatException ex) {
                    group.sendMessage("请输入一个正数！");
                    return;
                } catch (NullPointerException ex) {
                    group.sendMessage("意外的错误：java.lang.NullPointerException");
                    return;
                }
                if(money<=0){
                    group.sendMessage("请传入一个正数！");
                    return;
                }
                if(Config.getPaymentMax()<money){
                    group.sendMessage("你的交易金额超过服主设置的上限！");
                    return;
                }
                if(econ.getBalance(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())))>=money){
                    if(PlayerData.playerHasBindData(args[1])){
                        econ.withdrawPlayer(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())),money);
                        econ.depositPlayer(Bukkit.getOfflinePlayer(args[1]),money);
                        group.sendMessage("成功转账给"+args[1]+money+"金币！");
                    }
                    else{
                        group.sendMessage("该玩家尚未绑定QQ，提醒他绑定QQ吧！");
                    }
                }
                else{
                    group.sendMessage("余额不足！");
                }
            }
            else{
                group.sendMessage("你没有绑定账号！请绑定账号！");
            }
            return;
        }
        if(args[0].equals(".bank")){
            if(!Config.getPayEnable()){
                group.sendMessage("服主未开启经济功能！");
                return;
            }
            if(args.length!=3){
                group.sendMessage("参数错误！");
                return;
            }
            if(args[1].equals("deposit")){
                if(!PlayerData.DataHasPlayer(e.getSenderID())){
                    group.sendMessage("你没有绑定账号！请绑定账号！");
                    return;
                }
                double money;
                try{
                    money=Double.parseDouble(args[2]);
                } catch (NumberFormatException ex) {
                    group.sendMessage("请输入一个正数！");
                    return;
                } catch (NullPointerException ex) {
                    group.sendMessage("意外的错误：java.lang.NullPointerException");
                    return;
                }
                if(money<=0){
                    group.sendMessage("请输入一个正数！");
                    return;
                }
                if(Bank.getBankBalance(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())))+money>Config.getBankMax()){
                    group.sendMessage("你转账后的银行余额超过服主设置的上限！");
                    return;
                }
                if(Bank.playerDeposit(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())),money)){
                    group.sendMessage("存款成功！");
                }
                else{
                    group.sendMessage("余额不足！");
                }
            }
            if(args[1].equals("withdraw")){
                if(!PlayerData.DataHasPlayer(e.getSenderID())){
                    group.sendMessage("你没有绑定账号！请绑定账号！");
                    return;
                }
                double money;
                try{
                    money=Double.parseDouble(args[2]);
                } catch (NumberFormatException ex) {
                    group.sendMessage("请输入一个正数！");
                    return;
                } catch (NullPointerException ex) {
                    group.sendMessage("意外的错误：java.lang.NullPointerException");
                    return;
                }
                if(money<=0){
                    group.sendMessage("请输入一个正数！");
                    return;
                }
                if(Bank.playerWithdraw(Bukkit.getOfflinePlayer(PlayerData.DataPlayer(e.getSenderID())),money)){
                    group.sendMessage("取款成功！");
                }
                else{
                    group.sendMessage("银行余额不足！");
                }
            }
            if(args[1].equals("balance")){
                if(!PlayerData.DataHasPlayer(e.getSenderID())){
                    group.sendMessage("你没有绑定账号！请绑定账号！");
                    return;
                }
                group.sendMessage(args[2]+"的银行余额："+Bank.getBankBalance(Bukkit.getOfflinePlayer(args[2])));
            }
            return;
        }
        if(args[0].equals(".cave")){
            if(LastCave.containsKey(e.getSenderID())){
                long last=LastCave.get(e.getSenderID());
                long now=System.currentTimeMillis();
                long past=now-last;
                if(past<=5000){
                    group.sendMessage("请勿频繁使用回声洞！");
                    return;
                }
            }
            LastCave.put(e.getSenderID(),System.currentTimeMillis());
            if(!Config.getCaveEnable()){
                group.sendMessage("服主未开启回声洞功能！");
                return;
            }
            if(!Boolean.parseBoolean(Config.getDatabaseInfo("Enabled"))){
                group.sendMessage("服主未配置MongoDB数据库！");
                return;
            }
            if(args.length==1){
                try{
                    Document cavec=cave.getCave();
                    group.sendMessageMirai(cavec.getString("content")+"\n"+"——"+cavec.getString("sender"));
                }
                catch (Exception ex){
                    group.sendMessage("回声洞中无内容！");
                }
                return;
            }
            else if(args.length==3){
                if(args[1].equals("put")){
                    if(argsCode[2].contains("mirai:at")){
                        group.sendMessage("禁止在回声洞中添加At信息！");
                        return;
                    }
                    if(argsCode[2].contains("mirai:image")){
                        group.sendMessage("禁止在回声洞中添加图片信息！");
                        return;
                    }
                    cave.addCave(argsCode[2],e.getSenderName(),e.getSenderID());
                    group.sendMessage("添加成功！");
                }
                else{
                    group.sendMessage("参数错误！");
                }
            }
            else{
                group.sendMessage("参数错误！");
            }
            return;
        }
        if(Config.getQQ2ServerEnable()){
            if(eMessage.startsWith(Config.getQQ2ServerPrefix())){
                if(PlayerData.DataHasPlayer(e.getSenderID())){
                    Bukkit.broadcastMessage(PlayerData.DataPlayer(e.getSenderID()) + "：" + eMessage.substring(Config.getQQ2ServerPrefix().length()));
                }
                else{
                    Bukkit.broadcastMessage(e.getSenderID() + "：" + eMessage.substring(Config.getQQ2ServerPrefix().length()));
                }
            }
            else{
                if(!Config.getQQ2ServerPrefixEnable()){
                    if(PlayerData.DataHasPlayer(e.getSenderID())){
                        Bukkit.broadcastMessage(PlayerData.DataPlayer(e.getSenderID()) + "：" + eMessage);
                    }
                    else{
                        Bukkit.broadcastMessage(e.getSenderID() + "：" + eMessage);
                    }
                }
            }
        }
    }
}
