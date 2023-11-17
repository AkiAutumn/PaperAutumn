package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static me.aki.paper_autumn.casino.cashierInventory.cashierInventory;
import static me.aki.paper_autumn.casino.cashierInventory.createInventory;
import static me.aki.paper_autumn.casino.casino.*;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class PlayerInteractAtEntityListener implements Listener {

    public static Map<String, Long> tasered = new HashMap<String, Long>();
    public static Map<String, Long> pushingPlayers = new HashMap<String, Long>();

    @EventHandler
    public void PlayerEntityInteract(PlayerInteractAtEntityEvent event) throws IOException {

        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();

        ItemStack itemStack = player.getItemInHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        Object obj = itemMeta;

        if(entity instanceof Dolphin){
            Dolphin dolphin = (Dolphin) entity;
            if(dolphin.hasFish()) {
                dolphin.addPassenger(player);
            }
        }

        if (ghosts.containsKey(player.getUniqueId().toString())){
            event.setCancelled(true);
        }

        if(entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;

            if (!player.getGameMode().equals(GameMode.CREATIVE)) {

                // Cashier armorstand
                if (armorStand.getCustomName().equalsIgnoreCase("cashier")) {
                    createInventory(player);
                    player.openInventory(cashierInventory);
                } else {

                    if (!inCasinoGame.containsKey(player.getUniqueId().toString())) {
                        inCasinoGame.put(player.getUniqueId().toString(), false);
                    }
                    //player in hashmap
                    if (inCasinoGame.get(player.getUniqueId().toString())) {
                        //still in Game
                        return;
                    } else {

                        inCasinoGame.put(player.getUniqueId().toString(), true);
                        PlayerConfig.set(player.getUniqueId() + ".CancelNextChatMessage", true);
                    }

                    if (!(EconomySystem.getMoney(player.getUniqueId().toString()).compareTo(new BigInteger("0")) == 0)) {
                        switch (armorStand.getCustomName().toLowerCase()) {
                            case "coinflip":
                                PlayerConfig.set(player.getUniqueId() + ".CurrentCasinoGame", "coinflip");
                                player.sendMessage(CoinflipPrefix + "Write the amount of Credits you want to wager in chat!"
                                        + "\n" + ChatColor.GRAY + "(You can type 'Cancel' too)");
                                break;
                            case "crash":
                                PlayerConfig.set(player.getUniqueId() + ".CurrentCasinoGame", "crash");
                                player.sendMessage(CrashPrefix + "Write the amount of Credits you want to wager in chat!"
                                        + "\n" + ChatColor.GRAY + "(You can type 'Cancel' too)");
                                break;
                            case "blackjack":
                                PlayerConfig.set(player.getUniqueId() + ".CurrentCasinoGame", "blackjack");
                                player.sendMessage(BlackjackPrefix + "Write the amount of Credits you want to wager in chat!"
                                        + "\n" + ChatColor.GRAY + "(You can type 'Cancel' too)");
                                break;
                        }
                    } else {
                        denyAction(player);
                        player.sendMessage(CasinoPrefix + "oh oh... it seems like you're broke :(" + "\n"
                                + " " + "\n" + " But don't worry! Luckily you can come back tomorrow and collect the " + ChatColor.AQUA + "Daily Tip" + ChatColor.WHITE + " at the Cashier!");

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){

                            @Override
                            public void run(){
                                try {
                                    endCasinoGame(player);
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }, 10L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                inCasinoGame.put(player.getUniqueId().toString(), false);
                            }
                        }, 150L);
                    }
                }
            }
        } else {
            return;
        }

        if (entity instanceof Player) {
            Player target = (Player) entity;
            String uuid = player.getUniqueId().toString();

            if (itemStack.getType().equals(Material.AIR)) {

                if(player.isSprinting()){

                    if (!pushingPlayers.containsKey(uuid + ":" + "pushed")) {
                        Random random = new Random();
                        long duration = random.nextInt(10 - 7 + 1) + 7;
                        pushingPlayers.put(uuid + ":" + "pushed", System.currentTimeMillis() + (duration * 1000));

                        target.getWorld().playSound(target.getEyeLocation(), Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 10, 1);
                        Vector vector = new Vector();
                        vector = player.getLocation().getDirection();
                        vector.setY(0.1);
                        entity.setVelocity(vector);

                        player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 5, 0.75, 0.05, 0.75, 0.01);
                    }

                    if (pushingPlayers.get(uuid + ":" + "pushed") > System.currentTimeMillis()) {
                        //still on cooldown
                        return;
                    } else {
                        Random random = new Random();
                        long duration = random.nextInt(10 - 7 + 1) + 7;
                        pushingPlayers.put(uuid + ":" + "pushed", System.currentTimeMillis() + (duration * 1000));

                        target.getWorld().playSound(target.getEyeLocation(), Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 10, 1);
                        Vector vector = new Vector();
                        vector = player.getLocation().getDirection();
                        vector.setY(0.1);
                        entity.setVelocity(vector);

                        player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 5, 0.75, 0.05, 0.75, 0.01);
                    }

                } else

                if (!pushingPlayers.containsKey(uuid + ":" + "pushed")) {
                    Random random = new Random();
                    long duration = random.nextInt(5 - 3 + 1) + 3;
                    pushingPlayers.put(uuid + ":" + "pushed", System.currentTimeMillis() + (duration * 1000));

                    target.getWorld().playSound(target.getEyeLocation(), Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 10, 1);
                    Vector vector = new Vector();
                    vector = player.getLocation().getDirection();
                    vector.multiply(0.5);
                    vector.setY(0.1);
                    entity.setVelocity(vector);
                }

                if (pushingPlayers.get(uuid + ":" + "pushed") > System.currentTimeMillis()) {
                    //still on cooldown
                    return;
                } else {
                    Random random = new Random();
                    long duration = random.nextInt(5 - 3 + 1) + 3;
                    pushingPlayers.put(uuid + ":" + "pushed", System.currentTimeMillis() + (duration * 1000));

                    target.getWorld().playSound(target.getEyeLocation(), Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 10, 1);
                    Vector vector = new Vector();
                    vector = player.getLocation().getDirection();
                    vector.multiply(0.5);
                    vector.setY(0.1);
                    entity.setVelocity(vector);
                }
            }
        }
    }
}
