package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static me.aki.paper_autumn.utils.Targeter.getTargetPlayer;
import static me.aki.paper_autumn.utils.functions.sendActionbar;
import static me.aki.paper_autumn.utils.functions.sendActionbarInvalidArguments;


public class EmoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Player target = getTargetPlayer(player);

        ChatColor textColor = ChatColor.WHITE;
        ChatColor nameColor = ChatColor.LIGHT_PURPLE;
        String targetError = ChatColor.RED + "Didn't find a player - you need to aim at them";

        if(args.length == 1){
            switch (args[0].toLowerCase()){
                case "pat":
                    if(target != null) {
                        target.getWorld().spawnParticle(Particle.HEART, target.getLocation().add(0, 2.25, 0), 1);

                        sendActionbar(player, textColor + "You patted " + nameColor + target.getName());
                        sendActionbar(target, nameColor + player.getName() + textColor + " patted you");

                        if(PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedPats") != null) {
                            int oldStat = (int) PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedPats");
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedPats", oldStat + 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedPats", 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    } else {
                        sendActionbar(player, targetError);
                    }
                    break;

                case "kiss":
                    if(target != null) {
                        target.getWorld().spawnParticle(Particle.HEART, target.getLocation().add(0, 2, 0), 3, 0.25, 0.1, 0.25, 0.5);

                        sendActionbar(player, textColor + "You kissed " + nameColor + target.getName());
                        sendActionbar(target, nameColor + player.getName() + textColor + " kissed you");

                        if(PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedKiss") != null) {
                            int oldStat = (int) PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedKiss");
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedKiss", oldStat + 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedKiss", 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    } else {
                        sendActionbar(player, targetError);
                    }
                    break;

                case "hug":
                    if(target != null) {
                        target.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, target.getEyeLocation(), 5, 0.5, 0.3, 0.5, 0.25);

                        sendActionbar(player, textColor+ "You hugged " + nameColor + target.getName());
                        sendActionbar(target, nameColor + player.getName() + textColor + " hugged you");

                        if(PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedHug") != null) {
                            int oldStat = (int) PlayerConfig.get(target.getUniqueId() + ".stats.emotes.receivedHug");
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedHug", oldStat + 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                PlayerConfig.set(target.getUniqueId() + ".stats.emotes.receivedHug", 1);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    } else {
                        sendActionbar(player, targetError);
                    }
                    break;
                default: sendActionbarInvalidArguments(player);
            }
        } else {
            sendActionbarInvalidArguments(player);
        }
        return false;
    }
}
