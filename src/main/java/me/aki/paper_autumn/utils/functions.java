package me.aki.paper_autumn.utils;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.PlayerConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Field;

import static me.aki.paper_autumn.Main.playersCooldown;

public class functions {

    public static net.md_5.bungee.api.ChatColor getCustomChatColor(Player player){
        if(PlayerConfig.contains(player.getUniqueId().toString() + ".chatColor")){
            int r = (int) PlayerConfig.get(player.getUniqueId().toString() + ".chatColor.r");
            int g = (int) PlayerConfig.get(player.getUniqueId().toString() + ".chatColor.g");
            int b = (int) PlayerConfig.get(player.getUniqueId().toString() + ".chatColor.b");
            net.md_5.bungee.api.ChatColor chatColor = net.md_5.bungee.api.ChatColor.of(new Color(r, g, b));
            return chatColor;
        } else {
            return net.md_5.bungee.api.ChatColor.WHITE;
        }
    }

    public static void addCooldown(Player player, long cooldown) {
    if(playersCooldown.containsKey(player.getUniqueId().toString())) {
        long time = (playersCooldown.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
        playersCooldown.put(player.getUniqueId().toString(), System.currentTimeMillis() + (cooldown + time) * 1000);
    } else {
        playersCooldown.put(player.getUniqueId().toString(), System.currentTimeMillis() + cooldown * 1000);
    }
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyFile(File sourceFile, File destinationFile)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

    public static final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = (Block) iter.next();
        while (iter.hasNext()) {
            lastBlock = (Block) iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    public static void sendActionbar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
    }

    public static void sendActionbarSuccess(Player player){
        sendActionbar(player, (ChatColor.GREEN + "" + ChatColor.BOLD + "✓"));
        ((Player) player).playSound(((Player) player).getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 2, 2);
    }

    public static void sendActionbarError(Player player){
        sendActionbar(player, (ChatColor.RED + "" + ChatColor.BOLD + "×"));
        ((Player) player).playSound(((Player) player).getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 0);
    }

    public static void sendActionbarNotOP(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "oh oh, you can't do this"));
    }

    public static void sendActionbarPlayerNotFound(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Couldn't find this player, maybe a typo?"));
    }

    public static void sendActionbarInvalidArguments(Player player) {
        sendActionbar(player, ChatColor.RED + "Invalid Arguments");
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "N";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "NE";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "E";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "SE";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "S";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D)) {
            return "SW";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D)) {
            return "W";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D)) {
            return "NW";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "N";
        }
        return null;
    }

    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public static Field getField(Class<?> clazz, String name) {

        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
    }
}


