package me.aki.paper_autumn.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.aki.paper_autumn.utils.functions.*;


public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player) || !(sender.isOp())) {
            sendActionbarNotOP(player);
        } else {

            if (args.length == 0 && !player.getAllowFlight()) {
                player.setAllowFlight(true);
                player.setFlying(true);
                sendActionbar(player, ("You can fly now"));
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_PARROT_FLY, 2, 1);

            } else {

                if (args.length == 0) {
                    player.setFlySpeed(0.1f);
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    sendActionbar(player, "You can't fly anymore");

                } else {

                    Player target = sender.getServer().getPlayer(args[0]);

                    if (target == null) {
                        sendActionbarPlayerNotFound(player);

                    } else {

                        if(args.length == 1 && !target.getAllowFlight()) {
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            sendActionbar(player, "You gave " + target.getName() + " the ability to fly");
                            sendActionbar(target, "You can fly now");
                            ((Player) target).playSound(((Player) target).getLocation(), Sound.ENTITY_PARROT_FLY, 2, 1);

                        }else{

                            target.setFlySpeed(0.1f);
                            target.setFlying(false);
                            target.setAllowFlight(false);
                            sendActionbar(target, "You can't fly anymore");
                            sendActionbar(player, "You took " + target.getName() + " the ability to fly");
                            ((Player) target).playSound(((Player) target).getLocation(), Sound.ENTITY_PARROT_FLY, 2, 0);
                        }
                    }
                }
            }
        }
        return true;
    }
}