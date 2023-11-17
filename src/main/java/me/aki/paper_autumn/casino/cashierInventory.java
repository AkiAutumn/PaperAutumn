package me.aki.paper_autumn.casino;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import me.aki.paper_autumn.utils.particleData;
import me.aki.paper_autumn.utils.particleEffects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;

import static me.aki.paper_autumn.casino.casino.*;

public class cashierInventory implements Listener {

    public static Inventory cashierInventory;
    String cashierInventoryName = ChatColor.DARK_GRAY + "How can i help you?";

    public static void createInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "How can i help you?");

        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        ItemStack daily = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta dailyMeta = item.getItemMeta();
        dailyMeta.setDisplayName(ChatColor.AQUA + "Collect a daily tip!");
        daily.setItemMeta(dailyMeta);

        ItemStack cosmetics = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta cosmeticsmeta = cosmetics.getItemMeta();
        cosmeticsmeta.setDisplayName(ChatColor.DARK_PURPLE + "Cosmetics Menu");
        cosmetics.setItemMeta(cosmeticsmeta);

        inv.setItem(0, item);
        inv.setItem(1, item);
        inv.setItem(2, item);
        inv.setItem(3, item);
        inv.setItem(4, daily);
        inv.setItem(5, item);
        inv.setItem(6, cosmetics);
        inv.setItem(7, item);
        inv.setItem(8, item);

        cashierInventory = inv;
    }

    public static Inventory cosmeticsInventory;
    String cosmeticsInventoryName = ChatColor.DARK_GRAY + "Buy or select an cosmetic!";

    public static void createCosmeticInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Buy or select an cosmetic!");

        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        ItemStack none = new ItemStack(Material.BARRIER);
        ItemMeta noneMeta = item.getItemMeta();
        noneMeta.setDisplayName(ChatColor.RED + "no cosmetic");
        none.setItemMeta(noneMeta);

        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta totemMeta = item.getItemMeta();
        totemMeta.setDisplayName(ChatColor.GREEN + "Totem Helix");
        totem.setItemMeta(totemMeta);

        ItemStack rain = new ItemStack(Material.WATER_BUCKET);
        ItemMeta rainMeta = item.getItemMeta();
        rainMeta.setDisplayName(ChatColor.BLUE + "Rain");
        rain.setItemMeta(rainMeta);

        inv.setItem(0, item); inv.setItem(9, item); inv.setItem(18, item);
        inv.setItem(1, item); inv.setItem(10, item); inv.setItem(19, item);
        inv.setItem(2, item); inv.setItem(11, totem); inv.setItem(20, item);
        inv.setItem(3, item); inv.setItem(12, item); inv.setItem(21, item);
        inv.setItem(4, item); inv.setItem(13, none); inv.setItem(22, item);
        inv.setItem(5, item); inv.setItem(14, item); inv.setItem(23, item);
        inv.setItem(6, item); inv.setItem(15, rain); inv.setItem(24, item);
        inv.setItem(7, item); inv.setItem(16, item); inv.setItem(25, item);
        inv.setItem(8, item); inv.setItem(17, item); inv.setItem(26, item);

        cosmeticsInventory = inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) throws IOException {

        // CASHIER INVENTORY

        if (event.getView().getTitle().contains(cashierInventoryName)) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
                return;
            }

            switch (event.getSlot()) {
                case 4:
                    event.setCancelled(true);

                    int amount = 1000;
                    LocalDate localDate = LocalDate.now();

                    if (PlayerConfig.contains(player.getUniqueId() + ".lastDaily")) {

                        Object o = PlayerConfig.get(player.getUniqueId() + ".lastDaily");
                        Object casinoStatsDailytip = Config.get("casino.dailyTipsCollected");

                        assert casinoStatsDailytip != null;
                        assert o != null;
                        if (o.equals(localDate.toString())) {
                            denyAction(player);
                        } else {

                            EconomySystem.addMoney(player.getUniqueId().toString(), BigInteger.valueOf(amount));
                            confirmAction(player);

                            player.sendMessage(WalletPrefix + ChatColor.AQUA + "Daily Tip" + ChatColor.WHITE + " collected! " + ChatColor.GRAY + "(+" + amount + " Credits)");

                            Config.set("casino.dailyTipsCollected", ((int) casinoStatsDailytip + 1));

                            PlayerConfig.set(player.getUniqueId() + ".lastDaily", localDate.toString());
                        }
                    } else {
                        PlayerConfig.set(player.getUniqueId() + ".lastDaily", "0/0/0");
                    }
                    break;

                case 6:
                    event.setCancelled(true);
                    player.closeInventory();
                    player.updateInventory();

                    createCosmeticInventory(player);
                    player.openInventory(cosmeticsInventory);

                    break;

                default:

            }
        }

        // COSMETICS INVENTORY

        if (event.getView().getTitle().contains(cosmeticsInventoryName)) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
                return;
            }

            particleData particle = new particleData(player.getUniqueId());

            if(particle.hasID()) {
                particle.endTask();
                particle.removeID();
            }

            particleEffects effects = new particleEffects(player);

            switch (event.getSlot()) {
                case 13:
                    player.closeInventory();
                    player.updateInventory();
                    PlayerConfig.set(player.getUniqueId() + ".cosmetic", "none");
                    confirmAction(player);
                    break;

                case 11:

                    effects.startHelix(Particle.TOTEM);
                    player.closeInventory();
                    player.updateInventory();
                    PlayerConfig.set(player.getUniqueId() + ".cosmetic", "totemHelix");
                    confirmAction(player);
                    break;

                case 15:
                    effects.startRain();
                    player.closeInventory();
                    player.updateInventory();
                    PlayerConfig.set(player.getUniqueId() + ".cosmetic", "fire");
                    confirmAction(player);

                    break;

                default:
            }
        }
    }
}
