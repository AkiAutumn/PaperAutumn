package me.aki.paper_autumn.utils;

import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.enchantments.DoubleJumpEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SavedItems {

    public static ItemStack oneTimeElytra() {
        ItemStack itemStack = new ItemStack(Material.ELYTRA);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "One-Time-Elytra");
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack specialFeather() {
        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Magical Feather");
        itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack doubleJumpBoots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        item.addEnchantment(Main.doubleJumpEnchantment, 1);
        return item;
    }
    public static ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(player.getName() + "'s Head");
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack infinityBlock() {
        ItemStack block = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Infinity Block");
        block.setItemMeta(blockMeta);

        return block;
    }

    public static ItemStack infinityCart() {
        ItemStack block = new ItemStack(Material.MINECART);
        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Infinity Minecart");
        block.setItemMeta(blockMeta);

        return block;
    }

    public static ItemStack tpBow() {
        ItemStack block = new ItemStack(Material.BOW);
        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "TP Bow");
        blockMeta.setUnbreakable(true);
        block.setItemMeta(blockMeta);

        return block;
    }

    public static ItemStack x3Pickaxe() {
        ItemStack block = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "3x3 Pickaxe");
        blockMeta.setUnbreakable(true);
        block.setItemMeta(blockMeta);

        return block;
    }

    public static ItemStack vorpal_katana() {
        ItemStack diamondVorpal = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diamondVorpalMeta = diamondVorpal.getItemMeta();
        diamondVorpalMeta.setUnbreakable(true);
        diamondVorpalMeta.setDisplayName(ChatColor.DARK_AQUA + "Vorpal Katana");
        diamondVorpal.setItemMeta(diamondVorpalMeta);

        return diamondVorpal;
    }

    public static ItemStack vorpal_katana_ability() {
        ItemStack goldenVorpal = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta goldenVorpalMeta = goldenVorpal.getItemMeta();
        goldenVorpalMeta.setUnbreakable(true);
        goldenVorpalMeta.setDisplayName(ChatColor.DARK_PURPLE + "Vorpal Katana");
        goldenVorpal.setItemMeta(goldenVorpalMeta);

        return goldenVorpal;
    }

}