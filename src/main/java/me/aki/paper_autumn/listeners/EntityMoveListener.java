package me.aki.paper_autumn.listeners;

import io.papermc.paper.event.entity.EntityMoveEvent;
import me.aki.paper_autumn.Config;
import org.bukkit.*;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EntityMoveListener implements Listener {
    @EventHandler
    public void EntityMove (EntityMoveEvent event){

        Entity entity = event.getEntity();
        String obj1 = String.valueOf(Config.get("worldedgetp.negZ"));
        String obj2 = String.valueOf(Config.get("worldedgetp.posX"));
        String obj3 = String.valueOf(Config.get("worldedgetp.posZ"));
        String obj4 = String.valueOf(Config.get("worldedgetp.negX"));
        long negZ = Long.valueOf(obj1);
        long posX = Long.valueOf(obj2);
        long posZ = Long.valueOf(obj3);
        long negX = Long.valueOf(obj4);
        String worldEdgeTP = (String) Config.get("worldedgetp.status");

        if(entity instanceof Dolphin){
            Dolphin dolphin = (Dolphin) entity;
            if(dolphin.getPassenger() instanceof Player) {

                if(dolphin.isInWaterOrRain()) {
                    Player player = (Player) dolphin.getPassenger();
                    Vector vector = player.getLocation().getDirection().multiply(0.25);

                    dolphin.setJumping(true);

                    switch (player.getItemInHand().getType()) {
                        case TROPICAL_FISH -> vector = vector.multiply(2.5);
                        case SALMON -> vector = vector.multiply(2);
                        case COD -> vector = vector.multiply(1.5);
                    }
                    switch (player.getInventory().getItemInOffHand().getType()) {
                        case TROPICAL_FISH -> vector = vector.multiply(2.5);
                        case SALMON -> vector = vector.multiply(2);
                        case COD -> vector = vector.multiply(1.5);
                    }

                    if(dolphin.isInWater()){
                            vector.multiply(0.5);
                            vector.setY(vector.getY() - 0.3);
                    }

                    if(player.isFlying()) {
                        double vectorY = player.getLocation().getDirection().getY();
                        vector.setY(vectorY);
                    }

                    if(dolphin.isOnGround()){
                        vector.multiply(0.25);
                    }
                    dolphin.setVelocity(vector);
                    dolphin.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                }
            }
        }

        assert worldEdgeTP != null;
        if ("true".equalsIgnoreCase(worldEdgeTP)) {
            Location location = entity.getLocation();
            Location newLocation = location;

            if (location.getX() > posX) {
                newLocation = new Location(location.getWorld(), negX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgeEntityTP(entity, newLocation);
            }
            if (location.getX() < negX) {
                newLocation = new Location(location.getWorld(), posX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgeEntityTP(entity, newLocation);
            }
            if (location.getZ() > posZ) {
                newLocation = new Location(location.getWorld(), location.getX(), location.getY() + 1, negZ, location.getYaw(), location.getPitch());
                worldEdgeEntityTP(entity, newLocation);
            }
            if (location.getZ() < negZ) {
                newLocation = new Location(location.getWorld(), location.getX(), location.getY() + 1, posZ, location.getYaw(), location.getPitch());
                worldEdgeEntityTP(entity, newLocation);
            }
        }
    }

    public void worldEdgeEntityTP(Entity entity, Location newLocation) {
        if(entity.getWorld().equals(Bukkit.getWorld("world"))) {
            if (!entity.getPassengers().isEmpty()) {
                entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 2, 2);

                Entity passenger = entity.getPassenger();
                passenger.teleport(newLocation);

                Entity clone = entity.getWorld().spawn(newLocation, entity.getClass());
                clone.setPassenger(passenger);
                entity.remove();

                clone.getWorld().playSound(clone.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);
            }
        }
    }
}
