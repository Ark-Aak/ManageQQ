package plugin.manageqq;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import plugin.manageqq.Commands.mqqExecutor;
import plugin.manageqq.Listener.PlayerChatHandler;
import plugin.manageqq.Exceptions.MiraiBotDoesNotExistException;
import plugin.manageqq.Exceptions.MiraiUnknownException;
import plugin.manageqq.Mirai.MiraiBotUtil;
import plugin.manageqq.Mirai.MiraiNetworkUtil;
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
        MiraiBotUtil.init(false);
        log.info("sessionKey=" + session.sessionKey);
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
