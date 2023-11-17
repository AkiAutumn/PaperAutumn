package me.aki.paper_autumn.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null || !sender.isOp()) {

            String bar = "§cYou must be an operator to execute this command";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

        } else {
            if (args.length == 0) {
                String bar = "§cInvalid Value";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
            }
            if (args.length == 1 && player.getAllowFlight()) {
                for (String s : args) {
                    try {
                        float value = Float.parseFloat(s.trim());
                        if(value > 1 || value < -1) {
                            String bar = "§cInvalid Value";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                    } else if (value < 0) {
                            player.setFlying(true);
                            player.setFlySpeed(value);
                            String bar = "§bIt's going upside down by §3" + value;
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2, 2);

                        } else if (value == 0) {
                                player.setFlying(true);
                                player.setFlySpeed(value);
                                String bar = "§bWell.. we are freezed now";
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_PLAYER_SPLASH, 2, 2);


                            } else if (value > 0) {
                                    player.setFlying(true);
                                    player.setFlySpeed(value);
                                    String bar = "§bYour fly speed is now §3" + value;
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                                    ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_PARROT_FLY, 2, 1);
                                } else {
                                String bar = "§cInvalid Value";
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                }
            }
            if (args.length == 1 && !player.getAllowFlight()) {
                String bar = "§cIm sorry, but you're not even able to fly";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
            }
         }
        return true;
    }
}