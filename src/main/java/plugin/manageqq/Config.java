package plugin.manageqq;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Hashtable;

public class Config {
    private static Hashtable<String,String> cacheString = new Hashtable<>();
    private static Hashtable<String,Boolean> cacheBool = new Hashtable<>();
    private static Hashtable<String,Long> cacheNum = new Hashtable<>();
    private static Hashtable<String,Double> cacheDouble = new Hashtable<>();
    private static Hashtable<String,List<Long>> cacheList = new Hashtable<>();

    public static void writeCache(){
        cacheString.clear();
        cacheBool.clear();
        cacheNum.clear();
        cacheDouble.clear();
        cacheList.clear();
        ManageQQ.instance.reloadConfig();
        cacheString.put("Server2QQPrefix",ManageQQ.instance.getConfig().getString("Config.server2qq.prefix.string"));
        cacheString.put("QQ2ServerPrefix",ManageQQ.instance.getConfig().getString("Config.qq2server.prefix.string"));
        cacheString.put("InfoText",ManageQQ.instance.getConfig().getString("Config.info.text"));
        cacheString.put("JoinMessage",ManageQQ.instance.getConfig().getString("Message.JoinServer"));
        cacheString.put("QuitMessage",ManageQQ.instance.getConfig().getString("Message.QuitServer"));
        cacheString.put("HelpMessage",ManageQQ.instance.getConfig().getString("Message.help"));
        cacheNum.put("TokenLength",ManageQQ.instance.getConfig().getLong("Config.bind.bindTokenLength"));
        cacheNum.put("PaymentMax",ManageQQ.instance.getConfig().getLong("Config.pay.max"));
        cacheNum.put("BankMax",ManageQQ.instance.getConfig().getLong("Config.pay.bank_max"));
        cacheDouble.put("PaymentTax",ManageQQ.instance.getConfig().getDouble("Config.pay.tax"));
        cacheDouble.put("BankInterest",ManageQQ.instance.getConfig().getDouble("Config.pay.interest"));
        cacheBool.put("AllowRebind",ManageQQ.instance.getConfig().getBoolean("Config.bind.allowRebind"));
        cacheBool.put("AllowUnbind",ManageQQ.instance.getConfig().getBoolean("Config.bind.allowUnbind"));
        cacheBool.put("QQ2ServerPrefixEnable",ManageQQ.instance.getConfig().getBoolean("Config.qq2server.prefix.enable"));
        cacheBool.put("Server2QQPrefixEnable",ManageQQ.instance.getConfig().getBoolean("Config.server2qq.prefix.enable"));
        cacheBool.put("PayEnable",getActionState("pay"));
        cacheBool.put("QQ2ServerEnable",getActionState("qq2server"));
        cacheBool.put("Server2QQEnable",getActionState("server2qq"));
        cacheBool.put("InfoEnable",getActionState("info"));
        cacheBool.put("CommandEnable",getActionState("command"));
        cacheBool.put("BindEnable",getActionState("bind"));
        cacheBool.put("JoinMessageEnabled",getActionState("joinMessage"));
        cacheBool.put("QuitMessageEnabled",getActionState("leaveMessage"));
        cacheBool.put("ServerStartMessageEnabled",getActionState("serverStartMessage"));
        cacheBool.put("ServerStopMessageEnabled",getActionState("serverStopMessage"));
        cacheBool.put("ForceBindEnabled",ManageQQ.instance.getConfig().getBoolean("Config.bind.forceBind"));
        cacheList.put("EnabledGroups",ManageQQ.instance.getConfig().getLongList("enabled-groups"));
        cacheList.put("EnabledBots",ManageQQ.instance.getConfig().getLongList("enabled-bots"));
        cacheList.put("EnabledAdmins",ManageQQ.instance.getConfig().getLongList("admin"));
    }

    private static boolean getActionState(String action){
        return ManageQQ.instance.getConfig().getBoolean("Action."+action);
    }

    public static String getServer2QQPrefix(){
        return cacheString.get("Server2QQPrefix");
    }

    public static String getQQ2ServerPrefix(){
        return cacheString.get("QQ2ServerPrefix");
    }

    public static String getInfoText(){
        return cacheString.get("InfoText");
    }

    public static String getJoinMessage(){
        return cacheString.get("JoinMessage");
    }

    public static String getQuitMessage(){
        return cacheString.get("QuitMessage");
    }

    public static String getHelpMessage(){
        return cacheString.get("HelpMessage");
    }

    public static long getTokenLength(){
        return cacheNum.get("TokenLength");
    }

    public static long getPaymentMax(){
        return cacheNum.get("PaymentMax");
    }

    public static long getBankMax(){
        return cacheNum.get("BankMax");
    }

    public static double getPaymentTax(){
        return cacheDouble.get("PaymentTax");
    }

    public static double getBankInterest(){
        return cacheDouble.get("BankInterest");
    }

    public static boolean getAllowRebind(){
        return cacheBool.get("AllowRebind");
    }

    public static boolean getAllowUnbind(){
        return cacheBool.get("AllowUnbind");
    }

    public static boolean getQQ2ServerPrefixEnable(){
        return cacheBool.get("QQ2ServerPrefixEnable");
    }

    public static boolean getServer2QQPrefixEnable(){
        return cacheBool.get("Server2QQPrefixEnable");
    }

    public static boolean getPayEnable(){
        return cacheBool.get("PayEnable");
    }

    public static boolean getQQ2ServerEnable(){
        return cacheBool.get("QQ2ServerEnable");
    }

    public static boolean getServer2QQEnable(){
        return cacheBool.get("Server2QQEnable");
    }

    public static boolean getInfoEnable(){
        return cacheBool.get("InfoEnable");
    }

    public static boolean getCommandEnable(){
        return cacheBool.get("CommandEnable");
    }

    public static boolean getBindEnable(){
        return cacheBool.get("BindEnable");
    }

    public static boolean getJoinMessageEnable(){
        return cacheBool.get("JoinMessageEnabled");
    }

    public static boolean getQuitMessageEnable(){
        return cacheBool.get("QuitMessageEnabled");
    }

    public static boolean getServerStartMessageEnabled(){
        return cacheBool.get("ServerStartMessageEnabled");
    }

    public static boolean getServerStopMessageEnabled(){
        return cacheBool.get("ServerStopMessageEnabled");
    }

    public static boolean getForceBindEnabled(){
        return cacheBool.get("ForceBindEnabled");
    }

    public static List<Long> getEnabledGroups(){
        return cacheList.get("EnabledGroups");
    }

    public static List<Long> getEnabledBots(){
        return cacheList.get("EnabledBots");
    }

    public static List<Long> getEnabledAdmins(){
        return cacheList.get("EnabledAdmins");
    }
}
