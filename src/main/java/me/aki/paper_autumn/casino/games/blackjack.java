package me.aki.paper_autumn.casino.games;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static me.aki.paper_autumn.casino.casino.*;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class blackjack implements Listener {

    static Map<String, Integer> BlackJackValue = new HashMap<String, Integer>();
    static Map<String, Boolean> BlackJackDoubleable = new HashMap<String, Boolean>();
    static int BlackjackStatsWinning = (int) Config.get("casino.blackjack.highestWinning.amount");

    
    public static void startBlackjack(Player player) throws IOException {
        
        BlackJackValue.put(player.getUniqueId().toString(), 0);
        BlackJackValue.put(player.getUniqueId().toString() + ".dealer", 0);
        BlackJackDoubleable.put(player.getUniqueId().toString(), true);

        obtainControls(player);
        rollCard(player);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                try {
                    rollCard(player);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }, 10L);
    }

    static void rollCard(Player player) throws IOException {

        boolean doubleable = BlackJackDoubleable.get(player.getUniqueId().toString());
        if(!doubleable)
            player.getInventory().setItem(2, null);

        //for  player

        Random r = new Random();
        int low = 1; // this number is included
        int high = 12; // this number is excluded
        int result = r.nextInt(high-low) + low;

        switch (result){
            case 1:
            case 11:
                obtainCard(player, "Ace");
                addToHand(player, result, false);
                break;
            case 2:
                obtainCard(player, "2");
                addToHand(player, result, false);
                break;
            case 3:
                obtainCard(player, "3");
                addToHand(player, result, false);
                break;
            case 4:
                obtainCard(player, "4");
                addToHand(player, result, false);
                break;
            case 5:
                obtainCard(player, "5");
                addToHand(player, result, false);
                break;
            case 6:
                obtainCard(player, "6");
                addToHand(player, result, false);
                break;
            case 7:
                obtainCard(player, "7");
                addToHand(player, result, false);
                break;
            case 8:
                obtainCard(player, "8");
                addToHand(player, result, false);
                break;
            case 9:
                obtainCard(player, "9");
                addToHand(player, result, false);
                break;
            case 10:

                int low10 = 1; // this number is included
                int high10 = 5; // this number is excluded
                int result10 = r.nextInt(high10-low10) + low10;

                switch (result10){
                    case 1: obtainCard(player, "10");
                        addToHand(player, result, false);
                        break;
                    case 2: obtainCard(player, "Jack");
                        addToHand(player, result, false);
                        break;
                    case 3: obtainCard(player, "Queen");
                        addToHand(player, result, false);
                        break;
                    case 4: obtainCard(player, "King");
                        addToHand(player, result, false);
                        break;
                }

                break;
        }

        //for  dealer

        int resultD = r.nextInt(high-low) + low;

        switch (resultD){
            case 1:
            case 11:
                addToHand(player, resultD, true);
                break;
            case 2:
                addToHand(player, resultD, true);
                break;
            case 3:
                addToHand(player, resultD, true);
                break;
            case 4:
                addToHand(player, resultD, true);
                break;
            case 5:
                addToHand(player, resultD, true);
                break;
            case 6:
                addToHand(player, resultD, true);
                break;
            case 7:
                addToHand(player, resultD, true);
                break;
            case 8:
                addToHand(player, resultD, true);
                break;
            case 9:
                addToHand(player, resultD, true);
                break;
            case 10:

                int low10 = 1; // this number is included
                int high10 = 5; // this number is excluded
                int resultD10 = r.nextInt(high10-low10) + low10;

                switch (resultD10){
                    case 1:
                        addToHand(player, result, true);
                        break;
                    case 2:
                        addToHand(player, result, true);
                        break;
                    case 3:
                        addToHand(player, result, true);
                        break;
                    case 4:
                        addToHand(player, result, true);
                        break;
                }

                break;
        }
    }

    static void obtainCard(Player player, String cardName){

        int id = (int) Config.get("savedMapView." + cardName + ".id");

        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = ((MapMeta) itemStack.getItemMeta());
        mapMeta.setMapId((Integer) id);
        itemStack.setItemMeta(mapMeta);

        player.getInventory().setItemInOffHand(itemStack);
    }

    static void obtainControls(Player player){
        ItemStack stand = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
        ItemMeta standMeta = stand.getItemMeta();
        standMeta.setDisplayName(ChatColor.RED + "Stand");
        stand.setItemMeta(standMeta);

        ItemStack hit = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
        ItemMeta hitMeta = stand.getItemMeta();
        hitMeta.setDisplayName(ChatColor.AQUA + "Hit");
        hit.setItemMeta(hitMeta);

        ItemStack doubled = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
        ItemMeta doubledMeta = stand.getItemMeta();
        doubledMeta.setDisplayName(ChatColor.BLUE + "Double Down");
        doubled.setItemMeta(doubledMeta);

        ItemStack split = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
        ItemMeta splitMeta = stand.getItemMeta();
        splitMeta.setDisplayName(ChatColor.DARK_BLUE + "Split");
        split.setItemMeta(splitMeta);

        ItemStack fold = new ItemStack(Material.CRIMSON_BUTTON);
        ItemMeta foldMeta = stand.getItemMeta();
        foldMeta.setDisplayName(ChatColor.DARK_RED + "Surrender");
        fold.setItemMeta(foldMeta);

        player.getInventory().setItem(0, stand);
        player.getInventory().setItem(1, hit);
        player.getInventory().setItem(2, doubled);
        player.getInventory().setItem(3, fold);
    }

    static void addToHand(Player player, int value, Boolean dealersHand) throws IOException {

        if(!dealersHand) {
            int currentValue = BlackJackValue.get(player.getUniqueId().toString());
            int newValue = currentValue + value;
            BlackJackValue.put(player.getUniqueId().toString(), newValue);
            sendActionbar(player, "Current hand: " + ChatColor.GOLD + newValue);

            if(newValue > 21)
                loseBlackjack(player);

            if(newValue == 21)
                stand(player);

        } else {
            int currentValue = BlackJackValue.get(player.getUniqueId().toString() + ".dealer");
            int newValue = currentValue + value;
            BlackJackValue.put(player.getUniqueId().toString() + ".dealer", newValue);
        }
    }

    static void stand(Player player) throws IOException {
        int currentValue = BlackJackValue.get(player.getUniqueId().toString());
        int currentDealerValue = BlackJackValue.get(player.getUniqueId().toString() + ".dealer");
        boolean doubleable = BlackJackDoubleable.get(player.getUniqueId().toString());

        if(!doubleable)
            player.getInventory().setItem(2, null);

        if ((currentValue == currentDealerValue) || ((currentDealerValue > 21) && (currentValue > 21))) {
            sendActionbar(player, ChatColor.YELLOW + "Its a tie!" + ChatColor.GRAY + " - Dealers hand: " + currentDealerValue);

            int winning = CurrentCasinoStake.get(player.getUniqueId().toString());
            addToWallet(player, winning);
        } else {
            if (currentValue > 21) {
                loseBlackjack(player);
            }

            if (currentDealerValue > 21) {
                sendActionbar(player, ChatColor.GREEN + "Dealer busted!" + ChatColor.GRAY + " - Dealers hand: " + currentDealerValue);

                int winning = CurrentCasinoStake.get(player.getUniqueId().toString()) * 2;
                addToWallet(player, winning);

                if(winning > (int) BlackjackStatsWinning) {
                    try {
                        globalRecordBroadcast("winning", player, "Blackjack", winning);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

            } else {

                if (currentValue > currentDealerValue) {
                    sendActionbar(player, ChatColor.GREEN + "You've won!" + ChatColor.GRAY + " - Dealers hand: " + currentDealerValue);

                    int winning = CurrentCasinoStake.get(player.getUniqueId().toString()) * 2;
                    addToWallet(player, winning);

                    if(winning > (int) BlackjackStatsWinning) {
                        try {
                            globalRecordBroadcast("winning", player, "Blackjack", winning);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                if (currentValue < currentDealerValue) {
                    sendActionbar(player, ChatColor.RED + "You've lost!" + ChatColor.GRAY + " - Dealers hand: " + currentDealerValue);
                }
            }
        }

        resetBlackjack(player);
    }

    static void doubleDown(Player player) throws IOException {
        int stake = CurrentCasinoStake.get(player.getUniqueId().toString());
        int doubledStake = stake * 2;

            if (EconomySystem.hasEnoughMoney(player.getUniqueId().toString(), BigInteger.valueOf(stake))) {
                CurrentCasinoStake.put(player.getUniqueId().toString(), doubledStake);
                removeFromWallet(player, stake);

                rollCard(player);
                stand(player);
            } else {
                sendActionbar(player, ChatColor.RED + "You don't have enough Credits to double down!");
            }

    }

    static void surrender(Player player) throws IOException {
        int currentDealerValue = BlackJackValue.get(player.getUniqueId().toString() + ".dealer");
        sendActionbar(player, ChatColor.RED + "Surrendered!" + ChatColor.GRAY + " - Dealers hand: " + currentDealerValue);
        int amount = CurrentCasinoStake.get(player.getUniqueId().toString()) / 2;
        addToWallet(player, amount);

        resetBlackjack(player);
    }

    static void loseBlackjack(Player player) throws IOException {
        denyAction(player);
        sendActionbar(player, ChatColor.RED + "BUSTED with a hand of " + ChatColor.DARK_RED + BlackJackValue.get(player.getUniqueId().toString()));

        resetBlackjack(player);
    }

    static void resetBlackjack(Player player) throws IOException {
        player.getInventory().setItemInOffHand(null);
        player.getInventory().setItem(0, null);
        player.getInventory().setItem(1, null);
        player.getInventory().setItem(2, null);
        player.getInventory().setItem(3, null);

        endCasinoGame(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        String itemName = "null";
        try{
            Object object = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
            itemName = String.valueOf(object);
        } catch (NullPointerException e){}
        Object objectCurrentCasinoGame = PlayerConfig.get(player.getUniqueId() + ".CurrentCasinoGame");

        if(objectCurrentCasinoGame != null) {
            if (objectCurrentCasinoGame.toString().equalsIgnoreCase("blackjack"))
                event.setCancelled(true);
        }

        if (itemName.toLowerCase().contains("stand")) {
            BlackJackDoubleable.put(player.getUniqueId().toString(), false);
            stand(player);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 10, 2);
        }

        if (itemName.toLowerCase().contains("hit")) {
            BlackJackDoubleable.put(player.getUniqueId().toString(), false);
            rollCard(player);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 10, 1);
        }

        if (itemName.toLowerCase().contains("double down")) {
            doubleDown(player);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 10, 1);
        }

        if (itemName.toLowerCase().contains("surrender")) {
            surrender(player);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 10, 0);
        }

    }
}
