package me.aki.paper_autumn.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarNotOP;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;


        if(!sender.isOp()){

            sendActionbarNotOP(player);

        }else{

            if(args.length == 0){
                player.setHealth(20);
                player.setFoodLevel(20);

                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 2);
                sendActionbar(player, (ChatColor.GREEN + "You've healed yourself"));
            }
            if(args.length == 1){

                Player target = sender.getServer().getPlayer(args[0]);

                if(target == null) {

                    return true;
                }

                if(args.length == 1) {
                    target.setHealth(20);
                    target.setFoodLevel(20);
                    target.playSound((target).getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 2);
                    sendActionbar(player, (ChatColor.GREEN + "You've healed " + target.getName()));
                    sendActionbar(target, (ChatColor.GREEN + "You've been healed by " + player.getName()));
                }
            }
        }
        return false;
    }
}