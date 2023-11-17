package me.aki.paper_autumn.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.reviveGhost;

public class PickupItemListener implements Listener {
    @EventHandler
    public void onCollect(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();

        if (ghosts.containsKey(player.getUniqueId().toString())){
            if(item.getItemStack().hasItemMeta()) {
                if (item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + player.getName() + "'s Rose")) {
                    event.setCancelled(true);
                    item.remove();
                    reviveGhost(player.getUniqueId());
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(item.getItemStack().getType().equals(Material.WITHER_ROSE)){
            if(item.getItemStack().hasItemMeta()){
                if(item.getItemStack().getItemMeta().hasEnchant(Enchantment.LOYALTY)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
