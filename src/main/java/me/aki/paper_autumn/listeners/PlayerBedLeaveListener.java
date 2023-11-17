package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import static me.aki.paper_autumn.listeners.PlayerBedEnterListener.sleepyPlayers;

public class PlayerBedLeaveListener implements Listener {

    Object object = Config.get("bedskip");
    String string = String.valueOf(object);

    @EventHandler
    public void sleep(PlayerBedLeaveEvent event) {
        if(string.equals("true")) {
            Player player = event.getPlayer();
            sleepyPlayers.remove(player.getUniqueId().toString());
        }
    }
}
