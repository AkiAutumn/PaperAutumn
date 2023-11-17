package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static me.aki.paper_autumn.commands.HideCommand.hidden;
import static me.aki.paper_autumn.commands.noDamageCommand.invulnerable;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class EntityDamageEvent implements Listener {

    public static Map<String, Long> damaged = new HashMap<String, Long>();
    public static Map<String, Long> ghosts = new HashMap<String, Long>();
    long homeCooldownDamage = Long.parseLong((String) Config.get("homeCooldown_onDamage"));

    Object object = Config.get("deathGhost");
    String string = Objects.requireNonNull(object).toString();

    @EventHandler
    public void onDamage(org.bukkit.event.entity.EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof Player) {
            Player player = (Player) entity;

            if(invulnerable.contains(player.getUniqueId().toString())){
                event.setCancelled(true);
            }

            if(!event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALLING_BLOCK)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FLY_INTO_WALL)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.CONTACT)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.DROWNING)
            && !event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.HOT_FLOOR)) {
                if (damaged.containsKey(player.getUniqueId().toString())) {
                    long time = (damaged.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
                    if (time < (homeCooldownDamage * 1000)) {
                        damaged.put(player.getUniqueId().toString(), System.currentTimeMillis() + (homeCooldownDamage * 1000));
                    }
                } else {
                    damaged.put(player.getUniqueId().toString(), System.currentTimeMillis() + (homeCooldownDamage * 1000));
                }
            }

            if(string.equalsIgnoreCase("true")) {
                if(player.getHealth() - event.getFinalDamage() <= 0) {
                    event.setCancelled(true);
                    if(event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK) || event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)){
                        player.getWorld().dropItemNaturally(player.getLocation(), SavedItems.getHead(player)).setPickupDelay(20);
                    }

                    becomeGhost(player, 180);
                    damaged.put(player.getUniqueId().toString(), System.currentTimeMillis());
                }
            }
        }
    }

    public static void becomeGhost(Player player, int seconds) {
        for (ItemStack itemStack : player.getInventory()) {
            if(itemStack != null) {
                if (!itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + player.getName() + "'s Rose")) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                }
            }
        }

        ItemStack itemStack = new ItemStack(Material.WITHER_ROSE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + "'s Rose");
        meta.addEnchant(Enchantment.LOYALTY, 1, true);
        itemStack.setItemMeta(meta);
        player.getWorld().dropItemNaturally(player.getLocation(), itemStack).setPickupDelay(20);

        player.getInventory().clear();

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.hidePlayer(player);
        }
        hidden.add(player);
        ghosts.put(player.getUniqueId().toString(), System.currentTimeMillis() + (seconds * 1000));

        int TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
            public void run() {
                if(ghosts.containsKey(player.getUniqueId().toString())) {
                    long time = (ghosts.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
                    sendActionbar(player, ChatColor.RED + "Time left: " + time + "s");
                }
            }
        }, 0L, 20L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
            public void run() {
                Bukkit.getScheduler().cancelTask(TaskID);
                if(ghosts.containsKey(player.getUniqueId().toString())) {
                    reviveGhost(player.getUniqueId());
                }
            }
        }, seconds * 20);

        player.setInvulnerable(true);
        player.setHealth(0.0001);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 9, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, seconds * 20, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, seconds * 20, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, seconds * 20, 250, false, false, false));
        player.setWalkSpeed((float) 0.3);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 0);
        player.sendTitle(ChatColor.DARK_RED + "✞", ChatColor.RED + "YOU DIED", 1, 50, 1);
        player.getWorld().spawnParticle(Particle.SOUL, player.getEyeLocation(), 25 , 0.5, 1, 0.5, 0.5, null ,true);
        if(player.getBedSpawnLocation() == null) {
            player.teleport(player.getWorld().getSpawnLocation());
        } else {
            player.teleport(player.getBedSpawnLocation());
        }
    }

    public static void reviveGhost(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }
        hidden.remove(player);
        ghosts.remove(player.getUniqueId().toString());

        player.removePotionEffect(PotionEffectType.SATURATION);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.WITHER);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setWalkSpeed((float) 0.2);
        player.setInvulnerable(false);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 1, 1);
        player.sendTitle(ChatColor.DARK_GREEN + "❤", ChatColor.GREEN +  "REVIVED", 1, 50, 1);
        player.getWorld().spawnParticle(Particle.CLOUD, player.getEyeLocation(), 50 , 1, 1, 1, 0.5, null ,true);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation(), 10 , 2, 1, 2, 0.1, null ,true);

    }

    public static void revertGhost(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }
        hidden.remove(player);
        ghosts.remove(player.getUniqueId().toString());

        player.setMaxHealth(20);
        player.setHealth(20);
        player.removePotionEffect(PotionEffectType.SATURATION);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.WITHER);
        player.setWalkSpeed((float) 0.2);
        player.setInvulnerable(false);
    }
}
