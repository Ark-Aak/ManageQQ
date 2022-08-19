package plugin.manageqq;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandDispatch extends BukkitRunnable {
    private String cmd;
    public CommandDispatch(String command){
        cmd=command;
    }
    @Override
    public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
    }
}
