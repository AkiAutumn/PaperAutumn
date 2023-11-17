package me.aki.paper_autumn.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class HideCommand implements CommandExecutor {

    public static ArrayList<Player> hidden = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player) || !sender.hasPermission("roleplayutils.hide")) {

            String bar = "§cYou're not allowed to execute this command";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

        } else {

            if (args.length == 0 && hidden.contains(player)) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.showPlayer(player);
                }
                hidden.remove(player);

                String bar = "§7You're not hidden anymore";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.UI_TOAST_IN, 2, 0);
                player.spawnParticle(Particle.CLOUD, player.getLocation(), 5, 0.1, 0, 0.1, 0.01);
                player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 10, 0.75, 0, 0.75, 0.01);
                player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 2, 0), 5, 0.1, 0, 0.1, 0.01);

            } else {

                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.hidePlayer(player);
                }
                hidden.add(player);

                String bar = "§7You're now hidden";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.UI_TOAST_OUT, 2, 2);
                player.spawnParticle(Particle.CLOUD, player.getLocation(), 5, 0.1, 0, 0.1, 0.01);
                player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 10, 0.75, 0, 0.75, 0.01);
                player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 2, 0), 5, 0.1, 0, 0.1, 0.01);
            }
        }
        return true;
    }
}


