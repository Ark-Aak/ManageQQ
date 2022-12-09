package eosgame.manageqq.Configs;

import eosgame.manageqq.ManageQQ;

public class MessageConfig {

    public static String getHelp(){
        return ManageQQ.instance.getConfig().getString("Message.Help");
    }
}
