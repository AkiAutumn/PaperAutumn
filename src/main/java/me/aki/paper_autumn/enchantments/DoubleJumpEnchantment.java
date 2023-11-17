package me.aki.paper_autumn.enchantments;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.enchantments.EnchantmentRarity;
import me.aki.paper_autumn.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DoubleJumpEnchantment extends Enchantment implements Listener {

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        /*

        Player player = event.getPlayer();
        if(player.getEquipment().getBoots().getEnchantments().containsKey(this)){
            if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)){
                event.setCancelled(true);
                player.setVelocity(new Vector(0, 0.5, 0));

                TODO: Enchantment naturally obtainable
                        Prevent AirJumping
                        Manage AllowFlight (GameMode switches)

            }
        }
        */
    }

    public DoubleJumpEnchantment(String namespace){
        super(NamespacedKey.minecraft(namespace));
    }

    @Override
    public @NotNull String getName() {
        return "Double Jump";
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_FEET;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return null;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }
}
