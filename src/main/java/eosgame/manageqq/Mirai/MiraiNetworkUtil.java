package eosgame.manageqq.Mirai;

import eosgame.manageqq.Configs.MiraiConfig;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Network.Json;
import eosgame.manageqq.Network.NetworkUtil;

public class MiraiNetworkUtil {
    /**
     * 验证一个verifyKey
     *
     * @param verifyKey 配置中的verifyKey
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse verifySession(String verifyKey){
        Json json = new Json();
        json.set("verifyKey",verifyKey);
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.VERIFY),
                        json.toJsonString()
                )
        );
    }

    /**
     * 获取已经登录的机器人
     *
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse getLoginBots(){
        return new MiraiNetworkResponse(
                NetworkUtil.sendGet(
                        MiraiConfig.getMiraiApi(MiraiAPIList.BOT_LIST),
                        ""
                )
        );
    }

    /**
     * 释放session
     *
     * @param session sessionKey
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse releaseSession(MiraiSession session){
        Json json = new Json();
        json.set("sessionKey",session.sessionKey);
        json.set("qq", session.bindBot);
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.RELEASE),
                        json.toJsonString()
                )
        );
    }

    /**
     * 将session和机器人绑定
     *
     * @param session session实例
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse bindSession(MiraiSession session){
        Json json = new Json();
        json.set("sessionKey",session.sessionKey);
        json.set("qq",session.bindBot);
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.BIND),
                        json.toJsonString()
                )
        );
    }

    /**
     * 获取未读消息数量
     *
     * @param session session实例
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse getQueueMessageCount(MiraiSession session){
        return new MiraiNetworkResponse(
                NetworkUtil.sendGet(
                        MiraiConfig.getMiraiApi(MiraiAPIList.COUNT_MESSAGE),
                        "sessionKey=" + session.sessionKey
                )
        );
    }

    /**
     * 获取未读消息
     *
     * @param session session实例
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse getQueueMessage(MiraiSession session,int count){
        return new MiraiNetworkResponse(
                NetworkUtil.sendGet(
                        MiraiConfig.getMiraiApi(MiraiAPIList.FETCH_MESSAGE),
                        "sessionKey=" + session.sessionKey
                        + "&count=" + count
                )
        );
    }

    /**
     * 获取所有未读消息
     *
     * @param session session实例
     * @return Api返回的Response
     */
    public static MiraiNetworkResponse getAllQueueMessage(MiraiSession session){
        return new MiraiNetworkResponse(
                NetworkUtil.sendGet(
                        MiraiConfig.getMiraiApi(MiraiAPIList.FETCH_MESSAGE),
                        "sessionKey=" + session.sessionKey
                                + "&count=" + String.valueOf(getQueueMessageCount(session).getData())
                )
        );
    }

    public static MiraiNetworkResponse sendMessage(MiraiSession session, long target, MessageChain chain){
        Json json = new Json();
        json.set("sessionKey",session.sessionKey);
        json.set("target",target);
        json.set("messageChain",chain.toJsonString());
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.SEND_GROUP_MESSAGE),
                        json.toJsonString()
                )
        );
    }

    public static MiraiNetworkResponse getMemberInfo(MiraiSession session, long group, long target){
        return new MiraiNetworkResponse(
                NetworkUtil.sendGet(
                        MiraiConfig.getMiraiApi(MiraiAPIList.GET_MEMBER_INFO),
                        "sessionKey=" + session.sessionKey
                        + "&target=" + group
                        + "&memberId=" + target
                )
        );
    }

    public static MiraiNetworkResponse muteMember(MiraiSession session, long group, long target, long time){
        Json json = new Json();
        json.set("sessionKey",session.sessionKey);
        json.set("target",group);
        json.set("memberId",target);
        json.set("time",time);
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.MUTE_MEMBER),
                        json.toJsonString()
                )
        );
    }

    public static MiraiNetworkResponse recall(MiraiSession session, long target, long id){
        Json json = new Json();
        json.set("sessionKey",session.sessionKey);
        json.set("target",target);
        json.set("messageId",id);
        return new MiraiNetworkResponse(
                NetworkUtil.sendPost(
                        MiraiConfig.getMiraiApi(MiraiAPIList.RECALL),
                        json.toJsonString()
                )
        );
    }
}
