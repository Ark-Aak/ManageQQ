package eosgame.manageqq.Runnable;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Logger;
import eosgame.manageqq.ManageQQ;
import eosgame.manageqq.Mirai.*;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Mirai.Message.MessageType.MessagePlain;
import eosgame.manageqq.Mirai.Message.MessageType.MessageSource;
import eosgame.manageqq.Mirai.Message.MessageTypeList;
import eosgame.manageqq.Network.Json;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static eosgame.manageqq.ManageQQ.session;

public class MessageGetter extends BukkitRunnable {

    @Override
    public void run() {
        List<MessageChain> messageChains = new ArrayList<>();
        if((int) MiraiNetworkUtil.getQueueMessageCount(session).getData() == 0){
            Logger.debug("没有收到消息...");
            return;
        }
        MiraiNetworkResponse response = MiraiNetworkUtil.getAllQueueMessage(session);
        JSONArray data = response.getDataArray();
        for(int i=0;i<data.size();i++){
            JSONObject object = data.getJSONObject(i);
            if(!object.getString("type").equals(MessageTypeList.GROUP_MESSAGE)){
                //暂时还没写好友消息处理模块，先跳过了XD
                continue;
            }
            MessageChain messageChain = new MessageChain(
                    new MiraiMember(
                            new Json(data.getJSONObject(i).getJSONObject("sender"))
                    ),
                    new MiraiGroup(
                            new Json(data.getJSONObject(i).getJSONObject("sender").getJSONObject("group"))
                    )
            );
            JSONArray array = object.getJSONArray("messageChain");
            Logger.debug("messageChain： " + array.toJSONString());
            for(int j=0;j<array.size();j++){
                Json json = new Json(array.getJSONObject(j));
                Logger.debug("处理消息链Json元素" + j);
                Logger.debug(json.toJsonString());
                switch (json.getString("type")){
                    case MessageTypeList.SOURCE:
                        messageChain.append(new MessageSource(json));
                        break;
                    case MessageTypeList.PLAIN:
                        messageChain.append(new MessagePlain(json));
                        break;
                }
            }
            messageChains.add(messageChain);
        }
        for (MessageChain msg : messageChains) {
            String text = msg.toPlain();
            Logger.debug("消息文本：" + text);
            if (text.startsWith(".")) {
                String[] args = text.split(" ");
                Logger.debug("检测到命令" + args[0]);
                if(args.length == 1){
                    if (args[0].equals(".hello")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("让我看看这玩意到底有没有问题！"));
                    }
                }
                else if(args.length == 2){
                    if (args[0].equals(".say")){
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(args[1]));
                    }
                }
            }
        }
    }
}
