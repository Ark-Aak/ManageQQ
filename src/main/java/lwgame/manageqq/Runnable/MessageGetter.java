package lwgame.manageqq.Runnable;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lwgame.manageqq.Configs.MiraiConfig;
import lwgame.manageqq.Logger;
import lwgame.manageqq.ManageQQ;
import lwgame.manageqq.Mirai.*;
import lwgame.manageqq.Mirai.Message.MessageChain;
import lwgame.manageqq.Mirai.Message.MessageType.MessagePlain;
import lwgame.manageqq.Mirai.Message.MessageType.MessageSource;
import lwgame.manageqq.Mirai.Message.MessageTypeList;
import lwgame.manageqq.Mirai.MiraiGroup;
import lwgame.manageqq.Mirai.MiraiMember;
import lwgame.manageqq.Network.Json;
import lwgame.manageqq.Mirai.MiraiNetworkResponse;
import lwgame.manageqq.Mirai.MiraiNetworkUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MessageGetter extends BukkitRunnable {

    @Override
    public void run() {
        if(!MiraiConfig.getEnable()){
            return;
        }
        List<MessageChain> messageChains = new ArrayList<>();
        if((int) MiraiNetworkUtil.getQueueMessageCount(ManageQQ.session).getData() == 0){
            Logger.debug("没有收到消息...");
            return;
        }
        MiraiNetworkResponse response = MiraiNetworkUtil.getAllQueueMessage(ManageQQ.session);
        JSONArray data = response.getDataArray();
        for(int i=0;i<data.size();i++){
            JSONObject object = data.getJSONObject(i);
            if(!object.getString("type").equals(MessageTypeList.GROUP_MESSAGE)){
                //暂时还没写好友消息处理模块，先跳过了XD
                continue;
            }
            MessageChain messageChain = new MessageChain(
                    new MiraiMember(
                            new Json(data.getJSONObject(i).getJSONObject("sender")),
                            new MiraiGroup(
                                    new Json(data.getJSONObject(i).getJSONObject("sender").getJSONObject("group"))
                            )
                    )
            );
            JSONArray array = object.getJSONArray("messageChain");
            Logger.debug("messageChain： " + array.toJSONString());
            for(int j=0;j<array.size();j++){
                Json json = new Json(array.getJSONObject(j));
                Logger.debug("处理消息链Json元素" + j);
                Logger.debug("类型" + json.toJsonString());
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
        Logger.debug("尝试启动解析线程");
        new MessageParser(messageChains).runTaskAsynchronously(ManageQQ.instance);
    }
}
