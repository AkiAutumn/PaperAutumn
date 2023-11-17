package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.damaged;
import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarInvalidArguments;

public class HomeCommand implements CommandExecutor{

    long homeCooldownCommand= Long.parseLong((String) Config.get("homeCooldown_onCommand"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length == 0) {

                if (PlayerConfig.contains(player.getUniqueId() + ".home")) {

                    if (damaged.containsKey(player.getUniqueId().toString())) {
                        //player in hashmap

                        if (damaged.get(player.getUniqueId().toString()) > System.currentTimeMillis()) {
                            //still on cooldown
                            long time = (damaged.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
                            sendActionbar(player, ChatColor.RED + "" + ChatColor.BOLD + "× " + ChatColor.RESET + time + "s" + ChatColor.RED + "" + ChatColor.BOLD + " ×" );
                            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);

                        } else {
                            World world = (World) Bukkit.getWorld((String) PlayerConfig.get(player.getUniqueId().toString() + ".home.w"));
                            double x = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.x");
                            double y = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.y");
                            double z = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.z");

                            Location destination = new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
                            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 0.1, 0), 50, 0.5, 1, 0.5, 0.5, null, true);
                            player.teleport(destination);
                            damaged.put(player.getUniqueId().toString(), System.currentTimeMillis() + (homeCooldownCommand * 1000));
                            player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 0.1, 0), 50, 0.5, 1, 0.5, 0.5, null, true);

                            sendActionbar(player, (ChatColor.GREEN + "" + ChatColor.BOLD + "✓"));
                            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 2, 2);

                        }

                    } else {
                        World world = (World) Bukkit.getWorld((String) PlayerConfig.get(player.getUniqueId().toString() + ".home.w"));
                        double x = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.x");
                        double y = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.y");
                        double z = (double) PlayerConfig.get(player.getUniqueId().toString() + ".home.z");

                        Location destination = new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
                        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 0.1, 0), 50, 0.5, 1, 0.5, 1, null, true);
                        player.teleport(destination);
                        damaged.put(player.getUniqueId().toString(), System.currentTimeMillis() + (homeCooldownCommand * 1000));
                        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().add(0, 0.1, 0), 50, 0.5, 1, 0.5, 1, null, true);

                        sendActionbar(player, (ChatColor.GREEN + "" + ChatColor.BOLD + "✓"));
                        ((Player) sender).playSound(((Player) sender).getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 2, 2);
                    }
                } else {
                    sendActionbar(player, ChatColor.RED + "It seems that you haven't set a home yet - create one with /sethome");
                }
            } else {
                sendActionbarInvalidArguments(player);
            }
        }
        return true;
    }
}
