package plugin.manageqq.Events;

import plugin.manageqq.Mirai.Message.MessageChain;

public abstract class MiraiMessageEvent extends MiraiEvent{

    private final MessageChain message;

    public MiraiMessageEvent(long botId, long groupId, MessageChain message) {
        super(botId, groupId);
        this.message = message;
    }

    public MessageChain getMessage(){
        return message;
    }
}
