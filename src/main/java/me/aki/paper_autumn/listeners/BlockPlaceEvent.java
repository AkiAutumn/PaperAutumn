package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;

public class BlockPlaceEvent implements Listener {

    @EventHandler
    public void onPlace(org.bukkit.event.block.BlockPlaceEvent event) throws IOException {

        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInOffHand();
        ItemMeta meta = itemStack.getItemMeta();
        Location blockLoc = block.getLocation();

        if (ghosts.containsKey(player.getUniqueId().toString())){
            event.setCancelled(true);
        }

        assert meta != null;
        if(player.getWorld().getName().equalsIgnoreCase("tutorial")){
            if(!player.getGameMode().equals(GameMode.CREATIVE)){
                event.setBuild(false);
            }
        }

        if(block.getBlockData().getMaterial().equals(Material.WHITE_STAINED_GLASS)){
            if(player.getItemInHand().equals(SavedItems.infinityBlock())){
                player.setItemInHand(SavedItems.infinityBlock());

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                    @Override
                    public void run() {
                        if(block.getType().equals(Material.WHITE_STAINED_GLASS)) {
                            block.getWorld().spawnParticle(Particle.CLOUD, block.getLocation().add(0.5, 0.5, 0.5), 10, 0.5, 0.5, 0.5, 0.01);
                            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 1,2 );
                            block.setType(Material.AIR);
                        }
                    }
                }, 100L); //20 Tick (1 Second) delay before run() is called
            }
        }

        try {
            if (meta.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Invisible Item Frame")) {

                blockLoc.getWorld().getBlockAt(blockLoc).setType(Material.AIR);
                ItemFrame frame = (ItemFrame) blockLoc.getWorld().spawn(
                        blockLoc.getWorld().getBlockAt(blockLoc).getLocation().getBlock().getLocation().add(0, 0, 0),
                        ItemFrame.class);
                frame.setVisible(false);
                player.sendMessage("Placed Invisible Item Frame");

            }
        } catch (NullPointerException ignored) {
        }
    }
}
