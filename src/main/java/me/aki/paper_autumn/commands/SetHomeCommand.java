package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarInvalidArguments;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (sender instanceof Player){

            if (args.length == 0) {

                try {
                    PlayerConfig.set(player.getUniqueId().toString() + ".home.w", player.getWorld().getName());
                    PlayerConfig.set(player.getUniqueId().toString() + ".home.x", player.getLocation().getX());
                    PlayerConfig.set(player.getUniqueId().toString() + ".home.y", player.getLocation().getY());
                    PlayerConfig.set(player.getUniqueId().toString() + ".home.z", player.getLocation().getZ());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sendActionbar(player, (ChatColor.BLUE + "You've successfully set your home! :)"));
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.UI_STONECUTTER_SELECT_RECIPE, 5, 2);

            } else {
                sendActionbarInvalidArguments(player);
            }
        }
        return true;
    }

}
