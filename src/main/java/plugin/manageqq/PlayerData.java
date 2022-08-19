package plugin.manageqq;

import static org.bukkit.Bukkit.*;

public class PlayerData {
    public static boolean playerHasBindData(String name){
        return ManageQQ.instance.getConfig().getLong("BindData."+name)!=0;
    }
    public static long playerBindData(String name){
        return ManageQQ.instance.getConfig().getLong("BindData."+name);
    }
    public static void setPlayerBindData(String name,long bind){
        ManageQQ.instance.getConfig().set("BindData."+name,bind);
        ManageQQ.instance.getConfig().set("Index."+bind,name);
        ManageQQ.instance.saveConfig();
    }

    public static boolean DataHasPlayer(long data){
        return ManageQQ.instance.getConfig().getString("Index."+data)!=null;
    }
    public static String DataPlayer(long data){
        return ManageQQ.instance.getConfig().getString("Index."+data);
    }
    public static boolean QQIDisAdmin(long id){
        for(int i=0;i<Config.getEnabledAdmins().size();i++){
            if(i<Config.getEnabledAdmins().size()){
                if(id==Config.getEnabledAdmins().get(i)){
                    return true;
                }
            }
        }
        return false;
    }
}
