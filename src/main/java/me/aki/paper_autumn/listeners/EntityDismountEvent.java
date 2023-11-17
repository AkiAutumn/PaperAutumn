package me.aki.paper_autumn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDismountEvent implements Listener {
    @EventHandler
    public void onDismount(org.spigotmc.event.entity.EntityDismountEvent event){
        Entity dismounted = event.getDismounted();
        Entity entity = event.getEntity();

        if(dismounted instanceof Dolphin){
            Dolphin dolphin = (Dolphin) dismounted;
            if(dolphin.isInWater() && entity.isInWater()){
                event.setCancelled(true);
            }
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(!player.getGameMode().equals(GameMode.CREATIVE)){
                    if(!event.isCancelled()) {
                        player.setAllowFlight(false);
                    }
                }
            }
        }
    }

    /*

    @EventHandler
    public void onMount(org.spigotmc.event.entity.EntityMountEvent event){
        Entity mount = event.getMount();
        Entity entity = event.getEntity();

        if(mount instanceof Dolphin){
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(!player.getGameMode().equals(GameMode.CREATIVE)){
                    player.setAllowFlight(true);
                }
            }
        }
    }

     */
}
