package lwgame.manageqq.Configs;

import lwgame.manageqq.ManageQQ;

public class InvitationConfig {

    public static long getCodeLength(){
        return ManageQQ.instance.getConfig().getLong("Invitation.CodeLength");
    }
}
