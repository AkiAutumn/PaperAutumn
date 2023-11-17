package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class PlayerMoveListener implements Listener {

    String obj1 = String.valueOf(Config.get("worldedgetp.negZ"));
    String obj2 = String.valueOf(Config.get("worldedgetp.posX"));
    String obj3 = String.valueOf(Config.get("worldedgetp.posZ"));
    String obj4 = String.valueOf(Config.get("worldedgetp.negX"));
    long negZ = Long.valueOf(obj1);
    long posX = Long.valueOf(obj2);
    long posZ = Long.valueOf(obj3);
    long negX = Long.valueOf(obj4);
    String worldEdgeTP = (String) Config.get("worldedgetp.status");

    public static Map<String, Float> pastPlayerFalldistance = new HashMap<String, Float>();
    public static ArrayList<String> glidedPlayers = new ArrayList<String>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();

        if (player.getInventory().getChestplate() != null) {
            if (player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null) {
                        if (item.equals(SavedItems.specialFeather()) || item.equals(SavedItems.oneTimeElytra())) {
                            if (player.isGliding()) {
                                if(!glidedPlayers.contains(player.getUniqueId().toString())) {
                                    glidedPlayers.add(player.getUniqueId().toString());
                                }
                                if (player.isSneaking()) {
                                    player.setVelocity(player.getVelocity().multiply(1.01));
                                    player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation(), 2, .2, .2, .2, .1);
                                    player.getWorld().spawnParticle(Particle.SCRAPE, player.getLocation(), 1, .1, .1, .1, .1);
                                }
                            }
                        }
                    }
                }

                if(glidedPlayers.contains(player.getUniqueId().toString())){
                    if(player.isInWater() || player.isOnGround()){
                        if(player.getInventory().getChestplate().equals(SavedItems.oneTimeElytra())) {
                            player.getInventory().setChestplate(null);
                            glidedPlayers.remove(player.getUniqueId().toString());
                        }
                    }
                }
            }
        }



        if ((Boolean) Config.get("antiwatermlg.enabled")) {

            Block block = player.getLocation().add(0, -1, 0).getBlock();
            Block blockAbove = player.getLocation().getBlock();

            if (block.isSolid()) {
                if (!block.getType().equals(Material.WATER) && !blockAbove.getType().equals(Material.WATER)) {
                    pastPlayerFalldistance.put(player.getUniqueId().toString(), (float) 0);
                }
            }

            if(!player.isGliding()) {
                if (pastPlayerFalldistance.containsKey(player.getUniqueId().toString())) {
                    Float maxFalldistance = pastPlayerFalldistance.get(player.getUniqueId().toString());
                    if (player.getFallDistance() > maxFalldistance) {
                        pastPlayerFalldistance.put(player.getUniqueId().toString(), player.getFallDistance());
                    }
                    if (maxFalldistance > 16) {
                        if (block.getType().equals(Material.WATER) || blockAbove.getType().equals(Material.WATER)) {
                            player.damage(maxFalldistance / (double) Config.get("antiwatermlg.divideDamageBy"));
                            pastPlayerFalldistance.put(player.getUniqueId().toString(), (float) 0);
                        }
                    }
                } else {
                    pastPlayerFalldistance.put(player.getUniqueId().toString(), player.getFallDistance());
                }
            }
        }

        if (ghosts.containsKey(player.getUniqueId().toString())) {
            player.spawnParticle(Particle.SOUL, player.getLocation().add(0, 0.1, 0), 1, 0.1, 0.1, 0.1, 0.1);
        }

        assert worldEdgeTP != null;
        if ("true".equalsIgnoreCase(worldEdgeTP)) {
            Location location = player.getLocation();
            Location newLocation = location;

            if (location.getX() > posX) {
                newLocation = new Location(location.getWorld(), negX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgePlayerTP(player, newLocation);
            }
            if (location.getX() < negX) {
                newLocation = new Location(location.getWorld(), posX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgePlayerTP(player, newLocation);
            }
            if (location.getZ() > posZ) {
                newLocation = new Location(location.getWorld(), location.getX(), location.getY() + 1, negZ, location.getYaw(), location.getPitch());
                worldEdgePlayerTP(player, newLocation);
            }
            if (location.getZ() < negZ) {
                newLocation = new Location(location.getWorld(), location.getX(), location.getY() + 1, posZ, location.getYaw(), location.getPitch());
                worldEdgePlayerTP(player, newLocation);
            }
        }
    }

    public void worldEdgePlayerTP(Player player, Location newLocation) {
        if(player.getWorld().equals(Bukkit.getWorld("world"))) {
            if(!player.isInsideVehicle()) {
                player.getWorld().spawnParticle(Particle.FLASH, player.getEyeLocation(), 10, 0, 0, 0, 0, null, true);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getEyeLocation(), 50, 1.5, 1.5, 1.5, 0, null, true);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 2, 2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 4, false, false, false));

                player.teleport(newLocation);

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getEyeLocation(), 50, 1.5, 1.5, 1.5, 0, null, true);
                player.getWorld().spawnParticle(Particle.FLASH, player.getEyeLocation(), 10, 0, 0, 0, 0, null, true);
            }
        }
    }
}
