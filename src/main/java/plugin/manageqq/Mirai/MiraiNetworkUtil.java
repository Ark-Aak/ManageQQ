package plugin.manageqq.Mirai;

import com.alibaba.fastjson.JSONObject;
import plugin.manageqq.Configs.MiraiConfig;
import plugin.manageqq.Network.NetworkUtil;

public class MiraiNetworkUtil {
    /**
     * 验证一个verifyKey
     *
     * @param verifyKey 配置中的verifyKey
     * @return 返回的Response
     */
    public static MiraiNetworkResponse verifySession(String verifyKey){
        JSONObject json = new JSONObject();
        json.put("verifyKey",verifyKey);
        return new MiraiNetworkResponse(NetworkUtil.sendPost(MiraiConfig.getMiraiFullUrl() + MiraiAPIList.VERIFY,json.toJSONString()));
    }

    public static MiraiNetworkResponse getLoginBots(){
        return new MiraiNetworkResponse(NetworkUtil.sendGet(MiraiConfig.getMiraiFullUrl() + MiraiAPIList.BOT_LIST,""));
    }
}
