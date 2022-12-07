package eosgame.manageqq.Mirai.Message.MessageType;

import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;

public abstract class MessageBase {

    private final String messageType;

    public MessageBase(String messageType){
        this.messageType = messageType;
    }

    public String getMessageType(){
        return messageType;
    }

    @NotNull
    public abstract JSONObject getAsJsonObject();
}
