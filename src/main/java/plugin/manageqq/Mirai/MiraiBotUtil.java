package plugin.manageqq.Mirai;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import plugin.manageqq.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiraiBotUtil {

    private static final List<Long> loginBot = new ArrayList<>();
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
        for (Object datum : data) {
            loginBot.add(Long.valueOf(String.valueOf(datum)));
        }
        Logger.info("解析完成，登录QQ号数量：" + data.size());
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
}
