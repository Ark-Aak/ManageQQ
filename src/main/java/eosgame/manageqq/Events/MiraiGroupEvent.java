package eosgame.manageqq.Events;

public abstract class MiraiGroupEvent extends MiraiEvent{

    private final long groupId;

    public MiraiGroupEvent(long botId, long groupId) {
        super(botId);
        this.groupId = groupId;
    }

    public long getGroupId(){
        return groupId;
    }
}
