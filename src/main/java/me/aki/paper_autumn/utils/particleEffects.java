package me.aki.paper_autumn.utils;

import me.aki.paper_autumn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class particleEffects {

    private int taskID;
    private final Player player;

    public particleEffects(Player player) {
        this.player = player;
    }

    public static void startPlayerSpawnEffect(Player player) {
        new BukkitRunnable(){

            int i = 50;
            double var = 0;
            Location loc, first, second;

            @Override
            public void run() {
                if(i > 0) {
                    if(i == 50){
                        player.playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 10, 2);
                    }

                    var += Math.PI / 16;

                    loc = player.getLocation();
                    first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
                    second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));

                    player.getWorld().spawnParticle(Particle.END_ROD, first, 0);
                    player.getWorld().spawnParticle(Particle.END_ROD, second, 0);

                    i--;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public void startHelix(Particle particleEffect) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            double var = 0;
            Location loc, first, second;
            particleData particle = new particleData(player.getUniqueId());

            @Override
            public void run() {
                if(!particle.hasID()) {
                    particle.setID(taskID);
                }

                var += Math.PI / 16;

                loc = player.getLocation().add(0, 1, 0);
                first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
                second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));

                player.getWorld().spawnParticle(particleEffect, first, 10, 0.1, 0.1, 0.1 ,0, null, true);
                player.getWorld().spawnParticle(particleEffect, second, 10, 0.1, 0.1, 0.1, 0, null, true);
            }
        }, 0, 1);
    }

    public void startRain() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            particleData particle = new particleData(player.getUniqueId());

            @Override
            public void run() {
                if(!particle.hasID()) {
                    particle.setID(taskID);
                }

                double random = Math.random();
                player.getWorld().spawnParticle(Particle.CLOUD, player.getEyeLocation().add(0, 1, 0), 3,0.3, 0.1, 0.3, 0);
                particles.drawRandom(player, player.getEyeLocation().add(0, 1, 0), 1, 0.05, Particle.WATER_DROP);

            }
        }, 0, 1);
    }

}
