package eosgame.manageqq.Events;

import eosgame.manageqq.Mirai.Message.MessageChain;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MiraiGroupMessageEvent extends MiraiGroupEvent{

    private static final HandlerList handlers = new HandlerList();
    private final MessageChain message;

    public MiraiGroupMessageEvent(long botId, long groupId, MessageChain message) {
        super(botId, groupId);
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public MessageChain getMessage(){
        return message;
    }
}
