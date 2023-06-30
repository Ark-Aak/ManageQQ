package lwgame.manageqq;

import lwgame.manageqq.Configs.DataBaseConfig;
import lwgame.manageqq.Configs.MessageConfig;
import lwgame.manageqq.Configs.MiraiConfig;
import lwgame.manageqq.Databases.MongoUtil;
import lwgame.manageqq.Exceptions.MiraiUnknownException;
import lwgame.manageqq.Mirai.MiraiBotUtil;
import lwgame.manageqq.Runnable.MessageGetter;
import lwgame.manageqq.Mirai.MiraiSession;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import lwgame.manageqq.Commands.mqqExecutor;
import lwgame.manageqq.Listener.PlayerJoinHandler;
import lwgame.manageqq.Exceptions.MiraiBotDoesNotExistException;
import org.bukkit.scheduler.BukkitTask;

public final class ManageQQ extends JavaPlugin{

    public static Logger log;
    public static JavaPlugin instance;
    public static MiraiSession session;
    public static BukkitTask getter;
    public static ConcurrentHashMap<Long, Long> SimTot = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, String> lastMsg = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, Long> lastTime = new ConcurrentHashMap<>();
    public static OfflinePlayer SYSTEM;

    @Override
    public void onEnable() {
        instance=this;
        log=getLogger();
        log.info("===============================");
        log.info("欢迎使用ManageQQ！作者Anschluss_zeit");
        log.info("项目使用GPLv3开源");
        log.info("项目地址https://github.com/LW-GAME/ManageQQ");
        log.info("由于MiraiMC长期不更新上游依赖，现在改用HttpApi");
        //Bukkit.getPluginManager().registerEvents(this, this);
        File f=new File("config.yml");
        if(!f.exists()){
            saveDefaultConfig();
        }
        MiraiBotUtil.init(false);
        log.info("注册监听器" + ChatColor.GREEN + "PlayerJoinHandler...");
        Bukkit.getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        log.info("注册命令...");
        Objects.requireNonNull(Bukkit.getPluginCommand("mqq")).setExecutor(new mqqExecutor());
        log.info("启动线程...");
        getter = new MessageGetter().runTaskTimerAsynchronously(this, MiraiConfig.getQueryDelay(),MiraiConfig.getQueryPeriod());
        if(DataBaseConfig.getEnabled()){
            log.info("初始化MongoDB...");
            MongoUtil.Initialization(DataBaseConfig.getUrl(),DataBaseConfig.getDb());
            MongoUtil.createCollection("bind");
            MongoUtil.createCollection("cave");
            MongoUtil.createCollection("user");
        }
        log.info("检查配置...");
        if(MessageConfig.getReply().size() != MessageConfig.getRegex().size() ||
                MessageConfig.getData().size() != MessageConfig.getRegex().size() ||
                MessageConfig.getData().size() != MessageConfig.getReply().size()
        ){
            log.severe("===============================");
            log.severe("错误：AutoReply部分的配置无法匹配！");
            log.severe("===============================");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        SYSTEM = Bukkit.getOfflinePlayer("SYSTEM");;
        log.info("插件已启动！感谢您的使用");
        log.info("===============================");
    }

    @Override
    public void onDisable() {
        log.info("正在停止线程...");
        Bukkit.getScheduler().cancelTasks(this);
        log.info("释放sessionKey...");
        try {
            session.releaseSession();
        } catch (MiraiBotDoesNotExistException e) {
            log.info("session已经被自动销毁...跳过");
        } catch (MiraiUnknownException e) {
            e.printStackTrace();
        }
        log.info("插件已禁用！感谢您的使用！");
    }
}
