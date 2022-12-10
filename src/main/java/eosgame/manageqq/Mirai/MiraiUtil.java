package eosgame.manageqq.Mirai;

import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Exceptions.MiraiUnknownException;
import eosgame.manageqq.Exceptions.MiraiVerifyKeyInvalidException;
import eosgame.manageqq.Logger;
import eosgame.manageqq.Mirai.Message.MessageType.MessageSource;
import eosgame.manageqq.Network.Json;

public class MiraiUtil {
    /**
     * 验证verifyKey
     *
     * @param verifyKey 创建Mirai-HTTP-Server时的verifyKey
     * @return sessionKey 验证得到的sessionKey
     * @throws MiraiVerifyKeyInvalidException 当verifyKey无效时抛出
     * @throws MiraiUnknownException 当出现未知错误时抛出
     */
    public static String getSession(String verifyKey) throws MiraiVerifyKeyInvalidException, MiraiUnknownException {
        MiraiNetworkResponse response;
        response = MiraiNetworkUtil.verifySession(verifyKey);
        switch (response.getErrorCode()){
            case MiraiCode.OK:
                return (String) response.getValue("session");
            case MiraiCode.INVALID_VERIFY_KEY:
                throw new MiraiVerifyKeyInvalidException(verifyKey);
        }
        throw new MiraiUnknownException(response.getErrorCode());
    }

    /**
     * 获取未读消息数量
     *
     * @param session session实例
     * @return 未读消息的 数量
     * @throws MiraiUnknownException 出现未知错误时抛出
     */
    public static int getQueueMessageCount(MiraiSession session) throws MiraiUnknownException{
        MiraiNetworkResponse response = MiraiNetworkUtil.getQueueMessageCount(session);
        if(response.getErrorCode()!=0){
            throw new MiraiUnknownException(response.getErrorCode());
        }
        return Integer.parseInt(String.valueOf(response.getData()));
    }

    public static Json getQueueMessage(MiraiSession session,int count) throws MiraiUnknownException{
        MiraiNetworkResponse response = MiraiNetworkUtil.getQueueMessage(session,count);
        return response.getPlainResponse();
    }

    public static long hasBanWord(String word){
        String[] words = MessageConfig.getBanWord().split("\n");
        for(String str : words){
            String[] part = str.split("/");
            if(part[1].equals("true")){
                String symbol = "~!@#$%^&*()_+`-={}[]|\\;':\"<>?,./，。；、 《》“”‘’—";
                for(int i=0;i<symbol.length();i++){
                    while(word.indexOf(symbol.charAt(i)) != -1){
                        word = word.replace(symbol.substring(i,i+1),"");
                    }
                }
            }
            if(word.contains(part[0])){
                return Long.parseLong(part[2]);
            }
        }
        return 0;
    }

    public static boolean isBanPeople(long id){
        String[] people = MessageConfig.getBanPeople().split("\n");
        for(String str : people){
            if(id == Long.parseLong(str)){
                return true;
            }
        }
        return false;
    }
}
