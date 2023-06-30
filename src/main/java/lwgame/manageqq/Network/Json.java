package lwgame.manageqq.Network;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;

public class Json {

    private JSONObject json;

    public Json(@NotNull String res){
        json = JSON.parseObject(res);
        if(json == null){
            json = new JSONObject();
        }
    }

    public Json(){
        json = new JSONObject();
    }

    public Json(JSONObject json){
        this.json = json;
    }

    public String toJsonString(){
        return json.toJSONString();
    }

    public void set(String key,Object value){
        json.put(key,value);
    }

    public String getString(String key){
        return json.getString(key);
    }

    public long getLong(String key){
        return json.getLongValue(key);
    }

    public int getInt(String key){
        return json.getIntValue(key);
    }

    public boolean getBoolean(String key){
        return json.getBooleanValue(key);
    }

    @Deprecated
    public Object getObject(String key){return json.get(key);}

    public JSONObject getAsJsonObject(String key){
        return json.getJSONObject(key);
    }

    public JSONArray getArray(String key){
        return json.getJSONArray(key);
    }
}
