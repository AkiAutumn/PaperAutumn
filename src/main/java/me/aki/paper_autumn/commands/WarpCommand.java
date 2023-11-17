package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.damaged;
import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.particles.drawCircle;


public class WarpCommand implements CommandExecutor {

    private Main plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            return false;

        }else{

            if(args.length == 0){
                String bar = "§cUse /warp <name>";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
            }

            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {

                    sendActionbar(player, ChatColor.GRAY + "Use " + ChatColor.GREEN + "/warp add or remove <name>");

                } else {

                    String name = args[0].toLowerCase();
                    if (Config.contains("warps." + name + ".world")) {

                        String worldName = Config.get("warps." + name + ".world").toString();

                        if (Config.contains("warps." + name)) {
                            if (Bukkit.getWorld(worldName) != null) {
                                if (damaged.containsKey(player.getUniqueId().toString())) {
                                    //player in hashmap

                                    if (damaged.get(player.getUniqueId().toString()) > System.currentTimeMillis()) {
                                        //still on cooldown
                                        long time = (damaged.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
                                        sendActionbar(player, ChatColor.RED + "" + ChatColor.BOLD + "× " + ChatColor.RESET + time + "s" + ChatColor.RED + "" + ChatColor.BOLD + " ×" );
                                        ((Player) sender).playSound(((Player) sender).getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);

                                    } else {
                                        warp(name, player);
                                        String bar = "You've teleported to §6" + name;
                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                                    }

                                } else {
                                    warp(name, player);
                                    String bar = "You've teleported to §6" + name;
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                                }
                            } else {

                                sendActionbar(player, ChatColor.RED + "Couldn't find the world, in which the warp is - is it loaded?");

                            }
                        } else {

                            String bar = "§cWarp §4" + name + "§c doesn't exist";
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                        }
                    } else {
                        sendActionbar(player, ChatColor.RED + "There are no warps created yet");
                    }
                }
            }

            if(args.length == 2 && sender.isOp()){
                if(args[0].equalsIgnoreCase("add")){

                    String name = args[1].toLowerCase();

                    if(Config.contains("warps." + name)){

                        String bar = "§cWarp §4" + name + "§c already exists";
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                    } else {

                        addWarp(name, player);
                    }
                }

                if(args[0].equalsIgnoreCase("remove")){

                    String name = args[1];
                    removeWarp(name, player);

                }
            }
        }
        return false;
    }

    //Methods

    public void removeWarp(String name, Player player){

        try {
            Config.set("warps." + name, null);
            String bar = "§aWarp §2" + name + "§a removed";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWarp(String name, Player player){
        Location location = player.getLocation();
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        try {
            Config.set("warps." + name + ".world", world);
            Config.set("warps." + name + ".x", x);
            Config.set("warps." + name + ".y", y);
            Config.set("warps." + name + ".z", z);
            String bar = "§aWarp §2" + name + "§a created";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warp(String name, Player player){
        String world = (String) Config.get("warps." + name + ".world");
        double x = (double) Config.get("warps." + name + ".x");
        double y = (double) Config.get("warps." + name + ".y");
        double z = (double) Config.get("warps." + name + ".z");
        float yaw = (float) player.getLocation().getYaw();

        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, 0);

        player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1, 1);
        drawCircle(player.getLocation().add(0, 1, 0), (float) 1.5, Particle.CLOUD, null);
        player.teleport(location);
        drawCircle(player.getLocation().add(0, 1, 0), (float) 1.5, Particle.CLOUD, null);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 2);
    }
}