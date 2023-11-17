package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;

import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarNotOP;

public class TabEditCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!sender.isOp()){

            sendActionbarNotOP(player);

        }else{

            if(args.length == 0){
                sendActionbar(player, (ChatColor.RED + "Use /tab header | footer <text>"));
            }

            if(args.length >= 1){

                switch (args[0]) {
                    case "header":

                        String header = Arrays.toString(args);
                        try {
                            Config.set("tablist.header", header.substring(9, header.length()-1).replaceAll(",", ""));
                            player.sendMessage("set header to: " + header.substring(9, header.length()-1).replaceAll(",", ""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "footer":

                        String footer = Arrays.toString(args);
                        try {
                            Config.set("tablist.footer", footer.substring(9, footer.length()-1).replaceAll(",", ""));
                            player.sendMessage("set footer to: " + footer.substring(9, footer.length()-1).replaceAll(",", ""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;

                    default: sendActionbar(player, (ChatColor.RED + "Use /tab header | footer <text>"));
                }
            }
        }
        return false;
    }
}