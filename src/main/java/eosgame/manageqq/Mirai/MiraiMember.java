package eosgame.manageqq.Mirai;

import eosgame.manageqq.Network.Json;

public class MiraiMember {

    private final long id,joinTimestamp,lastSpeakTimestamp,muteTimeRemaining;
    private final String memberName,specialTitle;
    private final int permission;

    public MiraiMember(Json resource){
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
}
