package eosgame.manageqq.Commands;

import eosgame.manageqq.Configs.BaseConfig;
import eosgame.manageqq.Configs.MessageConfig;
import eosgame.manageqq.Exceptions.MiraiUnknownException;
import eosgame.manageqq.Logger;
import eosgame.manageqq.ManageQQ;
import eosgame.manageqq.Mirai.MiraiBotUtil;
import eosgame.manageqq.Mirai.MiraiUtil;
import eosgame.manageqq.Utils.BindUtil;
import eosgame.manageqq.Utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mqqExecutor implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 1:
                if(args[0].equals("reload")){
                    Logger.info("重载配置...");
                    BaseConfig.reloadConfig();
                    Logger.info("重载MiraiBot模块...");
                    MiraiBotUtil.init(true);
                    Logger.info("重载完成！");
                    return true;
                }
                if(args[0].equals("botlist")){
                    sender.sendMessage(ChatColor.GOLD + "已经登录的机器人：");
                    for(long bot : MiraiBotUtil.getLoginBots()){
                        sender.sendMessage(String.valueOf(bot));
                    }
                    return true;
                }
                if(args[0].equals("getQueueSize")){
                    try {
                        sender.sendMessage(ChatColor.GREEN + "队列中的消息数量：" + MiraiUtil.getQueueMessageCount(ManageQQ.session));
                    } catch (MiraiUnknownException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                break;
            case 2:
                if(args[0].equals("bind")){
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.GOLD + "控制台无法使用此命令！");
                        return true;
                    }
                    if(BindUtil.checkToken(sender.getName(),args[1])){
                        BindUtil.addBind(sender.getName());
                        sender.sendMessage(StringUtil.coloredString(MessageConfig.getBindSuccessful()));
                        return true;
                    }
                    else{
                        sender.sendMessage(StringUtil.coloredString(MessageConfig.getBindFailed()));
                        return true;
                    }
                }
        }
        sender.sendMessage(ChatColor.RED + "貌似输入的命令有点小问题QAQ...");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandList = new ArrayList<>();
        commandList.add("reload");
        commandList.add("bind");
        commandList.add("botlist");
        if (!(sender instanceof Player)) {
            // 控制台注册个鬼
            return null;
        }
        if(args.length > 2){
            return null;
        }
        if (args.length == 0){
            return commandList;
        }
        List<String> res = new ArrayList<>();
        if (args.length == 1) {
            if(args[0].equals(""))return commandList;
            for(String cmd : commandList){
                if(cmd.startsWith(args[0])){
                    res.add(cmd);
                }
            }
            return res;
        }
        if(args.length == 2){
            if(args[0].equals("bind")){
                return Collections.singletonList("<输入bindToken>");
            }
        }
        return null;
    }
}
