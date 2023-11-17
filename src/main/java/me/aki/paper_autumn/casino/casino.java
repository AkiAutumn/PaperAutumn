package me.aki.paper_autumn.casino;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class casino {

    public static String WalletPrefix = (ChatColor.BLUE + "Wallet" + ChatColor.GRAY + " | " + ChatColor.WHITE);
    public static String CoinflipPrefix = (ChatColor.GOLD + "Coinflip" + ChatColor.GRAY + " | " + ChatColor.WHITE);
    public static String CrashPrefix = (ChatColor.AQUA + "Crash" + ChatColor.GRAY + " | " + ChatColor.WHITE);
    public static String BlackjackPrefix = (ChatColor.DARK_GRAY + "Blackjack" + ChatColor.GRAY + " | " + ChatColor.WHITE);
    public static String SlotsPrefix = (ChatColor.LIGHT_PURPLE + "Slots" + ChatColor.GRAY + " | " + ChatColor.WHITE);
    public static String CasinoPrefix = (ChatColor.RED + "Casino" + ChatColor.GRAY + " | " + ChatColor.WHITE);


    public static Map<String, Boolean> inCasinoGame = new HashMap<String, Boolean>();
    public static Map<String, Integer> CurrentCasinoStake = new HashMap<String, Integer>();

    public static void confirmAction(Player player) {
        sendActionbar(player, ChatColor.GREEN + "" + ChatColor.BOLD + "✓");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 10, 2);
    }

    public static void removeFromWallet(Player player, int amount) {
        EconomySystem.removeMoney(player.getUniqueId().toString(), BigInteger.valueOf(amount));
        player.sendMessage(WalletPrefix + ChatColor.RED + "-" + " " + amount + " Credits");
        player.playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 10, 2);
    }

    public static void addToWallet(Player player, int amount) {
        EconomySystem.addMoney(player.getUniqueId().toString(), BigInteger.valueOf(amount));
        player.sendMessage(WalletPrefix + ChatColor.GREEN + "+" + " " + amount + " Credits");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 2);
    }

    public static void denyAction(Player player) {
        sendActionbar(player, ChatColor.RED + "" + ChatColor.BOLD + "×");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 10, 0);
    }

    public static void endCasinoGame(Player player) throws IOException {
        PlayerConfig.set(player.getUniqueId() + ".CurrentCasinoGame", "none");
        PlayerConfig.set(player.getUniqueId() + ".CasinoCrash", false);
        inCasinoGame.put(player.getUniqueId().toString(), false);
    }

    public static void restoreCasinoStats() throws IOException {
        if(!Config.contains("casino.dailyTipsCollected"))
            Config.set("casino.dailyTipsCollected", 0);
        if(!Config.contains("casino.coinflip.highestWinning"))
            Config.set("casino.coinflip.highestWinning", 0);
        if(!Config.contains("casino.crash.highestWinning"))
            Config.set("casino.crash.highestWinning", 0);
        if(!Config.contains("casino.crash.highestMultiplier"))
            Config.set("casino.crash.highestMultiplier", 0.0);
        if(!Config.contains("casino.blackjack.highestWinning.amount"))
            Config.set("casino.blackjack.highestWinning.amount", 0);
    }

    public static void restoreCasinoGameStats(Player player) throws IOException {
        if(!PlayerConfig.contains(player + ".CasinoCrash"))
            PlayerConfig.set(player.getUniqueId() + ".CasinoCrash", false);
        if(!PlayerConfig.contains("casino.coinflip.coinLocation"))
            PlayerConfig.set("casino.coinflip.coinLocation", player.getLocation());

    }

    public static void globalRecordBroadcast(String typeOfRecord, Player player, String gameName, double amount) throws IOException {

        switch (typeOfRecord) {
            case "winning":
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.sendMessage(CasinoPrefix + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " just set a new global record for the highest " + ChatColor.BOLD + gameName + ChatColor.WHITE + " payout!" + "\n"
                            + ChatColor.BOLD + "-> " + (int) amount + " Credits");
                }
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 2 );
                Config.set("casino." + gameName.toLowerCase() + ".highestWinning.amount", (int) amount);
                Config.set("casino." + gameName.toLowerCase() + ".highestWinning.uuid", player.getUniqueId().toString());
                Config.set("casino." + gameName.toLowerCase() + ".highestWinning.player", player.getName());
                break;

            case "multiplier":
                for (Player everyone : Bukkit.getOnlinePlayers()) {
                    everyone.sendMessage(CasinoPrefix + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " just set a new global record for the highest " + ChatColor.BOLD + gameName + ChatColor.WHITE + " multiplier!" + "\n"
                            + ChatColor.BOLD + "-> x" + amount);
                }
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 2 );
                Config.set("casino." + gameName.toLowerCase() + ".highestMultiplier.amount", amount);
                Config.set("casino." + gameName.toLowerCase() + ".highestMultiplier.uuid", player.getUniqueId().toString());
                Config.set("casino." + gameName.toLowerCase() + ".highestMultiplier.player", player.getName());
                break;
        }
    }
}
