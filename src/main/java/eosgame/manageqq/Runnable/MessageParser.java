package eosgame.manageqq.Runnable;

import eosgame.manageqq.Configs.MiraiConfig;
import eosgame.manageqq.Utils.BindUtil;
import eosgame.manageqq.Utils.CaveUtil;
import eosgame.manageqq.Configs.DataBaseConfig;
import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Logger;
import eosgame.manageqq.ManageQQ;
import eosgame.manageqq.Mirai.Message.MessageChain;
import eosgame.manageqq.Mirai.MiraiBotUtil;
import eosgame.manageqq.Mirai.MiraiMember;
import eosgame.manageqq.Mirai.MiraiNetworkUtil;
import eosgame.manageqq.Mirai.MiraiUtil;
import eosgame.manageqq.Network.NetworkUtil;
import eosgame.manageqq.Utils.StringUtil;
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static eosgame.manageqq.ManageQQ.*;

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
            if(lastMsg.containsKey(msg.getSender().getId())){
                String last = lastMsg.get(msg.getSender().getId());
                double sim = StringUtil.getSimilarityRatio(last, msg.toPlain());
                long lastSum = 0L;
                if(SimTot.containsKey(msg.getSender().getId())){
                    lastSum = SimTot.get(msg.getSender().getId());
                }
                if(sim >= MiraiConfig.getDetectLimit()){
                    SimTot.put(msg.getSender().getId(), lastSum+(long)(sim*100));
                }
                else{
                    SimTot.put(msg.getSender().getId(), lastSum-Math.min(MiraiConfig.getCountDown(),lastSum));
                }
                if(lastSum+(long)(sim*100) >= MiraiConfig.getBanLimit()){
                    SimTot.put(msg.getSender().getId(), 0L);
                    if(MiraiBotUtil.hasPermission(msg.getGroup(), msg.getSender())){
                        msg.getSender().mute(MiraiConfig.getSpamMute());
                    }
                    MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDontSpam()));
                    lastMsg.put(msg.getSender().getId(), msg.toPlain());
                    return;
                }
                //MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("Sim value:" + sim + "\n" + "Sim total:" + SimTot.get(msg.getSender().getId())));
            }
            else{
                SimTot.put(msg.getSender().getId(), 0L);
            }
            lastMsg.put(msg.getSender().getId(), msg.toPlain());
            lastTime.put(msg.getSender().getId(), new Date().getTime());
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
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        Document doc = CaveUtil.getCave();
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(
                                String.valueOf(doc.get("content")) + "\n——" + String.valueOf(doc.get("sender"))
                        ));
                        return;
                    }
                }
                else if(args.length == 2){
                    if(args[0].equals(".bind")){
                        Document doc = BindUtil.getBindById(msg.getSender().getId());
                        if(doc != null){
                            String message = MessageConfig.getHasBind();
                            while(message.contains("{player}")){
                                message = message.replace((CharSequence) "{player}", (CharSequence) doc.get("playerName"));
                            }
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                            return;
                        }
                        String token=BindUtil.genNewToken(msg.getSender().getId(),args[1]);
                        String message = MessageConfig.getRequested();
                        while(message.contains("{command}")){
                            message = message.replace("{command}","/mqq bind " + token);
                        }
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                        return;
                    }
                    if(args[0].equals(".stats")){
                        long target;
                        try{
                            target = Long.parseLong(args[1]);
                        }catch (Exception e){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNotANumber()));
                            return;
                        }
                        if(!SimTot.containsKey(target)){
                            SimTot.put(target,0L);
                        }
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(String.valueOf("Spam Score:" + SimTot.get(target))));
                        return;
                    }
                }
                else if(args.length == 3){
                    if (args[0].equals(".cave")) {
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        if(args[1].equals("put")) {
                            CaveUtil.addCave(args[2],msg.getSender().getMemberName(),msg.getSender().getId());
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getOK()));
                            return;
                        }
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
