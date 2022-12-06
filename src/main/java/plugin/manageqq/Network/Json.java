package plugin.manageqq.Network;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import plugin.manageqq.Logger;

public class Json {

    private JSONObject json;

    public Json(String res){
        //json = .getAsJsonObject();
        json = JSON.parseObject(res);
    }

    public Json(){
        json = new JSONObject();
    }

    public String toJsonString(){
        return json.toJSONString();
    }

    public void set(String key,Object value){
        json.put(key,value);
    }

    public Object get(String key){
        return json.get(key);
    }

    public JSONArray getArray(String key){
        return json.getJSONArray(key);
    }
}
