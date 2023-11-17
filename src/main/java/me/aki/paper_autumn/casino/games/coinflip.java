package me.aki.paper_autumn.casino.games;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.Random;

import static me.aki.paper_autumn.casino.casino.*;

public class coinflip {

    public static Location CoinSpawnLocation = (Location) Config.get("casino.coinflip.coinLocation");
    static int CoinFlipStatsWinning = (int) Config.get("casino.coinflip.highestWinning.amount");


    public static void startCoinFlip(Player player){

        ItemStack Coin = new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        ItemMeta CoinMeta = Coin.getItemMeta();
        assert CoinMeta != null;
        CoinMeta.setDisplayName(ChatColor.GOLD + "Coin");
        Coin.setItemMeta(CoinMeta);

        Item item = player.getWorld().dropItemNaturally(CoinSpawnLocation, Coin);
        item.setPickupDelay(999999999);
        item.getWorld().spawnParticle(Particle.CLOUD, item.getLocation(), 5, 0.1, 0.1, 0.1, 0.05);

        int coinTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                item.setVelocity(new Vector(0, 0.4, 0));
                item.getWorld().playSound(item.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 2);
            }
        }, 0L, 20L); //0 Tick initial delay, 20 Tick (1 Second) between repeats

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                Bukkit.getScheduler().cancelTask(coinTask);
                item.getWorld().spawnParticle(Particle.CLOUD, item.getLocation(), 5, 0.1, 0.1, 0.1, 0.05);
                item.remove();

                Random random = new Random();
                String Result;

                if (random.nextBoolean()) {
                    Result = "number";
                } else {
                    Result = "head";
                }

                if(Result.equals("number")){

                    int winnings = CurrentCasinoStake.get(player.getUniqueId().toString()) * 2;

                    confirmAction(player);
                    addToWallet(player, winnings);

                    if(winnings > CoinFlipStatsWinning) {
                        try {
                            globalRecordBroadcast("winning", player, "Coinflip", winnings);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }

                    try {
                        endCasinoGame(player);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                } else {
                    denyAction(player);

                    try {
                        endCasinoGame(player);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }, 100L);
    }

}
