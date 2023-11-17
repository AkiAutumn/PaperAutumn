package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Timer;
import java.util.TimerTask;

import static me.aki.paper_autumn.utils.particles.drawCircle;

public class ProjectileLaunchListener implements Listener {

    long flareGunTime = 200;
    long gasGrenadeTime = 300;
    long molotovTime = 600;

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {

        LivingEntity livingEntity = event.getEntity();
        Arrow arrow = (Arrow) event.getProjectile();
        ItemStack itemStack = event.getBow();
        Object o = Config.get("advancedBowDamage");
        String s = String.valueOf(o);

        //bow knockback
        if(livingEntity instanceof Player){
            Player player = (Player) livingEntity;

            Vector velocity = arrow.getVelocity();
            velocity.multiply(Double.parseDouble((String) Config.get("projectileKnockbackMultiplier")));
            player.setVelocity(velocity);
        }

        //tp bow
        if(itemStack.equals(SavedItems.tpBow())) {
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(arrow.isOnGround() || arrow.isDead()){
                        livingEntity.getWorld().spawnParticle(Particle.REVERSE_PORTAL, livingEntity.getLocation(), 50, 0.5, 1, 0.5,0.1, null, true);
                        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 0);
                        livingEntity.teleport(arrow.getLocation().add(0, 1, 0));
                        livingEntity.getWorld().spawnParticle(Particle.REVERSE_PORTAL, livingEntity.getLocation(), 50, 0.5, 1, 0.5,0.1, null, true);
                        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 0);
                        this.cancel();
                    }
                    arrow.getWorld().spawnParticle(Particle.REVERSE_PORTAL, arrow.getLocation(), 5, 0, 0, 0, 0.2, null, true);
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0, 1);
        }

        //advanced bows
        if (s.equalsIgnoreCase("true")) {
            if (livingEntity.getType().equals(EntityType.PLAYER)) {
                Player player = (Player) livingEntity;

                if (itemStack.getType().equals(Material.BOW)) {
                    switch (itemStack.getItemMeta().getDisplayName().toLowerCase()) {
                        case "luger parabellum":
                        case "mauser c96":
                        case "m1911":
                            arrow.setVelocity(arrow.getVelocity().multiply(1.25));
                            break;
                    }
                }

                if (itemStack.getType().equals(Material.CROSSBOW)) {
                    switch (itemStack.getItemMeta().getDisplayName().toLowerCase()) {
                        case "mp18":
                            arrow.setDamage(3);
                            arrow.setVelocity(arrow.getVelocity().multiply(1.5));
                            break;
                        case "lewis gun":
                            arrow.setDamage(3);
                            arrow.setVelocity(arrow.getVelocity().multiply(1.25));
                            break;
                        case "chauchat":
                            arrow.remove();
                            break;
                        case "mg08":
                            arrow.setDamage(2);
                            arrow.setVelocity(arrow.getVelocity().multiply(1.25));
                            break;
                        case "lee-enfield":
                            arrow.setDamage(75);
                            arrow.setVelocity(arrow.getVelocity().multiply(3.5));
                            break;
                        case "flare gun":
                            arrow.setDamage(10000);
                            arrow.setVelocity(arrow.getVelocity().multiply(1.25));

                            int TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
                                public void run() {
                                    arrow.getWorld().spawnParticle(Particle.FLASH, arrow.getLocation(), 3, 0, 0, 0, 0, null, true);
                                    arrow.getWorld().spawnParticle(Particle.LAVA, arrow.getLocation(), 3, 0, 0, 0, 0, null, true);
                                }
                            }, 0L, 2L);

                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                                public void run() {
                                    Bukkit.getScheduler().cancelTask(TaskID);
                                }
                            }, flareGunTime);

                            break;
                        case "gewehr 98":
                            arrow.setDamage(15);
                            arrow.setVelocity(arrow.getVelocity().multiply(1.75));
                            break;
                        case "winchester m1897 shotgun":
                            arrow.setDamage(40);
                            arrow.setVelocity(arrow.getVelocity().multiply(0.35));
                            break;
                        case "sawed-off shotgun":
                            arrow.setDamage(35);
                            arrow.setVelocity(arrow.getVelocity().multiply(0.5));
                            break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {

        Entity entity = event.getEntity();
        Object o = Config.get("event.advancedBowDamage");
        String s = String.valueOf(o);

        if (s.equalsIgnoreCase("true")) {
            if (entity.getType().equals(EntityType.SPLASH_POTION)) {
                ThrownPotion potion = (ThrownPotion) entity;
                ItemStack itemStack = potion.getItem();
                switch (itemStack.getItemMeta().getDisplayName().toLowerCase()) {

                    case "stick grenade":
                    case "mills bomb":

                        potion.setVelocity(potion.getVelocity().multiply(1.5));

                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                if(potion.isDead()){
                                    potion.getWorld().createExplosion(potion.getLocation(), 4, false, false);
                                    potion.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, potion.getLocation().add(0, 0.1, 0), 200, 2, 1, 2, 0, null, true);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Main.getPlugin(Main.class), 0, 2);
                        break;
                    case "plane bomb":

                        potion.setVelocity(potion.getVelocity().setX(0).setZ(0));

                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                if(potion.isDead()){
                                    potion.getWorld().createExplosion(potion.getLocation(), 10, true, false);
                                    potion.getWorld().spawnParticle(Particle.FLAME, potion.getLocation().add(0, 1, 0), 2500, 5, 5, 5, 0.1, null, true);
                                    potion.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, potion.getLocation().add(0, 1, 0), 2500, 5, 3, 5, 0, null, true);
                                    this.cancel();
                                }
                            }
                            }.runTaskTimer(Main.getPlugin(Main.class), 0, 2);
                        break;
                    case "gas grenade":

                        potion.setVelocity(potion.getVelocity().multiply(1.5));

                        Particle.DustOptions dust = new Particle.DustOptions(
                                Color.fromRGB(125, 118, 111), 75);

                                int TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
                                    public void run() {
                                        potion.getWorld().spawnParticle(Particle.REDSTONE, potion.getLocation(), 15, 2, 1, 2, dust);
                                    }
                                }, 30L, 2L);

                                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                                    public void run() {
                                        Bukkit.getScheduler().cancelTask(TaskID);
                                    }
                                }, gasGrenadeTime);

                                break;
                    case "molotov":

                        potion.setVelocity(potion.getVelocity().multiply(1.5));

                        int TaskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
                            public void run() {
                                potion.getWorld().spawnParticle(Particle.FLAME, potion.getLocation(), 3, 0, 0, 0, 0.05);
                            }
                        }, 0L, 2L);

                        int TaskID2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
                            public void run() {
                                potion.getWorld().spawnParticle(Particle.FLAME, potion.getLocation(), 100, 2, 0.25, 2, 0.025);
                                for (Entity near : potion.getNearbyEntities(3, 1, 3)) {
                                    if (near.getLocation().distance(potion.getLocation()) <= 3) {
                                        near.setFireTicks(100);
                                    }
                                }
                            }
                        }, 30L, 2L);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                            public void run() {
                                Bukkit.getScheduler().cancelTask(TaskID1);
                                Bukkit.getScheduler().cancelTask(TaskID2);
                            }
                        }, molotovTime);
                        break;

                    case "v1":
                        potion.setVelocity(potion.getVelocity().setX(0).setZ(0));
                        long chargeTime = 0;
                        double generalVelocityMultiplier = 1;
                        potion.setVelocity(potion.getVelocity().multiply(0.75));

                        new BukkitRunnable(){
                            float pitch = 1;
                            @Override
                            public void run() {
                                if(potion.isDead()) {
                                    this.cancel();
                                }
                                potion.getWorld().playSound(potion.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 20, pitch);
                                potion.getWorld().spawnParticle(Particle.LAVA, potion.getLocation(), 1, 0.1, 0.75, 0.1, 1, null, true);
                                pitch = (float) (pitch - 0.01);

                            }
                        }.runTaskTimer(Main.getPlugin(Main.class), 0, 4);

                        new BukkitRunnable(){
                            @Override
                            public void run() {

                                potion.getWorld().spawnParticle(Particle.LAVA, potion.getLocation(), 10, 0, 0, 0, 0.5, null, true);

                                if(potion.isDead()){

                                    V1Velocity(potion, generalVelocityMultiplier, chargeTime);

                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                                        public void run() {

                                            potion.getWorld().createExplosion(potion.getLocation(), 50, true, false);
                                            potion.getWorld().playSound(potion.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 200, 0);
                                            potion.getWorld().playSound(potion.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 0);
                                            potion.getWorld().playSound(potion.getLocation(), Sound.AMBIENT_BASALT_DELTAS_MOOD, 100, 0);
                                            potion.getWorld().spawnParticle(Particle.FLAME, potion.getLocation().add(0, 1, 0), 5000, 10, 1.5, 10, 0.01, null, true);
                                            potion.getWorld().spawnParticle(Particle.LAVA, potion.getLocation().add(0, 5, 0), 3000, 15, 15, 15, 0.1, null, true);
                                            potion.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, potion.getLocation().add(0, 2, 0), 100, 10, 10, 10, 1, null, true);
                                            potion.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, potion.getLocation().add(0, 1, 0), 3000, 20, 2.5, 20, 0, null, true);
                                            potion.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, potion.getLocation().add(0, 10, 0), 3000, 2.5, 10, 2.5, 0, null, true);
                                            potion.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, potion.getLocation().add(0, 25, 0), 4000, 10, 4, 10, 0, null, true);

                                            for (Player other : Bukkit.getOnlinePlayers()) {
                                                if (other.getLocation().distance(potion.getLocation()) <= 75) {
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 240, 4, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 240, 2, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 120, 1, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 2, false, false));
                                                }
                                            }

                                            for (Player other : Bukkit.getOnlinePlayers()) {
                                                if (other.getLocation().distance(potion.getLocation()) <= 100) {
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 4, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 240, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 1, false, false));
                                                }
                                            }

                                            for (Player other : Bukkit.getOnlinePlayers()) {
                                                if (other.getLocation().distance(potion.getLocation()) <= 125) {
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 4, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 120, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0, false, false));
                                                }
                                            }

                                            for (Player other : Bukkit.getOnlinePlayers()) {
                                                if (other.getLocation().distance(potion.getLocation()) <= 200) {
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 4, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 0, false, false));
                                                }
                                            }

                                            for (Player other : Bukkit.getOnlinePlayers()) {
                                                if (other.getLocation().distance(potion.getLocation()) <= 300) {
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 40, 0, false, false));
                                                    other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 0, false, false));
                                                }
                                            }
                                            potion.getWorld().setStorm(true);
                                            potion.getWorld().setWeatherDuration(200);
                                        }
                                    }, chargeTime);
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(Main.getPlugin(Main.class), 0, 2);

                        break;
                }
            }
        }
    }

    private void V1Velocity(Entity arrow, double generalVelocityMultiplier, long delay){
        final int[] i = {10};

        new BukkitRunnable() {
            @Override
            public void run() {
                if(i[0] == 75){
                    this.cancel();
                }

                if(i[0] % 3 == 0) {

                    drawCircle(arrow.getLocation().add(0, 0.5, 0), (float) i[0], Particle.CAMPFIRE_COSY_SMOKE, null);

                    for (Entity near : arrow.getNearbyEntities(i[0], i[0], i[0])) {
                        if (near.getLocation().distance(arrow.getLocation()) <= i[0]) {

                                Location nearloc = near.getLocation();
                                Location eLoc = arrow.getLocation();
                                Location newLoc = nearloc.subtract(eLoc);
                                Vector newV = new Vector(newLoc.toVector().normalize().multiply(1.25).getY(), 0.125, newLoc.toVector().normalize().multiply(1.25).getZ());
                                newV.multiply(generalVelocityMultiplier);

                                near.setVelocity(newV);
                                arrow.getWorld().playSound(arrow.getLocation(), Sound.AMBIENT_BASALT_DELTAS_MOOD, 50, 0);
                            }
                        }
                    }
                ++i[0];
            }
        }.runTaskTimer(Main.getPlugin(Main.class), delay, 0L);
    }
}
