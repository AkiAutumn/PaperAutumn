package me.aki.paper_autumn.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if(itemStack.getType().equals(Material.POTION)){
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            PotionData potionData = potionMeta.getBasePotionData();
            if(potionData.getType().equals(PotionType.INVISIBILITY)){
                event.setCancelled(true);
                boolean upgraded = potionData.isUpgraded();
                int amplifier = 0;
                if(upgraded){
                    amplifier = 1;
                }

                boolean extended = potionData.isExtended();
                int duration = 3600;
                if(extended){
                    duration = 9600;
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, amplifier, false, false, false ));
                player.setItemInHand(new ItemStack(Material.GLASS_BOTTLE));
            }
        }

    }
}
