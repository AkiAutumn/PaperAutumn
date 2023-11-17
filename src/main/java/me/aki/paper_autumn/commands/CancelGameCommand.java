package me.aki.paper_autumn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.aki.paper_autumn.casino.casino.CasinoPrefix;
import static me.aki.paper_autumn.casino.casino.endCasinoGame;

public class CancelGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        try {
            endCasinoGame(player);
            player.sendMessage(CasinoPrefix + "You've cancelled your current Casino Game");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return true;
    }
}
