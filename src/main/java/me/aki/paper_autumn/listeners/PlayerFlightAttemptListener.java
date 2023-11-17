package me.aki.paper_autumn.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerFlightAttemptListener implements Listener {
    @EventHandler
    public void onFlightAttempt(PlayerToggleFlightEvent event){
        Player player = event.getPlayer();
        if(player.getVehicle() instanceof Dolphin){
            Dolphin dolphin = (Dolphin) player.getVehicle();

        }
    }
}
