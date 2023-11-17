package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import static me.aki.paper_autumn.economyGame.functions.obtainMoney;

public class PlayerExpChangeListener implements Listener {

    @EventHandler
    public void onChange(PlayerExpChangeEvent event) {
     Player player = event.getPlayer();
     int amount = event.getAmount();

     Object obj = Config.get("earnEconomy");
     String str = String.valueOf(obj);

        if(str.equalsIgnoreCase("true")) {
            event.setAmount(0);
            obtainMoney(player, amount);
        }
    }
}
