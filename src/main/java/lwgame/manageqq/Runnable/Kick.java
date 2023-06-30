package lwgame.manageqq.Runnable;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Kick extends BukkitRunnable {

    private String message;
    private Player p;

    public Kick(Player p, String Message){
        this.message=Message;
        this.p=p;
    }
    @Override
    public void run() {
        p.kickPlayer(message);
    }
}
