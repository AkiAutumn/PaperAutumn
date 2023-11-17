package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.ArrayList;
import java.util.Random;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;

public class PlayerBedEnterListener implements Listener {

    Object object = Config.get("bedskip");
    String string = String.valueOf(object);
    public static ArrayList sleepyPlayers = new ArrayList();

    public void sendRandomBedMSG(Player p) {
        ArrayList<String> BedMessages = new ArrayList<String>() {
            {
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is taking a nap." + ChatColor.WHITE +  " (" + sleepyPlayers.size() + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " fell asleep." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " went to bed." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is too tired." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is slowly closing his/her eyes." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is having sweet dreams." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is gonna have wet dreams." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is resting." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " had enough for today. (╯°□°）╯︵ ┻━┻" + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " has worked enough for today." + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " is going to have a good night (｡•̀ᴗ-)✧" + ChatColor.WHITE + " (" + sleepyPlayers.size() + "/" + Bukkit.getOnlinePlayers().size() + ")");
                add(ChatColor.GRAY + "" + getCustomChatColor(p) + p.getName() + ChatColor.RESET + "" + ChatColor.GRAY + " flipped enough tables for today. ┬─┬ ノ( ゜-゜ノ)" + ChatColor.WHITE + " (" + sleepyPlayers.size()  + "/" + Bukkit.getOnlinePlayers().size() + ")");
            }
        };

        Random random = new Random();
        int index = random.nextInt(BedMessages.size());
        String message = BedMessages.get(index);

        for (Player everyone : Bukkit.getOnlinePlayers()) {
            everyone.sendMessage(message);
        }
    }

    @EventHandler
    public void sleep(PlayerBedEnterEvent event) {
        Player p = event.getPlayer();

        if (ghosts.containsKey(p.getUniqueId().toString())){
            event.setCancelled(true);
        }

        if (string.equals("true")) {

            if(event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)){
                sleepyPlayers.add(p.getUniqueId().toString());
                sendRandomBedMSG(p);

                if(sleepyPlayers.size() >= (Bukkit.getOnlinePlayers().size() / 2)){
                    p.getWorld().setTime(0);
                    for (Player everyone : Bukkit.getOnlinePlayers()) {
                        everyone.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "The spooky night was skipped!");
                        sleepyPlayers.clear();
                    }
                }
            }
        }
    }
}
