package me.aki.paper_autumn.freezing;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.portals.portalConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class freezing implements Listener {

    public static Map<String, Boolean> freezingRunnables = new HashMap<String, Boolean>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        if(Boolean.valueOf(String.valueOf(Config.get("freezeInCold")))){
            Player player = event.getPlayer();
            attachFreezingRunnable(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        freezingRunnables.put(player.getUniqueId().toString(), true);
    }

    public static void attachRunnableToAllPlayers(){
        if(Boolean.valueOf(String.valueOf(Config.get("freezeInCold")))){
            for(Player everyone : Bukkit.getOnlinePlayers()) {
                attachFreezingRunnable(everyone);
            }
        }
    }

    public static void attachFreezingRunnable(Player player){
        Bukkit.getLogger().info("Started Runnable for " + player.getName());
        freezingRunnables.put(player.getUniqueId().toString(), false);
        Long timeInTicks = 20L;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (freezingRunnables.get(player.getUniqueId().toString())) {
                    this.cancel();
                    Bukkit.getLogger().info("Cancelled Runnable for " + player.getName());
                }

                int freezingIndex = getFreezingIndexForBiome(player.getLocation().getBlock().getBiome());

                freezingIndex = freezingIndex * 10;

                if(freezingIndex > 1000){
                    freezingIndex = 0;
                }

                player.setFreezeTicks(player.getFreezeTicks() + freezingIndex);
                player.sendMessage("-> " + player.getFreezeTicks());
            }
        }.runTaskTimer(Main.getPlugin(Main.class), timeInTicks, timeInTicks);
    }

    public static int getFreezingIndexForBiome(Biome biome){

        int freezingIndex = 0;

        switch (biome){
            case FROZEN_OCEAN, DEEP_FROZEN_OCEAN -> freezingIndex = 4;
            case FROZEN_PEAKS -> freezingIndex = 6;
            case FROZEN_RIVER -> freezingIndex = 3;
            case SNOWY_BEACH, SNOWY_PLAINS, SNOWY_TAIGA -> freezingIndex = 2;
            case SNOWY_SLOPES -> freezingIndex = 5;
            case ICE_SPIKES -> freezingIndex = 7;
        }

        return freezingIndex;
    }
}
