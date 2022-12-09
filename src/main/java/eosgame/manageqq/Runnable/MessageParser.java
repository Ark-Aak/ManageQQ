package eosgame.manageqq.Runnable;

import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Logger;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Mirai.Message.MessageType.MessagePlain;
import eosgame.manageqq.Mirai.MiraiBotUtil;
import eosgame.manageqq.Network.NetworkUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MessageParser extends BukkitRunnable {

    private final List<MessageChain> messageChains;

    public MessageParser(List<MessageChain> messageChains){
        this.messageChains = messageChains;
    }

    @Override
    public void run() {
        for (MessageChain msg : messageChains) {
            String text = msg.toPlain();
            Logger.debug("消息文本：" + text);
            if (text.startsWith(".")) {
                String[] args = text.split(" ");
                Logger.debug("检测到命令" + args[0]);
                if(args.length == 1){
                    if (args[0].equals(".hello")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("你好QWQ！"));
                    }
                    if (args[0].equals(".help")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getHelp()));
                    }
                    if (args[0].equals(".hitokoto")){
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(
                                NetworkUtil.sendGet("https://v1.hitokoto.cn","c=a&c=b&c=c&c=e&c=l&max_length=999").getString("hitokoto")
                        ));
                    }
                }
            }
        }
    }
}
