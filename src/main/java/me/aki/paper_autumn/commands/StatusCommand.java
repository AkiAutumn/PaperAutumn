package me.aki.paper_autumn.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.aki.paper_autumn.utils.functions.getCustomChatColor;
import static me.aki.paper_autumn.utils.functions.sendActionbarInvalidArguments;

public class StatusCommand implements CommandExecutor {

    public static ArrayList<String> RPStatus = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        switch (args[0]) {

            case "rp":
            case "ic":
            case "roleplay":
            case "rollenspiel":
            case "rollenspielmodus":
                player.setDisplayName(ChatColor.RED + "● " + getCustomChatColor(player) + player.getName());
                player.setPlayerListName(ChatColor.RED + " ● " + getCustomChatColor(player) + player.getName());
                RPStatus.add(player.getUniqueId().toString());

                break;

            case "ooc":
            case "normal":
            case "reset":
            case "default":
                player.setDisplayName(getCustomChatColor(player) + player.getName());
                player.setPlayerListName(getCustomChatColor(player) + player.getName());
                RPStatus.remove(player.getUniqueId().toString());
                break;

            default:
                sendActionbarInvalidArguments(player);
        }
        return true;
    }
}
