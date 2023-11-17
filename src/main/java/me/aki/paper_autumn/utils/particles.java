package me.aki.paper_autumn.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.util.Objects;

public class particles {

    public static void drawCircle(Location location, float radius, Particle particle, Particle.DustOptions data) {
        for (double t = 0; t < 50; t += 0.1) {
            float x = (float) ((float) radius * Math.sin(t));
            float z = (float) ((float) radius * Math.cos(t));
            Objects.requireNonNull(location.getWorld()).spawnParticle(particle, (float) location.getX() + x, (float) location.getY(), (float) location.getZ() + z, 0, 0, 0, 0, 1, data, true);
        }
    }

    public static void drawCircleBall(Entity entity, Location location, float radius, Particle particle) {
        for (double t = 0; t < 50; t += 0.5) {
            float x = (float) ((float) radius * Math.sin(t));
            float z = (float) ((float) radius * Math.cos(t));
            entity.getWorld().spawnParticle(particle, (float) location.getX() + x, (float) location.getY() + z, (float) location.getZ(), 0, 0, 0, 0, 1);
            entity.getWorld().spawnParticle(particle, (float) location.getX(), (float) location.getY() + z, (float) location.getZ() + x, 0, 0, 0, 0, 1);
        }
    }


    public static void drawHelix(Entity entity, Location loc, int radius, Particle particle) {

        for (double y = 0; y <= 10; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            entity.getWorld().spawnParticle(particle, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
        }
    }

    public static void drawHelixAlternative(Entity entity, Location loc, int radius, Particle particle) {

        for (double y = 0; y <= 10; y += 0.05) {
            double x = radius * Math.sin(y);
            double z = radius * Math.cos(y);
            entity.getWorld().spawnParticle(particle, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
        }
    }

    public static void drawWaveCross(Location loc, int radius, Particle particle) {

        for (double y = 0; y <= 10; y += 0.05) {
            double x = radius * Math.sin(y);
            double z = radius * Math.sin(y);
            loc.getWorld().spawnParticle(particle, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
        }

        for (double y = 0; y <= 10; y += 0.05) {
            double x = radius * Math.sin(y);
            double z = radius * Math.sin(y);
            loc.getWorld().spawnParticle(particle, (float) (loc.getX() - x), (float) (loc.getY() + y), (float) (loc.getZ() - z), 0, 0, 0, 0, 1);
        }
    }

    public static void drawSpiral(Entity entity, Location loc, Particle particle) {
        double radius = 5;

        for (double y = 5; y >= 0; y -= 0.050) {
            radius = y / 3;
            double x = radius * Math.cos(3 * y);
            double z = radius * Math.sin(3 * y);

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            entity.getWorld().spawnParticle(particle, (float) (loc2.getX() + x), (float) (loc2.getY() + y), (float) (loc2.getZ() + z), 0, 0, 0, 0, 1);
        }

        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = -(radius * Math.cos(3 * y));
            double z = -(radius * Math.sin(3 * y));

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            entity.getWorld().spawnParticle(particle, (float) (loc2.getX() + x), (float) (loc2.getY() + y), (float) (loc2.getZ() + z), 0, 0, 0, 0, 1);
        }

    }

    public static void drawSphere(Entity entity, double r, Particle particle){

        Location l = entity.getLocation();
        for(double phi = 0; phi <= Math.PI; phi += Math.PI / 15) {
            double y = r * Math.cos(phi) + 1.5;
            for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 30) {
                double x = r * Math.cos(theta) * Math.sin(phi);
                double z = r * Math.sin(theta) * Math.sin(phi);

                l.add(x, y, z);
                l.getWorld().spawnParticle(particle, l, 1, 0F, 0F, 0F, 0.001);
                l.subtract(x, y, z);
            }
        }

    }

    public static void drawDots(Entity entity, double radius, int amount, Particle particle) {

        Location location = entity.getLocation();

        entity.getWorld().spawnParticle(particle, (float) location.getX() + radius, (float) location.getY(), (float) location.getZ() + radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() - radius, (float) location.getY(), (float) location.getZ() - radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() - radius, (float) location.getY(), (float) location.getZ() + radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() + radius, (float) location.getY(), (float) location.getZ() - radius, amount, 0, 0, 0, 0.05);
    }

    public static void drawDotsVertical(Entity entity, double radius, int amount, Particle particle) {

        Location location = entity.getLocation();

        entity.getWorld().spawnParticle(particle, (float) location.getX() + radius, (float) location.getY() + radius, (float) location.getZ() + radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() - radius, (float) location.getY() - radius, (float) location.getZ() - radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() - radius, (float) location.getY() + radius, (float) location.getZ() + radius, amount, 0, 0, 0, 0.05);
        entity.getWorld().spawnParticle(particle, (float) location.getX() + radius, (float) location.getY() + -radius, (float) location.getZ() - radius, amount, 0, 0, 0, 0.05);
    }

    public static void drawRandom(Entity entity, Location location, double amount, double dissolve, Particle particle) {

        //default amount is 10
        for (double t = 0; t < amount; t += 0.1) {
            double x = (0 + Math.random() - Math.random());
            double z = (0 + Math.random() - Math.random());
            entity.getWorld().spawnParticle(particle, (float) location.getX() + x, (float) location.getY(), (float) location.getZ() + z, 0, 0, 0, 0, dissolve);
        }
    }
}
