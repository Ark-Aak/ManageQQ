package lwgame.manageqq.Mirai;

import lwgame.manageqq.ManageQQ;
import lwgame.manageqq.Network.Json;

public class MiraiMember {

    private final long id,joinTimestamp,lastSpeakTimestamp,muteTimeRemaining;
    private final String memberName,specialTitle;
    private final int permission;
    private final long group;
    private final MiraiGroup groupMirai;

    public MiraiMember(long target, MiraiGroup group){
        groupMirai = group;
        this.group = group.getId();
        Json resource = MiraiNetworkUtil.getMemberInfo(ManageQQ.session,group.getId(),target).getPlainResponse();
        id = resource.getLong("id");
        joinTimestamp = resource.getLong("joinTimestamp");
        lastSpeakTimestamp = resource.getLong("lastSpeakTimestamp");
        muteTimeRemaining = resource.getLong("muteTimeRemaining");
        memberName = resource.getString("memberName");
        specialTitle = resource.getString("specialTitle");
        String tPerm = resource.getString("permission");
        if(tPerm.equals("MEMBER")){
            permission = 0;
        }
        else if(tPerm.equals("OWNER")){
            permission = 2;
        }
        else{
            permission = 1;
        }
    }

    public MiraiMember(Json resource,MiraiGroup group){
        groupMirai = group;
        this.group = group.getId();
        id = resource.getLong("id");
        joinTimestamp = resource.getLong("joinTimestamp");
        lastSpeakTimestamp = resource.getLong("lastSpeakTimestamp");
        muteTimeRemaining = resource.getLong("muteTimeRemaining");
        memberName = resource.getString("memberName");
        specialTitle = resource.getString("specialTitle");
        String tPerm = resource.getString("permission");
        if(tPerm.equals("MEMBER")){
            permission = 0;
        }
        else if(tPerm.equals("OWNER")){
            permission = 2;
        }
        else{
            permission = 1;
        }
    }

    public long getId() {
        return id;
    }

    public long getJoinTimestamp() {
        return joinTimestamp;
    }

    public long getLastSpeakTimestamp() {
        return lastSpeakTimestamp;
    }


    public long getMuteTimeRemaining() {
        return muteTimeRemaining;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public int getPermission() {
        return permission;
    }

    public void mute(long time){
        MiraiNetworkUtil.muteMember(ManageQQ.session,group,id,time);
    }

    public long getGroup() {
        return group;
    }

    public MiraiGroup getGroupMirai() {
        return groupMirai;
    }
}
