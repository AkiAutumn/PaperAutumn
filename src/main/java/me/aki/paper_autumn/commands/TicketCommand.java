package me.aki.paper_autumn.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.aki.paper_autumn.utils.functions.getCustomChatColor;

public class TicketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length > 0) {

            String[] modifiedArray = Arrays.copyOfRange(args, 0, args.length);
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < modifiedArray.length; i++) {
                sb.append(modifiedArray[i]);
                sb.append(" ");
            }
            String msg = sb.toString();

            TextComponent ticket = new TextComponent(ChatColor.WHITE + "> " + ChatColor.DARK_RED + "Ticket" + ChatColor.GRAY + " [" + getCustomChatColor(player) + player.getName() + ChatColor.GRAY + "] " + ChatColor.RESET + msg);
            ticket.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

            int adminCount = 0;
            for(Player players : Bukkit.getOnlinePlayers()){
                if(players.isOp()){
                    adminCount ++;
                    players.spigot().sendMessage(ticket);
                    players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0);
                }
            }

            if(adminCount == 0){
                player.sendMessage(org.bukkit.ChatColor.RED + "There are no Admins online at the moment :(");
            }
        } else {
            player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.WHITE + "/ticket <msg>" + ChatColor.GRAY + " to alarm an Admin about your problem");
        }

        return true;
    }
}
