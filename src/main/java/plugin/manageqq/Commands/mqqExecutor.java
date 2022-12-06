package plugin.manageqq.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.manageqq.Configs.BaseConfig;
import plugin.manageqq.Exceptions.MiraiUnknownException;
import plugin.manageqq.Logger;
import plugin.manageqq.ManageQQ;
import plugin.manageqq.Mirai.MiraiBotUtil;
import plugin.manageqq.Mirai.MiraiUtil;

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
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
