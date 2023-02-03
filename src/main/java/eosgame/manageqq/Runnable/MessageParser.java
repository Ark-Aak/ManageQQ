package eosgame.manageqq.Runnable;

import eosgame.manageqq.Configs.MiraiConfig;
import eosgame.manageqq.Utils.*;
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
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

import static eosgame.manageqq.ManageQQ.*;

public class MessageParser extends BukkitRunnable {

    private final List<MessageChain> messageChains;

    public MessageParser(List<MessageChain> messageChains){
        this.messageChains = messageChains;
    }

    @Override
    public void run() {
        Logger.debug("解析线程启动");
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
            if(lastMsg.containsKey(msg.getSender().getId()) && lastTime.containsKey(msg.getSender().getId())){
                String last = lastMsg.get(msg.getSender().getId());
                double sim = StringUtil.getSimilarityRatio(last, msg.toPlain());
                long lastSum = 0L;
                long now = new Date().getTime(), lastSend = 0L;
                if(now - lastSend <= MiraiConfig.getTimeLimit()){
                    if(SimTot.containsKey(msg.getSender().getId())){
                        lastSum = SimTot.get(msg.getSender().getId());
                    }
                    if(lastTime.containsKey(msg.getSender().getId())){
                        lastSend = lastTime.get(msg.getSender().getId());
                    }
                    long finalScore = lastSum;
                    if(sim >= MiraiConfig.getDetectLimit()){
                        if(sim >= MiraiConfig.getDetectLimit()){
                            finalScore += (long)(sim*100);
                        }
                        if(now - lastSend <= MiraiConfig.getTimeLimit()){
                            finalScore += MiraiConfig.getTimeLimit() - (now - lastSend);
                        }
                        SimTot.put(msg.getSender().getId(), finalScore);
                    }
                    else{
                        SimTot.put(msg.getSender().getId(), lastSum-Math.min(MiraiConfig.getCountDown(),lastSum));
                    }
                    if(finalScore >= MiraiConfig.getBanLimit()){
                        SimTot.put(msg.getSender().getId(), 0L);
                        if(MiraiBotUtil.hasPermission(msg.getGroup(), msg.getSender())){
                            msg.getSender().mute(MiraiConfig.getSpamMute());
                        }
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDontSpam()));
                        lastMsg.put(msg.getSender().getId(), msg.toPlain());
                        lastTime.put(msg.getSender().getId(), new Date().getTime());
                        return;
                    }
                }
                //MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("Sim value:" + sim + "\n" + "Sim total:" + SimTot.get(msg.getSender().getId())));
            }
            else{
                SimTot.put(msg.getSender().getId(), 0L);
                lastTime.put(msg.getSender().getId(), new Date().getTime());
            }
            lastMsg.put(msg.getSender().getId(), msg.toPlain());
            lastTime.put(msg.getSender().getId(), new Date().getTime());
            if (text.startsWith(MiraiConfig.getCommandPrefix())) {
                String[] args = text.split(" ");
                args[0] = args[0].substring(MiraiConfig.getCommandPrefix().length());
                Logger.debug("检测到命令" + args[0]);
                String command = args[0];
                if(args.length == 1){
                    if (command.equals("你好")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain("你好QWQ！"));
                        return;
                    }
                    if (command.equals("帮助")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getHelp()));
                        return;
                    }
                    if (args[0].equals("一言")) {
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(
                                NetworkUtil.sendGet("https://v1.hitokoto.cn","c=a&c=b&c=c&c=e&c=l&max_length=999").getString("hitokoto")
                        ));
                        return;
                    }
                    if (args[0].equals("回声洞")) {
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
                    if (args[0].equals("余额")){
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        String message = StringUtil.replacePlaceholders(MessageConfig.getBalance(),"{eco}",String.valueOf(UserUtil.getCoin(msg.getSender().getId())));
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                        return;
                    }
                    if (args[0].equals("签到")){
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        Random rand = new Random();
                        long rnd = Math.abs(rand.nextLong()) % MiraiConfig.getMaxSignInCoin() + 1;
                        if(UserUtil.canSignIn(msg.getSender().getId())){
                            UserUtil.setSignInTime(msg.getSender().getId());
                            UserUtil.addCoin(msg.getSender().getId(),rnd);
                            String message = StringUtil.replacePlaceholders(MessageConfig.getSignInSucceed(),"{eco}",String.valueOf(rnd));
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                            return;
                        }
                        else{
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getSignInFailed()));
                            return;
                        }
                    }
                }
                else if(args.length == 2){
                    if(command.equals("绑定")){
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        Document doc = BindUtil.getBindById(msg.getSender().getId());
                        if(doc != null){
                            String message = StringUtil.replacePlaceholders(MessageConfig.getHasBind(),"{player}", (String) doc.get("playerName"));
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                            return;
                        }
                        String token=BindUtil.genNewToken(msg.getSender().getId(),args[1]);
                        String message = StringUtil.replacePlaceholders(MessageConfig.getRequested(),"{command}","/mqq bind " + token);
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                        return;
                    }
                    if(command.equals("统计")){
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
                    if (command.equals("回声洞")) {
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        if(args[1].equals("投稿")) {
                            if(UserUtil.getCoin(msg.getSender().getId()) < MiraiConfig.getPutCaveCost()){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getHasNoMoney()));
                                return;
                            }
                            UserUtil.addCoin(msg.getSender().getId(), -1L * MiraiConfig.getPutCaveCost());
                            CaveUtil.addCave(args[2],msg.getSender().getMemberName(),msg.getSender().getId());
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getOK()));
                            return;
                        }
                    }
                    if (command.equals("禁言")) {
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
                    if (command.equals("绑定")){
                        if(args[1].equals("查询")){
                            try{
                                long target = Long.parseLong(args[2]);
                                Document doc = BindUtil.getBindById(target);
                                if(doc == null){
                                    MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoBind()));
                                }
                                else{
                                    String message = StringUtil.replacePlaceholders(MessageConfig.getQueryBind(),"{player}",doc.getString("playerName"));
                                    MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                                }
                            }catch (NumberFormatException e){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNotANumber()));
                            }
                            return;
                        }
                        if(args[1].equals("游戏名查询")){
                            Document doc = BindUtil.getBindByName(args[2]);
                            if(doc == null){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoBind()));
                            }
                            else{
                                String message = StringUtil.replacePlaceholders(MessageConfig.getQueryBind(),"{player}",String.valueOf(doc.getLong("bindId")));
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(message));
                            }
                            return;
                        }
                    }
                }
                else if(args.length == 4){
                    if(command.equals("余额")){
                        if(!DataBaseConfig.getEnabled()){
                            MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getDisabled()));;
                            return;
                        }
                        if(args[1].equals("增加")){
                            long target,amount;
                            try{
                                if(msg.getSender().getPermission() == 0){
                                    MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoPermission()));
                                    return;
                                }
                                target = Long.parseLong(args[2]);
                                amount = Long.parseLong(args[3]);
                            }
                            catch (NumberFormatException e){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNotANumber()));
                                return;
                            }
                            if(UserUtil.addCoin(target,amount)){
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getOK()));
                            }
                            else{
                                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getFailed()));
                            }
                            return;
                        }
                    }
                }
                List<String> regexs = MessageConfig.getRegex(), replys = MessageConfig.getReply();
                Logger.debug("准备开始匹配正则");
                for(int i=0;i<regexs.size();i++){
                    String regex = regexs.get(i), reply = replys.get(i);
                    Matcher match = RegexUtil.match(text,regex);
                    Logger.debug("尝试将"+regex+"与"+text+"进行匹配");
                    if(match.matches()){
                        Logger.debug("匹配成功");
                        Logger.debug(regex);
                        for(int j=1;j<=match.groupCount();j++){
                            String placeholder = "$" + j;
                            reply = StringUtil.replacePlaceholders(reply,placeholder,match.group(j));
                        }
                        MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(reply));
                        return;
                    }
                    else {
                        Logger.debug("匹配失败");
                    }
                }
                MiraiBotUtil.sendMessage(msg.getGroup().getId(), MessageChain.buildChain(MessageConfig.getNoCommand()));
            }
        }
        return;
    }
}
