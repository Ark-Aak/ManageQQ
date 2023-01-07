package eosgame.manageqq.Utils;

import eosgame.manageqq.ManageQQ;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerUtil {

    public static void dispatchCommand(String command){
        new BukkitRunnable(){
            @Override
            public void run() {Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);}
        }.runTask(ManageQQ.instance);
    }

    public static void sendMessageLater(String msg, Player p, long time){
        new BukkitRunnable(){
            @Override
            public void run() {p.sendMessage(msg);}
        }.runTaskLaterAsynchronously(ManageQQ.instance,time);
    }
}
