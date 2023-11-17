package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.utils.particleData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

import static me.aki.paper_autumn.commands.HideCommand.hidden;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }
        hidden.remove(player);
        PlayerConfig.set(player.getUniqueId() + ".firstJoin", false);

        event.setQuitMessage("< " + getCustomChatColor(player) + player.getName() + ChatColor.GRAY + " left.");

        /* TODO: Throws Error
        particleData p = new particleData(player.getUniqueId());
        if(p.hasID()) {
            p.endTask();
        }
         */
    }
}
