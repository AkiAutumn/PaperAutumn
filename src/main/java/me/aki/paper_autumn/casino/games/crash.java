package me.aki.paper_autumn.casino.games;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Random;

import static me.aki.paper_autumn.casino.casino.*;

public class crash {

    static int crashTask;
    static double crashMultiplier = 1;
    static double winnings;
    static int CrashStatsWinning = (int) Config.get("casino.crash.highestWinning.amount");
    static double CrashHighestMultiplier = (double) Config.get("casino.crash.highestMultiplier.amount");


    public static void startCrash(Player player) throws IOException {

        PlayerConfig.set(player.getUniqueId() + ".CasinoCrash", true);
        final double stake = CurrentCasinoStake.get(player.getUniqueId().toString());
        winnings = stake;

        crashTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                Random random = new Random();
                boolean lose = random.nextInt(5)==0;

                if(lose){
                    Bukkit.getScheduler().cancelTask(crashTask);
                    player.sendMessage(CrashPrefix + ChatColor.RED + "Your Stake crashed! :(");
                    denyAction(player);

                    try {
                        endCasinoGame(player);
                        crashMultiplier = 1;
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    crashMultiplier = crashMultiplier + 0.25;
                    winnings = stake * crashMultiplier;
                    CurrentCasinoStake.put(player.getUniqueId().toString(), (int) winnings);

                    player.sendMessage(CrashPrefix + "Multiplier: " + crashMultiplier);
                }
            }
        }, 20L, 40L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
    }

    public static void stopCrash(Player player) throws IOException {
        Bukkit.getScheduler().cancelTask(crashTask);
        player.sendMessage(CrashPrefix + "Game stopped");

        int stake = CurrentCasinoStake.get(player.getUniqueId().toString());

        confirmAction(player);
        addToWallet(player, stake);

        if(stake > CrashStatsWinning) {
            globalRecordBroadcast("winning", player, "Crash", stake);
        }

        if(crashMultiplier > CrashHighestMultiplier) {
            globalRecordBroadcast("multiplier", player, "Crash", crashMultiplier);
        }

        try {
            endCasinoGame(player);
            crashMultiplier = 1;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
