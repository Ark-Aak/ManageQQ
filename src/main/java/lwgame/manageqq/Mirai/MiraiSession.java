package lwgame.manageqq.Mirai;

import lwgame.manageqq.Exceptions.MiraiBotDoesNotExistException;
import lwgame.manageqq.Exceptions.MiraiBotOfflineException;
import lwgame.manageqq.Exceptions.MiraiUnknownException;

public class MiraiSession {

    public String sessionKey;
    public long bindBot;

    public MiraiSession(String session){
        this.sessionKey = session;
    }

    /**
     * 将Session与机器人绑定
     *
     * @param botId 机器人的QQ号
     * @throws MiraiBotOfflineException 当机器人未登录时抛出
     * @throws MiraiUnknownException 当出现未知错误时抛出
     */
    public void bindBotId(long botId) throws MiraiBotOfflineException,MiraiUnknownException {
        if(!MiraiBotUtil.isBotLogin(botId)){
            throw new MiraiBotOfflineException(botId);
        }
        this.bindBot = botId;
        int code=MiraiNetworkUtil.bindSession(this).getErrorCode();
        if(code != 0){
            throw new MiraiUnknownException(code);
        }
    }

    /**
     * 释放一个sessionKey
     *
     * @throws MiraiBotDoesNotExistException 当sessionKey和botId对应不上时抛出
     */
    public void releaseSession() throws MiraiBotDoesNotExistException, MiraiUnknownException {
        MiraiNetworkResponse response = MiraiNetworkUtil.releaseSession(this);
        if(response.getErrorCode() == 2){
            throw new MiraiBotDoesNotExistException(sessionKey,bindBot);
        }
        else if(response.getErrorCode() != 0){
            throw new MiraiUnknownException(response.getErrorCode());
        }
    }
}
