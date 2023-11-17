package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static me.aki.paper_autumn.utils.functions.sendActionbar;


public class noDamageCommand implements CommandExecutor {

    public static ArrayList<String> invulnerable = new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player) || !sender.hasPermission("roleplayutils.invulnerability")) {
            String bar = "§cYou've not allowed to do that";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        } else {

            if (args.length == 0 && !player.isInvulnerable()) {
                player.setInvulnerable(true);
                invulnerable.add(player.getUniqueId().toString());

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(player.isInvulnerable()){
                            sendActionbar(player, ChatColor.RED + "You're invulnerable");
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class), 0, 60);

            } else {

                if (args.length == 0) {
                    player.setInvulnerable(false);
                    invulnerable.remove(player.getUniqueId().toString());
                    String bar = "§aYou're not Invulnerable anymore";
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                } else {

                    Player target = sender.getServer().getPlayer(args[0]);

                    if (target == null) {
                        String bar = "§cCouldn't find this player";
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                        return true;

                    } else {

                        if(args.length == 1 && !target.isInvulnerable()){
                            target.setInvulnerable(true);
                            invulnerable.add(target.getUniqueId().toString());
                            String bar = "§aYou've made §2" + target.getName() + "§a Invulnerable";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    if(target.isInvulnerable()){
                                        sendActionbar(target, ChatColor.RED + "You're invulnerable");
                                    } else {
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(Main.getPlugin(Main.class), 0, 60);

                        }else{
                            target.setInvulnerable(false);
                            invulnerable.remove(target.getUniqueId().toString());
                            String bar = "§aYou've made §2" + target.getName() + "§a no longer Invulnerable";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                            String bar1 = "§aYou're not Invulnerable anymore";
                            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar1));
                        }
                    }
                }
            }
        }
        return true;
    }
}