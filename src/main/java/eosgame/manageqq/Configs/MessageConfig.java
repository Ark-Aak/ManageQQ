package eosgame.manageqq.Configs;

import eosgame.manageqq.ManageQQ;

public class MessageConfig {

    public static String getHelp(){
        return ManageQQ.instance.getConfig().getString("Message.Help");
    }

    public static String getNoPermission(){
        return ManageQQ.instance.getConfig().getString("Message.NoPermission");
    }

    public static String getNoCommand(){
        return ManageQQ.instance.getConfig().getString("Message.NoCommand");
    }

    public static String getBotNoPermission(){
        return ManageQQ.instance.getConfig().getString("Message.BotNoPermission");
    }

    public static String getNotANumber(){
        return ManageQQ.instance.getConfig().getString("Message.NotANumber");
    }

    public static String getInDeveloping(){
        return ManageQQ.instance.getConfig().getString("Message.InDeveloping");
    }

    public static String getBanWord(){
        return ManageQQ.instance.getConfig().getString("Bot.BanWord");
    }

    public static String getBanPeople(){
        return ManageQQ.instance.getConfig().getString("Bot.BanPeople");
    }

    public static String getRecall(){
        return ManageQQ.instance.getConfig().getString("Message.Recall");
    }
}
