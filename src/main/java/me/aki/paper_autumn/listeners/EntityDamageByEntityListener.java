package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.damaged;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class EntityDamageByEntityListener implements Listener {

    private void particle(Entity entity, Integer count, Double splatter){
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(115, 28, 28), 2);

        count = count * 2;
        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().add(0, 1, 0), count, 0.25, 0.25, 0.25, 5, dustOptions);

        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        int splatterCount = (int) (splatter * 200);
        entity.getWorld().spawnParticle(Particle.ITEM_CRACK, entity.getLocation().add(0,1,0), splatterCount, 0.75, 0.25, 0.75, splatter, itemStack);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();
        Entity damagerEntity = event.getDamager();
        Player player = null;
        String playerSkill = "";

        if(damagerEntity instanceof Player) {
            Player damagerPlayer = (Player) damagerEntity;
            if (ghosts.containsKey(damagerPlayer.getUniqueId().toString())){
                event.setCancelled(true);
            }
        }

        /*
        Cat cat = (Cat) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.CAT);

        if (damagerEntity.getType().equals(EntityType.PLAYER)){
            player = (Player) damagerEntity;

            if(entity.getType().equals(EntityType.CAT)) {
                cat.setCatType(Cat.Type.RAGDOLL);
                cat.setLeashHolder(player);
            }
        }
        */


        Object objectB = Config.get("blood");
        String stringB = String.valueOf(objectB);

        if(stringB.equalsIgnoreCase("true")){
            switch ((int) event.getDamage()){
                case 2: particle(entity, 1, 0.005);
                break;

                case 3:
                case 4: particle(entity, 2, 0.0075);
                    break;

                case 5:
                case 6: particle(entity, 3, 0.01);
                    break;

                case 7:
                case 8: particle(entity, 4, 0.025);
                    break;

                case 9:
                case 10: particle(entity, 5, 0.05);
                    break;

                case 11:
                case 12: particle(entity, 6, 0.075);
                    break;

                case 13:
                case 14: particle(entity, 7, 0.1);
                    break;

                case 15:
                case 16: particle(entity, 8, 0.125);
                    break;

                case 17:
                case 18: particle(entity, 9, 0.15);
                    break;

                case 19:
                case 20: particle(entity, 10, 0.175);
                    break;

                default:
            }
        }
    }
}
