package plugin.manageqq.Configs;

import plugin.manageqq.ManageQQ;

import java.util.List;

public class MiraiConfig {

    public static String getMiraiUrl(){
        return ManageQQ.instance.getConfig().getString("MCL.Url");
    }

    public static int getMiraiPort(){
        return ManageQQ.instance.getConfig().getInt("MCL.Port");
    }

    public static String getVerifyKey(){
        return ManageQQ.instance.getConfig().getString("MCL.verifyKey");
    }

    public static String getMiraiFullUrl(){
        return getMiraiUrl() + ":" + getMiraiPort();
    }

    public static long getBotId(){
        return ManageQQ.instance.getConfig().getLong("Bot.BotId");
    }

    public static List<Long> getGroups(){
        return ManageQQ.instance.getConfig().getLongList("Bot.Groups");
    }

    public static String getMiraiApi(String api){
        return getMiraiFullUrl() + api;
    }
}
