package eosgame.manageqq.Listener;

import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Configs.MiraiConfig;
import eosgame.manageqq.ManageQQ;
import eosgame.manageqq.Runnable.Kick;
import eosgame.manageqq.Utils.BindUtil;
import eosgame.manageqq.Utils.ServerUtil;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.title.Title;
import me.clip.placeholderapi.libs.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static org.bukkit.event.EventPriority.LOWEST;

public class PlayerJoinHandler implements Listener {

    public static HashMap<String, BukkitTask> mp = new HashMap<>();

    @EventHandler(priority = LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        String name = event.getPlayer().getName();
        Player p = event.getPlayer();
        if(BindUtil.getBindByName(name) == null && MiraiConfig.getForceBind()){
            ServerUtil.sendMessageLater(MessageConfig.getBindNotice(),p,20);
            mp.put(name,new Kick(p,MessageConfig.getHasNoBind()).runTaskLater(ManageQQ.instance, MiraiConfig.getBindTimeOut()));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        String name = event.getPlayer().getName();
        Player p = event.getPlayer();
        if(mp.containsKey(name)){
            mp.get(name).cancel();
            mp.remove(name);
        }
    }
}
