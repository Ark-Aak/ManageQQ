package plugin.manageqq;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.scheduler.BukkitRunnable;

public class SendMessage extends BukkitRunnable {
    private String message,id;
    public SendMessage(String message){
        this.message=message;
        id=ManageQQ.getRandomString(20);
    }
    @Override
    public void run() {
        if(Config.getServer2QQEnable()){
            for(long i:Config.getEnabledBots()){
                for(long j:Config.getEnabledGroups()){
                    MiraiBot.getBot(i).getGroup(j).sendMessage(message);
                }
            }
            return;
        }
    }
}
