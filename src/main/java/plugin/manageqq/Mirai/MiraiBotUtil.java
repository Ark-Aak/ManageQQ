package plugin.manageqq.Mirai;

import com.alibaba.fastjson2.JSONArray;
import jdk.jpackage.internal.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import plugin.manageqq.Configs.MiraiConfig;
import plugin.manageqq.Exceptions.MiraiBotOfflineException;
import plugin.manageqq.Exceptions.MiraiUnknownException;
import plugin.manageqq.Exceptions.MiraiVerifyKeyInvalidException;
import plugin.manageqq.Logger;
import plugin.manageqq.ManageQQ;

import java.util.ArrayList;
import java.util.List;

import static plugin.manageqq.ManageQQ.session;

public class MiraiBotUtil {

    private static List<Long> loginBot = new ArrayList<>();
    private static boolean initMark;

    /**
     * 初始化MiraiBot模块
     */
    public static void init(){
        Logger.info("MiraiBot模块正在初始化...");
        initMark=true;
        Logger.info("获取登陆列表...");
        MiraiNetworkResponse response = MiraiNetworkUtil.getLoginBots();
        Logger.info("解析登陆列表...");
        JSONArray data = response.getDataArray();
        loginBot.clear();
        for(Object botId : data){
            long bid = Long.parseLong(String.valueOf(botId));
            if(bid<0){
                //我也不知道这是啥奇葩bug
                //Fastjson貌似在序列化的时候给我强制转到int了
                //只能这样了
                //修正：在Fastjson v2.0.20后未出现此bug
                Logger.info("奇怪的Bug出现了：BotId=" + bid);
                bid = 4294967296L + bid;
                Logger.info("修正为BotId=" + bid);
            }
            loginBot.add(bid);
        }
        Logger.info("解析完成，登录QQ号数量：" + data.size());
        Logger.info("登录的机器人：");
        for(long val : loginBot){
            Logger.info(String.valueOf(val));
        }
        try {
            session = new MiraiSession(MiraiUtil.getSession(MiraiConfig.getVerifyKey()));
            Logger.info("将session与QQ" + MiraiConfig.getBotId() + "绑定...");
            session.bindBotId(MiraiConfig.getBotId());
            Logger.info(ChatColor.GOLD + "绑定成功，机器人Id="+MiraiConfig.getBotId());
        } catch (MiraiVerifyKeyInvalidException e) {
            Logger.error("verifyKey无效！");
            Logger.error("开始禁用插件...");
            Bukkit.getPluginManager().disablePlugin(ManageQQ.instance);
        } catch (MiraiBotOfflineException e) {
            Logger.error("指定的机器人未登录！");
            Logger.error("开始禁用插件...");
            Bukkit.getPluginManager().disablePlugin(ManageQQ.instance);
        } catch (MiraiUnknownException e) {
            Logger.error("出现未知错误" + e.getErrorCode() + "！");
            Logger.error("开始禁用插件...");
            Bukkit.getPluginManager().disablePlugin(ManageQQ.instance);
        }
    }

    /**
     * 检测某个机器人是否已经登陆
     *
     * @param botId 机器人QQ号
     * @return true表示已经登陆，false表示未登录
     */
    public static boolean isBotLogin(long botId){
        if(!initMark){
            init();
        }
        for (long loginId : loginBot) {
            if (loginId == botId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取已经登录的机器人
     *
     * @return 已经登录的机器人列表
     */
    public static List<Long> getLoginBots(){
        if(!initMark){
            init();
        }
        return loginBot;
    }
}
