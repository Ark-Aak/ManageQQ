package eosgame.manageqq;

import eosgame.manageqq.Configs.MiraiConfig;

public class Logger {

    public static void info(String msg){
        ManageQQ.log.info(msg);
    }

    public static void error(String msg){
        ManageQQ.log.severe(msg);
    }

    public static void warn(String msg){
        ManageQQ.log.warning(msg);
    }

    public static void debug(String msg){
        if(MiraiConfig.getDebug()){
            ManageQQ.log.info(msg);
        }
    }
}
