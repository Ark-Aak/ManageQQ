package plugin.manageqq.Configs;

import plugin.manageqq.ManageQQ;

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
}
