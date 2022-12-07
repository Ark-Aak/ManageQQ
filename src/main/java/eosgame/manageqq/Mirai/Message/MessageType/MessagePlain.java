package eosgame.manageqq.Mirai.Message.MessageType;

import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Network.Json;
import org.jetbrains.annotations.NotNull;

public class MessagePlain extends MessageBase{

    private final String text;

    public MessagePlain(Json resource) {
        super("Plain");
        text = (String) resource.get("text");
    }

    public String getText(){
        return text;
    }

    @Override
    public @NotNull JSONObject getAsJsonObject() {
        JSONObject res = new JSONObject();
        res.put("text",text);
        return res;
    }
}
