package eosgame.manageqq.Mirai.Message.MessageType;

import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Logger;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Network.Json;
import org.jetbrains.annotations.NotNull;

public class MessagePlain extends MessageBase{

    private final String text;

    public MessagePlain(Json resource) {
        super("Plain");
        text = resource.getString("text");
    }

    public MessagePlain(String text){
        super("Plain");
        this.text = text;
    }

    @Override
    public @NotNull String getText(){
        Logger.debug("Plain的getText被调用，文本：" + text);
        return text;
    }

    @Override
    public @NotNull JSONObject getAsJsonObject() {
        JSONObject res = new JSONObject();
        res.put("text",text);
        return res;
    }
}
