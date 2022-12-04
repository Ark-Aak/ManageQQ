package plugin.manageqq;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.scheduler.BukkitTask;
import plugin.manageqq.Commands.mqqExecutor;
import plugin.manageqq.Configs.MiraiConfig;
import plugin.manageqq.Databases.MongoUtil;
import plugin.manageqq.Events.PlayerChatHandler;
import plugin.manageqq.Exceptions.MiraiBotOfflineException;
import plugin.manageqq.Exceptions.MiraiUnknownException;
import plugin.manageqq.Exceptions.MiraiVerifyKeyInvalidException;
import plugin.manageqq.Mirai.MiraiSession;
import plugin.manageqq.Mirai.MiraiUtil;

public final class ManageQQ extends JavaPlugin{

    public static Logger log;
    public static JavaPlugin instance;
    public static MiraiSession session;

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
        instance=this;
        log=getLogger();
        log.info("===============================");
        log.info("欢迎使用ManageQQ！作者Anschluss_zeit");
        log.info("由于MiraiMC长期不更新上游依赖，现在改用HttpApi");
        //Bukkit.getPluginManager().registerEvents(this, this);
        File f=new File("config.yml");
        if(!f.exists()){
            saveDefaultConfig();
        }
        try {
            session = new MiraiSession(MiraiUtil.getSession(MiraiConfig.getVerifyKey()));
            session.bindBotId(1265723427L);
        } catch (MiraiVerifyKeyInvalidException | MiraiUnknownException | MiraiBotOfflineException e) {
            e.printStackTrace();
        }
        log.info("注册事件...");
        Bukkit.getPluginManager().registerEvents(new PlayerChatHandler(), this);
        log.info("注册命令...");
        Objects.requireNonNull(Bukkit.getPluginCommand("mqq")).setExecutor(new mqqExecutor());
        log.info("插件已启动！感谢您的使用");
        log.info("===============================");
    }

    @Override
    public void onDisable() {
        log.info("正在停止线程...");
        Bukkit.getScheduler().cancelTasks(this);
        log.info("插件已禁用！感谢您的使用！");
    }
}
