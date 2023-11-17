package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;
import java.util.Objects;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.damaged;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Object object = Config.get("deathban");
        String string = Objects.requireNonNull(object).toString();

        Object object2 = Config.get("event.advancedBowDamage");
        String string2 = Objects.requireNonNull(object2).toString(); ;

        damaged.put(player.getUniqueId().toString(), System.currentTimeMillis());

        if (string2.equalsIgnoreCase("true")) {
            player.getInventory().clear();
        }

        if(string.equals("true")){
            Date expires = new Date(System.currentTimeMillis()+60*5*1000);
            //Since there are 1000 miliseconds in one second, 60 seconds in one minute, and 60 minutes in an hour, by adding 60*60*1000 to the current time, you are adding an hour to the bantime.
            String ascii =
                    "     __     " + "\n" +
                    " ___|  |___ " + "\n" +
                    " |__    __| " + "\n" +
                    "    |  |    " + "\n" +
                    "    |  |    " + "\n" +
                    "    |  |    " + "\n" +
                    "    |  |    " + "\n" +
                    "    |__|    " + "\n";

            String reason = " " + "\n" + "\n" + "\n" + ChatColor.DARK_RED + "☠" + "\n" + "\n" + ChatColor.RED + "Du bist gestorben :(" + "\n" + "\n" + ChatColor.GRAY  + ascii + "\n" + "\n" + " ";
            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), reason, expires, "Plugin");
            player.getInventory().clear();
            player.kickPlayer(reason);
        }
    }
}
