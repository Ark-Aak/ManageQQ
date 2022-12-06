package plugin.manageqq.Mirai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import plugin.manageqq.Configs.MiraiConfig;
import plugin.manageqq.Network.Json;
import plugin.manageqq.Network.NetworkUtil;

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
}
