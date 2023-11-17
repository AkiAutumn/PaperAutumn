package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class BlockBreakListener implements Listener {

    private void particle(Block block, Integer count, Double splatter){
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(115, 28, 28), 2);

        count = count * 2;
        block.getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(0.5, 0.5, 0.5), count, 0.25, 0.25, 0.25, 5, dustOptions);

        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        int splatterCount = (int) (splatter * 200);
        block.getWorld().spawnParticle(Particle.ITEM_CRACK, block.getLocation().add(0.5,0.5,0.5), splatterCount, 0.75, 0.25, 0.75, splatter, itemStack);
    }

    @EventHandler
    public void onBreak(org.bukkit.event.block.BlockBreakEvent event) throws IOException {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Object obj = Config.get("earnEconomy");
        String str = String.valueOf(obj);

        if (ghosts.containsKey(player.getUniqueId().toString())){
            event.setCancelled(true);
        }

        if(block.getType().equals(Material.PLAYER_HEAD)) {
            if(player.getItemInHand().getType().equals(Material.IRON_AXE) ||
                    player.getItemInHand().getType().equals(Material.GOLDEN_AXE) ||
                    player.getItemInHand().getType().equals(Material.DIAMOND_AXE) ||
                    player.getItemInHand().getType().equals(Material.NETHERITE_AXE)) {
                event.setCancelled(true);
                particle(block, 10, 0.1);
                block.setType(Material.SKELETON_SKULL);
            }
        }

        if(str.equalsIgnoreCase("true")) {
            Material material = block.getType();

            switch (material.toString().toLowerCase()) {
                case "coal_ore":
                case "nether_gold_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(10);
                    break;
                case "nether_quartz_ore":
                case "copper_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(20);
                    break;
                case "iron_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(30);
                    break;
                case "gold_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(40);
                    break;
                case "lapis_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(50);
                    break;
                case "redstone_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(60);
                    break;
                case "diamond_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(70);
                    break;
                case "emerald_ore":
                    player.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class).setExperience(100);
                    break;
            }
        }
    }
}
