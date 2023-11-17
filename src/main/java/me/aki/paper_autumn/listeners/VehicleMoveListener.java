package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class VehicleMoveListener implements Listener {
    @EventHandler
    public void onMove(VehicleMoveEvent event) throws IOException {
        String obj1 = String.valueOf(Config.get("worldedgetp.negZ"));
        String obj2 = String.valueOf(Config.get("worldedgetp.posX"));
        String obj3 = String.valueOf(Config.get("worldedgetp.posZ"));
        String obj4 = String.valueOf(Config.get("worldedgetp.negX"));
        long negZ = Long.valueOf(obj1);
        long posX = Long.valueOf(obj2);
        long posZ = Long.valueOf(obj3);
        long negX = Long.valueOf(obj4);
        String worldEdgeTP = (String) Config.get("worldedgetp.status");
        Vehicle vehicle = event.getVehicle();

        if(vehicle.getCustomName() != null) {
            if (vehicle.getCustomName().equals(ChatColor.LIGHT_PURPLE + "Swifty Minecart")) {
                vehicle.getWorld().spawnParticle(Particle.FLAME, vehicle.getLocation(), 1, 0.5, 0.05, 0.5, 0.01);
            }
        }

        assert worldEdgeTP != null;
        if("true".equalsIgnoreCase(worldEdgeTP)){
            Location location = vehicle.getLocation();
            Location newLocation = location;

            if(location.getX() > posX) {
                newLocation = new Location(location.getWorld(),negX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgeVehicleTP(vehicle, newLocation);
            }
            if(location.getX() < negX) {
                newLocation = new Location(location.getWorld(),posX, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());
                worldEdgeVehicleTP(vehicle, newLocation);
            }
            if(location.getZ() > posZ) {
                newLocation = new Location(location.getWorld(),location.getX(), location.getY() + 1, negZ, location.getYaw(), location.getPitch());
                worldEdgeVehicleTP(vehicle, newLocation);
            }
            if(location.getZ() < negZ) {
                newLocation = new Location(location.getWorld(),location.getX(), location.getY() + 1, posZ, location.getYaw(), location.getPitch());
                worldEdgeVehicleTP(vehicle, newLocation);
            }
        }
    }

    public void worldEdgeVehicleTP(Vehicle vehicle, Location newLocation) {
        if(vehicle.getWorld().equals(Bukkit.getWorld("world"))) {
            Entity entity = null;
            Vector vector = vehicle.getVelocity();
            if(vehicle.getPassenger() != null){
                entity = vehicle.getPassenger();
            }
            vehicle.remove();
            vehicle.getWorld().spawnParticle(Particle.FLASH, vehicle.getLocation(), 10, 0, 0, 0, 0, null, true);
            vehicle.getWorld().spawnParticle(Particle.CLOUD, vehicle.getLocation(), 50, 1.5, 1.5, 1.5, 0, null, true);
            vehicle.getWorld().playSound(vehicle.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);
            vehicle.teleport(newLocation);

            Vehicle clone = vehicle.getWorld().spawn(newLocation, vehicle.getClass());
            clone.setVelocity(vector);
            if(entity != null) {
                entity.teleport(clone.getLocation());
                clone.addPassenger(entity);
            }

            clone.getWorld().playSound(clone.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 2, 2);
            clone.getWorld().spawnParticle(Particle.CLOUD, clone.getLocation(), 50, 1.5, 1.5, 1.5, 0, null, true);
            clone.getWorld().spawnParticle(Particle.FLASH, clone.getLocation(), 10, 0, 0, 0, 0, null, true);
        }
    }
}
