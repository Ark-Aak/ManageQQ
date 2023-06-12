package eosgame.manageqq.Configs;

import eosgame.manageqq.ManageQQ;

public class InvitationConfig {

    public static long getCodeLength(){
        return ManageQQ.instance.getConfig().getLong("Invitation.CodeLength");
    }
}
