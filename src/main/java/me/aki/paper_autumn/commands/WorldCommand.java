package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static me.aki.paper_autumn.utils.functions.*;


public class WorldCommand implements CommandExecutor {

    public static void createWorld(String worldName, World.Environment worldEnvironment, WorldType worldType, Player player){

        WorldCreator wc = new WorldCreator(worldName);

        wc.environment(worldEnvironment);
        wc.type(worldType);

        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))) {
            for (Player everyone : Bukkit.getOnlinePlayers()) {
                everyone.sendMessage(ChatColor.YELLOW + "Info " + ChatColor.GOLD + "| " + ChatColor.RESET + ChatColor.BOLD + player.getName() + ChatColor.RESET + " is creating a new world - " + ChatColor.RED + "might result in performance impact");
            }
        }

        sendActionbar(player, ChatColor.GRAY + "Your world is being created, please be patient");
        wc.createWorld();

        sendActionbar(player, ChatColor.GREEN + "World " + ChatColor.DARK_GREEN + worldName + ChatColor.GREEN + " was created!");
        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))) {
            for (Player everyone : Bukkit.getOnlinePlayers()) {
                everyone.sendMessage(ChatColor.YELLOW + "Info " + ChatColor.GOLD + "| " + ChatColor.RESET + "World creation completed.");
            }
        }

    }

    public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!sender.isOp()){

            sendActionbarNotOP(player);

        }else{

                if(args.length != 0 && args.length <= 3) {

                    switch (args[0]){
                        case "create":

                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                            Path worldPath = Paths.get(args[1]);
                            if (Files.exists(worldPath)) {
                                sendActionbar(player, ChatColor.RED + "A Directory with that name already exists - use " + ChatColor.GRAY + "/load" + ChatColor.RED + " to load a already existing world");
                            } else {

                                if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {

                                    if (args.length == 3) {

                                        switch (args[2].toLowerCase()) {

                                            case "[worldtype=normal,environment=normal]":
                                                createWorld(args[1], World.Environment.NORMAL, WorldType.NORMAL, player);
                                                break;
                                            case "[worldtype=normal,environment=nether]":
                                                createWorld(args[1], World.Environment.NETHER, WorldType.NORMAL, player);
                                                break;
                                            case "[worldtype=normal,environment=the_end]":
                                                createWorld(args[1], World.Environment.THE_END, WorldType.NORMAL, player);
                                                break;

                                            case "[worldtype=amplified,environment=normal]":
                                                createWorld(args[1], World.Environment.NORMAL, WorldType.AMPLIFIED, player);
                                                break;
                                            case "[worldtype=amplified,environment=nether]":
                                                createWorld(args[1], World.Environment.NETHER, WorldType.AMPLIFIED, player);
                                                break;
                                            case "[worldtype=amplified,environment=the_end]":
                                                createWorld(args[1], World.Environment.THE_END, WorldType.AMPLIFIED, player);
                                                break;

                                            case "[worldtype=large_biomes,environment=normal]":
                                                createWorld(args[1], World.Environment.NORMAL, WorldType.LARGE_BIOMES, player);
                                                break;
                                            case "[worldtype=large_biomes,environment=nether]":
                                                createWorld(args[1], World.Environment.NETHER, WorldType.LARGE_BIOMES, player);
                                                break;
                                            case "[worldtype=large_biomes,environment=the_end]":
                                                createWorld(args[1], World.Environment.THE_END, WorldType.LARGE_BIOMES, player);
                                                break;

                                            case "[worldtype=flat,environment=normal]":
                                                createWorld(args[1], World.Environment.NORMAL, WorldType.FLAT, player);
                                                break;
                                            case "[worldtype=flat,environment=nether]":
                                                createWorld(args[1], World.Environment.NETHER, WorldType.FLAT, player);
                                                break;
                                            case "[worldtype=flat,environment=the_end]":
                                                createWorld(args[1], World.Environment.THE_END, WorldType.FLAT, player);
                                                break;

                                            default:
                                                sendActionbar(player, ChatColor.RED + "Error");
                                        }
                                    } else {
                                        WorldCreator wc = new WorldCreator(args[1]);

                                        wc.environment(World.Environment.NORMAL);
                                        wc.type(WorldType.NORMAL);

                                        if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                                            for (Player everyone : Bukkit.getOnlinePlayers()) {
                                                everyone.sendMessage(ChatColor.YELLOW + "Info " + ChatColor.GOLD + "| " + ChatColor.RESET + ChatColor.BOLD + player.getName() + ChatColor.RESET + " is creating a new world - " + ChatColor.RED + "might result in performance impact");
                                            }
                                        }

                                        sendActionbar(player, ChatColor.GRAY + "Your world is being created, please be patient");

                                        wc.createWorld();

                                        sendActionbar(player, ChatColor.GREEN + "World " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " was created!");
                                        for (Player everyone : Bukkit.getOnlinePlayers()) {
                                            everyone.sendMessage(ChatColor.YELLOW + "Info" + ChatColor.GOLD + " | " + ChatColor.RESET + "World creation completed.");
                                        }

                                    }
                                }
                            }
                            }
                            break;

                        case "load":

                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                Path worldPath1 = Paths.get(args[1]);
                                if (Files.exists(worldPath1)) {

                                    sendActionbar(player, ChatColor.GRAY + "Loading world " + ChatColor.GOLD + args[1] + "...");
                                    for (Player everyone : Bukkit.getOnlinePlayers()) {
                                        everyone.sendMessage(ChatColor.YELLOW + "Info " + ChatColor.GOLD + "| " + ChatColor.RESET + ChatColor.BOLD + player.getName() + ChatColor.RESET + " is loading a world - " + ChatColor.RED + "might result in performance impact");
                                    }

                                    new WorldCreator(args[1]).environment(World.Environment.NORMAL).createWorld();

                                    sendActionbar(player, ChatColor.GRAY + "Successfully loaded world " + ChatColor.GOLD + args[1]);
                                    for (Player everyone : Bukkit.getOnlinePlayers()) {
                                        everyone.sendMessage(ChatColor.YELLOW + "Info" + ChatColor.GOLD + " | " + ChatColor.RESET + "World creation completed.");
                                    }

                                } else {
                                    sendActionbar(player, ChatColor.RED + "Couldn't find this world - did u spelled it right?");
                                }
                            }
                            break;

                        case "copy":

                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                if (Bukkit.getWorld(args[1]) != null) {

                                    Path sourceFilePath = Paths.get(args[1]);

                                    String source = sourceFilePath.toString();
                                    File srcDir = new File(source);

                                    String destination = source + "_copy";
                                    File destDir = new File(destination);

                                    try {
                                        copyDirectory(srcDir, destDir);
                                        sendActionbar(player, ChatColor.GRAY + "Successfully copied world " + ChatColor.GOLD + args[1]);
                                        System.out.println("INFO | " + player.getName() + " copied world " + args[1]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    sendActionbar(player, ChatColor.RED + "Couldn't find this world - did u spelled it right?");
                                }
                            }

                            break;

                        case "join":

                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                                    sendActionbar(player, ChatColor.RED + "Couldn't find this world - is it loaded?");
                                } else {

                                    //For target player
                                    if (args.length == 3) {
                                        Player target = sender.getServer().getPlayer(args[2]);

                                        if (target == null) {

                                            sendActionbarPlayerNotFound(player);

                                        } else {

                                            if (!Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                                                sendActionbar(player, ChatColor.RED + "Couldn't find this world - is it loaded?");
                                            } else {

                                                World world = Bukkit.getWorld(args[1]);

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
                                                sendActionbar(target, "You've been teleported to world " + ChatColor.GOLD + world.getName() + ChatColor.RESET + " by " + ChatColor.BOLD + player.getName());
                                                sendActionbar(player, "You've sent " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " to world " + ChatColor.GOLD + world.getName());
                                                //teleport to config saved location
                                                if (PlayerConfig.contains(target.getUniqueId() + ".worlds." + world + ".lastLocation")) {

                                                    Location lastLocation = new Location(world, 0, 0, 0);
                                                    Object object = PlayerConfig.get(target.getUniqueId() + ".worlds." + world + ".lastLocation");
                                                    String text = valueOf(object);

                                                    String[] locationXYZ = text.split(";");

                                                    double x = Double.parseDouble(locationXYZ[0]);
                                                    double y = Double.parseDouble(locationXYZ[1]);
                                                    double z = Double.parseDouble(locationXYZ[2]);

                                                    lastLocation.setX(x);
                                                    lastLocation.setY(y);
                                                    lastLocation.setZ(z);

                                                    target.teleport(lastLocation);
                                                }
                                            }
                                        }
                                    } else {

                                        //For player (self)

                                        World world = Bukkit.getWorld(args[1]);

                                        //save last coordinates
                                        try {
                                            PlayerConfig.set(player.getUniqueId() + ".worlds." + player.getWorld() + ".lastLocation", (player.getLocation().getX() + ";" + player.getLocation().getY() + ";" + player.getLocation().getZ()).toString());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        //teleport into world
                                        Location location = (world.getSpawnLocation());
                                        player.teleport(location);

                                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 3));
                                        sendActionbar(player, "You've teleported to world " + ChatColor.GOLD + world.getName());

                                        //teleport to config saved location
                                        if (PlayerConfig.contains(player.getUniqueId() + ".worlds." + world + ".lastLocation")) {

                                            Location lastLocation = new Location(world, 0, 0, 0);
                                            Object object = PlayerConfig.get(player.getUniqueId() + ".worlds." + world + ".lastLocation");
                                            String text = valueOf(object);

                                            String[] locationXYZ = text.split(";");

                                            double x = Double.parseDouble(locationXYZ[0]);
                                            double y = Double.parseDouble(locationXYZ[1]);
                                            double z = Double.parseDouble(locationXYZ[2]);

                                            lastLocation.setX(x);
                                            lastLocation.setY(y);
                                            lastLocation.setZ(z);

                                            player.teleport(lastLocation);
                                        }
                                    }
                                }
                            }

                            return true;

                        case "list":

                            String worlds = Bukkit.getWorlds().toString();
                            worlds = worlds.substring(1);
                            worlds = worlds.substring(0, worlds.length() - 1);
                            worlds = worlds.replace("CraftWorld{", "");
                            worlds = worlds.replace("name=", "");
                            worlds = worlds.replace("}", "");
                            worlds = worlds.replace(", ", "\n");

                            Path currentRelativePath = Paths.get("");
                            String s = currentRelativePath.toString();
                            System.out.println("Current relative path is: " + s);

                            File[] directories = new File(s).listFiles(File::isDirectory);
                            String otherDirectories = Arrays.toString(directories);

                            try {
                                Set<String> fileList = listFilesUsingDirectoryStream(s);

                                String string = fileList.toString();
                                string = string.substring(1);
                                string = string.substring(0, string.length() - 1);
                                string = string.replace("cache, ", "");
                                string = string.replace("crash-reports, ", "");
                                string = string.replace("plugins, ", "");
                                string = string.replace("logs, ", "");
                                string = string.replace(", ", "\n");

                                player.sendMessage(ChatColor.GRAY + "--- Loaded Worlds ---" + "\n" +
                                        ChatColor.AQUA + worlds + "\n" +
                                        ChatColor.GRAY + "Other Directories found:" + "\n" +
                                        ChatColor.AQUA + string + "\n" +
                                        ChatColor.GRAY + "--------------------");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;

                        case "rename":

                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                if (args.length == 3) {
                                    if (Bukkit.getWorld(args[1]) != null) {
                                        if (args[1].equals("world")) {
                                            sendActionbar(player, ChatColor.RED + "Can't rename the default world");
                                        } else {

                                            Path sourceFilePath = Paths.get(args[1]);
                                            Path targetFilePath = Paths.get(args[2]);

                                            World world = Bukkit.getWorld(args[1]);
                                            List<? extends Player> playerList = Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld().equals(world)).collect(Collectors.toList());

                                            for (Player target : playerList) {
                                                target.kickPlayer("*ugh*");
                                            }

                                            Bukkit.unloadWorld(args[1], true);

                                            try {
                                                Files.move(sourceFilePath, targetFilePath);
                                                System.out.println("INFO | " + player.getName() + " renamed directory " + args[1] + " to " + args[2]);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
                                        sendActionbar(player, ChatColor.RED + "Couldn't find this world - did u spelled it right?");
                                    }
                                }
                            }
                            break;

                        case "current":
                            sendActionbar(player, "Current world: " + ChatColor.GOLD + player.getWorld().getName());
                            break;

                        case "unload":
                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                if (Bukkit.getWorld(args[1]) != null) {
                                    if (args[1].equalsIgnoreCase("world")) {
                                        sendActionbar(player, ChatColor.RED + "Can't unload the default world");
                                    } else {
                                        sendActionbar(player, ChatColor.GRAY + "Unloaded world " + ChatColor.RED + args[1]);
                                        Bukkit.unloadWorld(args[1], true);
                                    }
                                } else {
                                    sendActionbar(player, ChatColor.RED + "Couldn't find this world - did u spelled it right?");
                                }
                            }
                            break;

                        case "delete":
                            if(args[1] == null){
                                sendActionbarInvalidArguments(player);
                            } else {

                                if (Bukkit.getWorld(args[1]) != null) {

                                    if (PlayerConfig.get(player.getUniqueId() + ".safetyQuestion").equals(false)) {

                                        try {
                                            sendActionbar(player, ChatColor.RED + "Repeat the command if you're sure to delete world " + ChatColor.DARK_RED + args[1]);
                                            PlayerConfig.set(player.getUniqueId() + ".safetyQuestion", true);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        if (PlayerConfig.get(player.getUniqueId() + ".safetyQuestion").equals(true)) {

                                            try {
                                                PlayerConfig.set(player.getUniqueId() + ".safetyQuestion", false);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            World delete = Bukkit.getWorld(args[1]);

                                            if (delete.getPlayers().isEmpty()) {
                                                Bukkit.unloadWorld(delete, false);

                                                File deleteFolder = delete.getWorldFolder();
                                                deleteWorld(deleteFolder);
                                                sendActionbar(player, ChatColor.RED + "You've deleted world " + ChatColor.DARK_RED + args[1]);
                                                System.out.println("INFO | " + player.getName() + " deleted world " + args[1]);
                                            } else {
                                                sendActionbar(player, ChatColor.RED + "Can't delete world when players are in it");
                                            }
                                        }
                                    }

                                } else {
                                    sendActionbar(player, ChatColor.RED + "Couldn't find this world - is it loaded?");
                                }
                            }
                            break;

                        default: sendActionbar(player, ChatColor.RED + "Invalid arguments -> \"" + ChatColor.DARK_RED + args[0] + ChatColor.RED + "\"");

                    }
                } else {
                    sendActionbarInvalidArguments(player);
                }
            }
        return false;
    }
}