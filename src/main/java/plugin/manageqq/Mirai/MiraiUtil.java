package plugin.manageqq.Mirai;

import plugin.manageqq.Configs.MiraiConfig;
import plugin.manageqq.Exceptions.MiraiUnknownException;
import plugin.manageqq.Exceptions.MiraiVerifyKeyInvalidException;
import plugin.manageqq.Network.NetworkUtil;

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
}
