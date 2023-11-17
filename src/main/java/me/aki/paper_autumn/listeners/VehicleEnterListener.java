package me.aki.paper_autumn.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.io.IOException;
import java.util.Objects;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.damaged;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class VehicleEnterListener implements Listener {
    @EventHandler
    public void onEnter(VehicleEnterEvent event) throws IOException {
        Entity entity = event.getEntered();
        Vehicle vehicle = event.getVehicle();

        if (entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            if (ghosts.containsKey(player.getUniqueId().toString())){
                event.setCancelled(true);
            }

            if(vehicle.getType().equals(EntityType.MINECART)) {
                Minecart minecart = (Minecart) vehicle;
                if(minecart.customName().equals(ChatColor.LIGHT_PURPLE + "Swifty Minecart")){
                    minecart.setMaxSpeed(999999999);
                }
            }

        }
    }
}
