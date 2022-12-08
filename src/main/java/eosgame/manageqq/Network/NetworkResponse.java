package eosgame.manageqq.Network;

import com.alibaba.fastjson2.JSONArray;
import eosgame.manageqq.Logger;

public class NetworkResponse {

    Json json;

    public NetworkResponse(String resource){
        json = new Json(resource);
    }

    public NetworkResponse(Json resource){
        json = resource;
    }

    public NetworkResponse(NetworkResponse response){
        json = response.json;
    }

    /**
     * 获取响应原文的JSON对象
     *
     * @return 响应原文的JSON对象
     */
    public Json getPlainResponse(){
        return json;
    }

    /**
     * 获取响应原文JSON中key对应的value
     *
     * @param key 键名
     * @return key对应的value
     */
    @Deprecated
    public Object getValue(Object key){
        return json.getObject(String.valueOf(key));
    }

    public int getInt(String key){
        return json.getInt(key);
    }

    public long getLong(String key){
        return json.getLong(key);
    }

    public String getString(String key){
        return json.getString(key);
    }

    public Boolean getBoolean(String key){
        return json.getBoolean(key);
    }

    /**
     * 获取某个key下的JSON对象
     *
     * @param key 键名
     * @return key下的JSON对象
     */
    @Deprecated
    public Object getValueJson(Object key){
        return json.getObject(String.valueOf(key));
    }

    /**
     * 获取某个key下的JSON数组对象
     *
     * @param key 键名
     * @return key下的JSON数组对象
     */
    public JSONArray getJsonArray(Object key){
        return json.getArray(String.valueOf(key));
    }
}