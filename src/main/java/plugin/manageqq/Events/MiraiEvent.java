package plugin.manageqq.Events;

import org.bukkit.event.Event;

public abstract class MiraiEvent extends Event {

    private final long botId;
    private final long groupId;

    public MiraiEvent(long botId,long groupId){
        this.botId = botId;
        this.groupId = groupId;
    }

    public long getBotId(){
        return botId;
    }

    public long getGroupId(){
        return groupId;
    }
}
