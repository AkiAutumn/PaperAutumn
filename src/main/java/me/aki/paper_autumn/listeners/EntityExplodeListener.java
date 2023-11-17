package me.aki.paper_autumn.listeners;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
    @EventHandler
    public void onInteract(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Creeper) {
            event.setCancelled(true);
            entity.getWorld().createExplosion(entity.getLocation(), 1, false, false);
        }
    }
}
