package plugin.manageqq;

import org.bukkit.OfflinePlayer;

public class Bank {
    public static double getBankBalance(OfflinePlayer p){
        return ManageQQ.instance.getConfig().getDouble("BankData."+p.getName());
    }
    public static void setBankBalance(OfflinePlayer p,double v){
        ManageQQ.instance.getConfig().set("BankData."+p.getName(),v);
        ManageQQ.instance.saveConfig();
    }
    public static boolean playerDeposit(OfflinePlayer p,double v){
        if(ManageQQ.econ.getBalance(p)<v)return false;
        ManageQQ.econ.withdrawPlayer(p,v);
        setBankBalance(p,getBankBalance(p)+v);
        return true;
    }
    public static boolean playerWithdraw(OfflinePlayer p,double v){
        if(getBankBalance(p)<v)return false;
        ManageQQ.econ.depositPlayer(p,v);
        setBankBalance(p,getBankBalance(p)-v);
        return true;
    }
}
