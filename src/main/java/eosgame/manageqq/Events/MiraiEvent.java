package eosgame.manageqq.Events;

import org.bukkit.event.Event;

public abstract class MiraiEvent extends Event {

    private final long botId;

    public MiraiEvent(long botId){
        this.botId = botId;
    }

    public long getBotId(){
        return botId;
    }
}
