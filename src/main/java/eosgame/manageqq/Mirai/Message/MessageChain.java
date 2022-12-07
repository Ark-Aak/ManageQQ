package eosgame.manageqq.Mirai.Message;

import com.alibaba.fastjson2.JSONArray;
import eosgame.manageqq.Mirai.Message.MessageType.MessageBase;
import eosgame.manageqq.Mirai.MiraiMember;

import java.util.ArrayList;
import java.util.List;

public class MessageChain {

    private final List<MessageBase> chain = new ArrayList<>();
    private MiraiMember sender;

    public MessageChain(){}

    public MessageChain(MiraiMember sender){
        this.sender = sender;
    }

    public void append(MessageBase message){
        chain.add(message);
    }

    public List<MessageBase> getMessageList(){
        return chain;
    }

    public int getMessageCount(){
        return chain.size();
    }

    public JSONArray toJsonString(){
        JSONArray res = new JSONArray();
        for(MessageBase msg : chain){
            res.add(msg.getAsJsonObject());
        }
        return res;
    }

    public MiraiMember getSender(){
        return sender;
    }
}
