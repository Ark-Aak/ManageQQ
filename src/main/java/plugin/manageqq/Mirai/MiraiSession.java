package plugin.manageqq.Mirai;

import plugin.manageqq.Exceptions.MiraiBotOfflineException;

public class MiraiSession {

    String sessionKey;
    long bindBot;

    public MiraiSession(String session){
        this.sessionKey = session;
    }

    /**
     * 将Session与机器人绑定
     *
     * @param botId 机器人的QQ号
     * @throws MiraiBotOfflineException 当机器人未登录时抛出
     */
    public void bindBotId(long botId) throws MiraiBotOfflineException {
        if(!MiraiBotUtil.isBotLogin(botId)){
            throw new MiraiBotOfflineException(botId);
        }
        this.bindBot = botId;
    }
}
