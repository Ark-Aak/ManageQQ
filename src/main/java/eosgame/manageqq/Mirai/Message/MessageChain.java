package eosgame.manageqq.Mirai.Message;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Logger;
import eosgame.manageqq.Mirai.Message.MessageType.MessageBase;
import eosgame.manageqq.Mirai.Message.MessageType.MessagePlain;
import eosgame.manageqq.Mirai.MiraiGroup;
import eosgame.manageqq.Mirai.MiraiMember;
import jdk.jpackage.internal.Log;

import java.util.ArrayList;
import java.util.List;

public class MessageChain {

    private final List<MessageBase> chain = new ArrayList<>();
    private MiraiMember sender;
    private MiraiGroup group;

    public MessageChain(){}

    public MessageChain(MiraiMember sender,MiraiGroup group){
        this.sender = sender;
        this.group = group;
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

    public synchronized JSONArray toJsonString(){
        JSONArray res = new JSONArray();
        for(MessageBase msg : chain){
            JSONObject obj = msg.getAsJsonObject();
            obj.put("type",msg.getMessageType());
            res.add(obj);
        }
        return res;
    }

    public MiraiMember getSender(){
        return sender;
    }

    public MiraiGroup getGroup(){
        return group;
    }

    public synchronized String toPlain(){
        StringBuilder str = new StringBuilder();
        for (int i=0;i<chain.size();i++){
            Logger.debug("当前处理消息链成员：" + i);
            Logger.debug(chain.get(i).getMessageType());
            Logger.debug(chain.get(i).getText());
            str.append(chain.get(i).getText());
        }
        return String.valueOf(str);
    }

    public static MessageChain buildChain(String text){
        MessageChain res = new MessageChain();
        res.append(new MessagePlain(text));
        return res;
    }
}
