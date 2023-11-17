package me.aki.paper_autumn.listeners;


import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class ItemInteractListener implements Listener {

    public static Map<String, Integer> playerSkillAbility = new HashMap<String, Integer>();
    public static ArrayList<String> playerInInfinityCart = new ArrayList<String>();

    //TODO Elyta damage fixen bei wasser

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        String itemName = "";

        if (ghosts.containsKey(player.getUniqueId().toString())){
            assert block != null;
            if(!(block.getType().toString().toLowerCase().contains("door"))) {
                event.setCancelled(true);
            }
        }

        if(player.getWorld().getName().equalsIgnoreCase("tutorial")){
            if(!player.getGameMode().equals(GameMode.CREATIVE)){
                if(!Objects.requireNonNull(block).getBlockData().getMaterial().equals(Material.POLISHED_BLACKSTONE_BUTTON)) {
                    event.setCancelled(true);
                }
            }
        }

        if(item.getType().equals(Material.ENDER_EYE)){
            if(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)){
                boolean enabled = Boolean.parseBoolean((String) Config.get("customEndportalLocation.enabled"));
                if(enabled){
                    event.setCancelled(true);
                    ItemStack itemStack = player.getItemInHand();
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    player.setItemInHand(itemStack);
                    if(Config.contains("customEndportalLocation.location")) {
                        Location location = (Location) Config.get("customEndportalLocation.location");
                        assert location != null;

                        EnderSignal enderSignal = (EnderSignal) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ENDER_SIGNAL);
                        enderSignal.setTargetLocation(location);
                        Bukkit.getLogger().info("endersignal new target = " + location.toString());
                    }
                }
            }

        }
        
        /*
        ################################################################################################################
                                                        WWI EVENT
        ################################################################################################################
        */

        Object s = Config.get("advancedBowDamage");

        if(s != null) {
            if (s.toString().equals("true")) {
                if (item.getType().equals(Material.CROSSBOW)) {
                    if (Objects.requireNonNull(item.getItemMeta()).getDisplayName().equalsIgnoreCase("chauchat")) {
                        if ((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {

                            event.setUseItemInHand(Event.Result.DENY);
                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 0);

                            Location origin = player.getEyeLocation();
                            Vector direction = origin.getDirection();
                            int range = 13;
                            direction.multiply(range /* the range */);

                            direction.normalize();
                            for (int i = 0; i < range /* range */; i++) {
                                Location loc = origin.add(direction);

                                loc.getWorld().spawnParticle(Particle.FLAME, origin, 10, 0.5, 0.5, 0.5, 0.01);
                                loc.getWorld().spawnParticle(Particle.LAVA, origin, 3, 0.05, 0.05, 0.05, 0);
                                if (loc.getWorld().getBlockAt(origin).getType().equals(Material.AIR)) {
                                    loc.getWorld().getBlockAt(origin).setType(Material.FIRE);
                                }
                                for (Entity near : Objects.requireNonNull(origin.getWorld()).getNearbyEntities(origin, 2, 2, 2)) {
                                    if (near != player) {
                                        near.setFireTicks(100);
                                        if (!near.isDead()) {
                                            LivingEntity livingEntity = (LivingEntity) near;
                                            livingEntity.damage(1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            try {
                Object object = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
                itemName = String.valueOf(object);
            } catch (NullPointerException e) {
            }

            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                assert block != null;
                if (block.getType().equals(Material.DARK_OAK_DOOR) ||
                        block.getType().equals(Material.OAK_DOOR) ||
                        block.getType().equals(Material.BIRCH_DOOR) ||
                        block.getType().equals(Material.JUNGLE_DOOR) ||
                        block.getType().equals(Material.SPRUCE_DOOR) ||
                        block.getType().equals(Material.ACACIA_DOOR)) {

                    Location underBlockLoc = block.getLocation().add(0, -2, 0);

                    if (player.getLocation().getWorld().getBlockAt(underBlockLoc).getType().equals(Material.BARRIER) ||
                            player.getLocation().getWorld().getBlockAt(underBlockLoc.add(0, -1, 0)).getType().equals(Material.BARRIER)) {

                        event.setCancelled(true);
                        player.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 10, 0);

                    }
                }
            }


            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (event.getItem() == null) {
                    assert block != null;
                    if (block.getType().equals(Material.DARK_OAK_DOOR) ||
                            block.getType().equals(Material.OAK_DOOR) ||
                            block.getType().equals(Material.BIRCH_DOOR) ||
                            block.getType().equals(Material.JUNGLE_DOOR) ||
                            block.getType().equals(Material.SPRUCE_DOOR) ||
                            block.getType().equals(Material.ACACIA_DOOR)) {
                        block.getWorld().playSound(block.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 0);
                    }
                    if (block.getType().equals(Material.LANTERN)) {
                        block.breakNaturally();
                    }
                }
            }
        }
    }
}
