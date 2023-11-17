package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockBurnListener implements Listener {

    @EventHandler
    public void onBurn(BlockBurnEvent event){
        Object o = Config.get("event.advancedBowDamage");
        String s = String.valueOf(o);

        if (s.equalsIgnoreCase("true")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBurn(BlockIgniteEvent event){
        Object o = Config.get("event.advancedBowDamage");
        String s = String.valueOf(o);

        if (s.equalsIgnoreCase("true")) {
            event.setCancelled(true);
        }
    }

}
