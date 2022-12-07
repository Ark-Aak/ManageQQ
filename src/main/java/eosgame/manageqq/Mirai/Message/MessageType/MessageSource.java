package eosgame.manageqq.Mirai.Message.MessageType;

import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Network.Json;
import org.jetbrains.annotations.NotNull;

public class MessageSource extends MessageBase{

    private final int id;
    private final int time;

    public MessageSource(Json resource) {
        super("Source");
        id = (int) resource.get("id");
        time = (int) resource.get("time");
    }

    public int getId(){
        return id;
    }

    public int getTime(){
        return time;
    }

    @Override
    public @NotNull JSONObject getAsJsonObject() {
        JSONObject res = new JSONObject();
        res.put("id",id);
        res.put("time",time);
        return res;
    }
}
