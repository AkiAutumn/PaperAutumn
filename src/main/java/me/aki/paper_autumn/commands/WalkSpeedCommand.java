package me.aki.paper_autumn.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarNotOP;

public class WalkSpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

            if (args.length == 0) {
                String bar = "§cInvalid Value";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
            }
            if (args.length == 1) {
                for (String s : args) {
                    try {
                        float value = Float.parseFloat(s.trim());
                        if(value > 1 || value < -1) {
                            String bar = "§cInvalid Value";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                        } else {
                            if(value >= 0.2000001){
                                if(player.isOp()){
                                    player.setWalkSpeed(value);
                                    sendActionbar(player, ChatColor.WHITE + "Your walk speed is now " + ChatColor.AQUA + value + ChatColor.GRAY + " (default is 0.2)");
                                } else {
                                    sendActionbarNotOP(player);
                                }
                            } else {
                                player.setWalkSpeed(value);
                                sendActionbar(player, ChatColor.WHITE + "Your walk speed is now " + ChatColor.AQUA + value + ChatColor.GRAY + " (default is 0.2)");
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                }
            }
        return true;
    }

}
