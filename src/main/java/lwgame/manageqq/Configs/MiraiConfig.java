package lwgame.manageqq.Configs;

import lwgame.manageqq.ManageQQ;

import java.util.List;
import java.util.Objects;

public class MiraiConfig {

    public static String getMiraiUrl(){
        return ManageQQ.instance.getConfig().getString("MCL.Url");
    }

    public static int getMiraiPort(){
        return ManageQQ.instance.getConfig().getInt("MCL.Port");
    }

    public static String getVerifyKey(){
        return ManageQQ.instance.getConfig().getString("MCL.VerifyKey");
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

    public static boolean getDebug(){
        return Objects.equals(ManageQQ.instance.getConfig().getString("Bot.Debug"), "true");
    }

    public static long getQueryDelay(){
        return ManageQQ.instance.getConfig().getLong("MCL.QueryDelay");
    }

    public static long getQueryPeriod(){
        return ManageQQ.instance.getConfig().getLong("MCL.QueryPeriod");
    }

    public static long getBanLimit(){
        return ManageQQ.instance.getConfig().getLong("Bot.BanLimit");
    }

    public static double getDetectLimit(){
        return ManageQQ.instance.getConfig().getDouble("Bot.DetectLimit");
    }

    public static long getCountDown(){
        return ManageQQ.instance.getConfig().getLong("Bot.CountDown");
    }

    public static long getSpamMute(){
        return ManageQQ.instance.getConfig().getLong("Bot.SpamMute");
    }

    public static long getTimeLimit(){
        return ManageQQ.instance.getConfig().getLong("Bot.TimeLimit");
    }

    public static long getSignInTimeLimit(){
        return ManageQQ.instance.getConfig().getLong("Bot.SignInTimeLimit");
    }

    public static String getCommandPrefix(){
        return ManageQQ.instance.getConfig().getString("Bot.CommandPrefix");
    }

    public static long getMaxSignInCoin(){
        return ManageQQ.instance.getConfig().getLong("Bot.MaxSignInCoin");
    }

    public static long getPutCaveCost(){
        return ManageQQ.instance.getConfig().getLong("Bot.PutCaveCost");
    }

    public static boolean getEnable(){
        return ManageQQ.instance.getConfig().getBoolean("Bot.Enable");
    }

    public static long getBindTimeOut(){
        return ManageQQ.instance.getConfig().getLong("Bot.BindTimeOut");
    }

    public static boolean getForceBind(){
        return ManageQQ.instance.getConfig().getBoolean("Bot.ForceBind");
    }

    public static double getRewardMultiplier(){
        return ManageQQ.instance.getConfig().getDouble("Bot.RewardMultiplier");
    }
}
