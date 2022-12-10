package eosgame.manageqq.Runnable;

import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Logger;
import eosgame.manageqq.ManageQQ;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Mirai.Message.MessageType.MessagePlain;
import eosgame.manageqq.Mirai.MiraiBotUtil;
import eosgame.manageqq.Mirai.MiraiMember;
import eosgame.manageqq.Mirai.MiraiNetworkUtil;
import eosgame.manageqq.Mirai.MiraiUtil;
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
            if(MiraiBotUtil.hasPermission(msg.getGroup(),msg.getSender())){
                long muteTime = MiraiUtil.hasBanWord(text);
                if(MiraiUtil.isBanPeople(msg.getSender().getId()) || muteTime != 0){
                    if(muteTime != 0){
                        msg.getSender().mute(muteTime);
                    }
                    MiraiNetworkUtil.recall(ManageQQ.session,msg.getGroup().getId(),msg.getMsgId());
                    MiraiBotUtil.sendMessage(msg.getGroup().getId(),MessageChain.buildChain(MessageConfig.getRecall()));
                    return;
                }
            }
            if (text.startsWith(".")) {
                String[] args = text.split(" ");
                Logger.debug("检测到命令" + args[0]);
                if(args.length == 1){
                    if (args[0].equals(".hello")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("你好QWQ！"));
                        return;
                    }
                    if (args[0].equals(".help")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getHelp()));
                        return;
                    }
                    if (args[0].equals(".hitokoto")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(
                                NetworkUtil.sendGet("https://v1.hitokoto.cn","c=a&c=b&c=c&c=e&c=l&max_length=999").getString("hitokoto")
                        ));
                        return;
                    }
                    if (args[0].equals(".cave")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getInDeveloping()));
                        return;
                    }
                }
                else if(args.length == 3){
                    if (args[0].equals(".cave")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getInDeveloping()));
                        return;
                    }
                    if (args[0].equals(".mute")) {
                        try{
                            if(msg.getSender().getPermission() == 0){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoPermission()));
                                return;
                            }
                            MiraiMember member = new MiraiMember(Long.parseLong(args[1]),msg.getGroup());
                            if(!MiraiBotUtil.hasPermission(msg.getGroup(),member)){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getBotNoPermission()));
                            }
                            member.mute(Long.parseLong(args[2]));
                        }
                        catch (NumberFormatException e){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNotANumber()));
                        }
                        return;
                    }
                }
                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoCommand()));
            }
        }
        return;
    }
}
