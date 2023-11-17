package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.awt.*;
import java.io.IOException;

import static me.aki.paper_autumn.casino.casino.denyAction;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class colorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 3) {
            int r = Integer.parseInt(args[0]);
            int g = Integer.parseInt(args[1]);
            int b = Integer.parseInt(args[2]);
            ChatColor newColor = ChatColor.of(new Color(r, g, b));

            if(validInt(r) && validInt(g) && validInt(b)) {
                try {
                    PlayerConfig.set(player.getUniqueId().toString() + ".chatColor.r", r);
                    PlayerConfig.set(player.getUniqueId().toString() + ".chatColor.g", g);
                    PlayerConfig.set(player.getUniqueId().toString() + ".chatColor.b", b);

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 2);
                    sendActionbar(player, newColor + "* That's your new colour *");
                    player.setPlayerListName(getCustomChatColor(player) + player.getName());

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            } else {
                denyAction(player);
                sendActionbar(player, ChatColor.RED + "Invalid RGB format");
            }
        } else {
            denyAction(player);
        }
        return true;
    }

    public boolean validInt(int val) {
        if (val > 255 || val < 0) {
            return false;
        } else {
            return true;
        }
    }
}
