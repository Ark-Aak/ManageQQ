package lwgame.manageqq.Mirai;

import com.alibaba.fastjson2.JSONArray;
import lwgame.manageqq.Network.NetworkResponse;

public class MiraiNetworkResponse extends NetworkResponse {

    public MiraiNetworkResponse(NetworkResponse response) {
        super(response);
    }

    /**
     * 获取Response中的code字段
     *
     * @return Response中的code字段
     */
    @Deprecated
    public int getErrorCode(){
        return (int) getValue("code");
    }

    /**
     * 获取Response中的msg字段
     *
     * @return Response中的msg字段
     */
    @Deprecated
    public String getMessage(){
        return (String) getValue("msg");
    }

    /**
     * 获取Response中的data字段
     *
     * @return Response中的data字段
     */
    @Deprecated
    public Object getData(){
        return getValueJson("data");
    }

    /**
     * 获取Response中的data字段
     *
     * @return Response中的data字段
     */
    public JSONArray getDataArray(){
        return getJsonArray("data");
    }
}
