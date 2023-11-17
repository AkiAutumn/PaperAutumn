package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.math.BigInteger;

import static me.aki.paper_autumn.casino.casino.*;
import static me.aki.paper_autumn.casino.games.blackjack.startBlackjack;
import static me.aki.paper_autumn.casino.games.coinflip.startCoinFlip;
import static me.aki.paper_autumn.casino.games.crash.startCrash;
import static me.aki.paper_autumn.casino.games.crash.stopCrash;
import static me.aki.paper_autumn.enchantments.economy.EconomySystem.hasEnoughMoney;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws IOException {

        Player player = event.getPlayer();

        player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1, 2);
        event.setMessage((player.isOp() ? ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage()));
        event.setCancelled(true);

        for(Player everyone : Bukkit.getOnlinePlayers()){
            String message = event.getMessage() + " ";
            String name = everyone.getName();
            String prefix = getCustomChatColor(player) + player.getName() + ChatColor.GRAY + ": " + ChatColor.RESET;

            if(message.contains(name)) {
                String[] split = message.split(name);

                String editedMessage = split[0] + getCustomChatColor(Bukkit.getPlayer(name)) + name + ChatColor.RESET + split[1];
                everyone.sendMessage(prefix + editedMessage);
                everyone.playSound(everyone.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 2, 2);

            } else {
                everyone.sendMessage(prefix + message);
            }
        }


        Object object = PlayerConfig.get(player.getUniqueId() + ".safetyQuestion");
        //Object objectCasinoGame = PlayerConfig.get(player.getUniqueId() + ".CancelNextChatMessage");
        //String casinoGame = (String) objectCasinoGame;
        //Object objectCurrentCasinoGame = PlayerConfig.get(player.getUniqueId() + ".CurrentCasinoGame");
        //Object objectCasinoCrash = PlayerConfig.get(player.getUniqueId() + ".CasinoCrash");

        if (object.equals(true)) {
            if (!event.getMessage().contains("world delete")) {

                try {
                    PlayerConfig.set(player.getUniqueId() + ".safetyQuestion", false);
                    sendActionbar(player, ChatColor.RED + "Deletion cancelled");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        // STOP CRASH GAME
        /*
        if (objectCasinoCrash.equals(true)) {
            if (event.getMessage().equalsIgnoreCase("stop")) {
                event.setCancelled(true);
                stopCrash(player);
                endCasinoGame(player);
            }
        }

        if (casinoGame != null) {
            if (casinoGame.equals("true")) {
                event.setCancelled(true);
                if (!event.getMessage().equalsIgnoreCase("cancel")) {
                    if (event.getMessage().matches("^[0-9]*$")) {

                        CurrentCasinoStake.put(player.getUniqueId().toString(), Integer.parseInt(event.getMessage()));

                        if (!CurrentCasinoStake.get(player.getUniqueId().toString()).equals(0)) {

                            if (hasEnoughMoney(player.getUniqueId().toString(), BigInteger.valueOf(CurrentCasinoStake.get(player.getUniqueId().toString())))) {
                                removeFromWallet(player, CurrentCasinoStake.get(player.getUniqueId().toString()));
                                PlayerConfig.set(player.getUniqueId() + ".CancelNextChatMessage", false);

                                switch (objectCurrentCasinoGame.toString()) {
                                    case "coinflip":
                                        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                                            public void run() {
                                                startCoinFlip(player);
                                            }
                                        }, 1L);
                                        break;
                                    case "crash":
                                        startCrash(player);
                                        break;
                                    case "blackjack":
                                        startBlackjack(player);
                                        break;
                                }
                            } else {
                                player.sendMessage(WalletPrefix + "You don't have that many Credits :(" + "\n"
                                        + ChatColor.GRAY + "(your balance: " + String.valueOf(EconomySystem.getMoney(player.getUniqueId().toString())) + " Credits)");
                            }

                        } else {
                            player.sendMessage(CasinoPrefix + "You can't gamble for free!");
                        }
                    } else {
                        player.sendMessage(CasinoPrefix + "Please write numbers only!");
                    }

                } else {
                    player.sendMessage(CasinoPrefix + "Cancelled Game");
                    PlayerConfig.set(player.getUniqueId() + ".CancelNextChatMessage", false);
                    PlayerConfig.set(player.getUniqueId() + ".CurrentCasinoGame", "none");
                    inCasinoGame.put(player.getUniqueId().toString(), false);
                }
            }
        }
         */
    }
}
