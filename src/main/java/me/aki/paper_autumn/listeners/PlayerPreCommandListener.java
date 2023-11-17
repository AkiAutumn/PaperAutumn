package me.aki.paper_autumn.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

import static me.aki.paper_autumn.casino.casino.denyAction;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class PlayerPreCommandListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) throws IOException {

        Player player = event.getPlayer();
        String msg = event.getMessage();

        if(!msg.toLowerCase().equals("/system tps")) {
            Date date = new Date();
            writeFile(date + " | " + player.getName() + " | " + msg);
        }

        if(event.getMessage().toLowerCase().startsWith("/msg")){
            event.setCancelled(true);
            String[] msgArray = event.getMessage().split(" ");
            String targetName = msgArray[1];
            Boolean delivered = false;

            for(Player target : Bukkit.getServer().getOnlinePlayers()){
                if(target.getName().equals(targetName)){
                    player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 1, 2);
                    String prefix = ChatColor.GRAY + "[" + getCustomChatColor(player) + player.getName() + ChatColor.GRAY + " » " + getCustomChatColor(target) + target.getName() + ChatColor.GRAY + "] ";

                    String[] modifiedArray = Arrays.copyOfRange(msgArray, 2, msgArray.length);
                    StringBuffer sb = new StringBuffer();
                    for(int i = 0; i < modifiedArray.length; i++) {
                        sb.append(modifiedArray[i]);
                        sb.append(" ");
                    }
                    String str = sb.toString();

                    player.sendMessage(prefix + ChatColor.RESET + str);
                    target.sendMessage(prefix + ChatColor.RESET + str);
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 2);
                    delivered = true;
                }
            }

            if(!delivered){
                denyAction(player);
                sendActionbar(player, ChatColor.RED + "Couldn't deliver your DM");
            }
        }
    }

    public static void writeFile(String msg) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("cmdLog.txt", true));
        pw.write(msg);
        pw.write(String.format("%n"));
        pw.close();
    }
}

