package me.aki.paper_autumn.listeners;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import java.io.IOException;

import static me.aki.paper_autumn.listeners.ItemInteractListener.playerInInfinityCart;

public class VehicleLeaveListener implements Listener {
    @EventHandler
    public void onMove(VehicleExitEvent event) throws IOException {
        LivingEntity livingEntity = event.getExited();

        if(livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            playerInInfinityCart.remove(player.getUniqueId().toString());
        }
    }
}
