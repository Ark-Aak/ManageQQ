package eosgame.manageqq.Mirai;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import eosgame.manageqq.Mirai.Message.MessageChain;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static eosgame.manageqq.ManageQQ.session;

public class MiraiMessageGetter extends BukkitRunnable {

    @Override
    public void run() {
        List<MessageChain> messageChains = new ArrayList<>();
        MiraiNetworkResponse response = MiraiNetworkUtil.getAllQueueMessage(session);
        JSONArray data = response.getDataArray();
        for(int i=0;i<data.size();i++){
            JSONObject object = data.getJSONObject(i);
            if (object.getString("type").equals("GroupMessage")) {
                JSONArray array = object.getJSONArray("messageChain");

            }
        }
    }
}
