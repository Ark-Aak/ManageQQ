package lwgame.manageqq.Configs;

import lwgame.manageqq.ManageQQ;

public class DataBaseConfig {

    public static boolean getEnabled(){
        return ManageQQ.instance.getConfig().getBoolean("Database.Enabled");
    }

    public static String getUrl(){
        return ManageQQ.instance.getConfig().getString("Database.Url");
    }

    public static String getDb(){
        return ManageQQ.instance.getConfig().getString("Database.Db");
    }
}
