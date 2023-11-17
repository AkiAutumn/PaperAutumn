package me.aki.paper_autumn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.aki.paper_autumn.utils.functions.*;

public class RemoteChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!sender.isOp()) {
            sendActionbarNotOP(player);
        } else if (args.length >= 2) {
            Player target = sender.getServer().getPlayer(args[0]);
            if(Bukkit.getOnlinePlayers().contains(target)){
                String[]croppedArgs = Arrays.copyOfRange(args, 1, args.length);
                String msg = String.join(" ", croppedArgs);

                target.chat(msg);
                sendActionbarSuccess(player);
            } else {
                sendActionbarPlayerNotFound(player);
            }
        }
        return false;
    }
}
