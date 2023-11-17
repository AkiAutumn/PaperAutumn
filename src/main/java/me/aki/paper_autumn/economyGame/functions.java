package me.aki.paper_autumn.economyGame;

import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.awt.*;
import java.math.BigInteger;

import static me.aki.paper_autumn.enchantments.economy.EconomySystem.getMoney;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class functions {

    public static void obtainMoney(Player player, int amount) {

        net.md_5.bungee.api.ChatColor color = net.md_5.bungee.api.ChatColor.of(new Color(191, 173, 201));

        EconomySystem.addMoney(player.getUniqueId().toString(), BigInteger.valueOf(amount));
        sendActionbar(player, color + "+ " + amount);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 2);
    }

    public static void displayMoney(Player player){
        int maxChars = 10;
        BigInteger amount = getMoney(player.getUniqueId().toString());
        String bits = shortenString(String.valueOf(getMoney(player.getUniqueId().toString())), maxChars);
        String b = shortenString(String.valueOf(getBytes(amount)), maxChars);
        String kb = shortenString(String.valueOf(getKiloBytes(amount)), maxChars);
        String mb = shortenString(String.valueOf(getMegaBytes(amount)), maxChars);
        String gb = shortenString(String.valueOf(getGigaBytes(amount)), maxChars);
        String tb = shortenString(String.valueOf(getTerraBytes(amount)), maxChars);
        String pb = shortenString(String.valueOf(getPetaBytes(amount)), maxChars);
        String eb = shortenString(String.valueOf(getExaBytes(amount)), maxChars);
        String zb = shortenString(String.valueOf(getZettaBytes(amount)), maxChars);
        String yb = shortenString(String.valueOf(getYottaBytes(amount)), maxChars);

        String s = "bits :: " + bits + "\n"
                + "bytes :: " + b + "\n"
                + "kb :: " + kb + "\n"
                + "mb :: " + mb + "\n"
                + "gb :: " + gb + "\n"
                + "tb :: " + tb + "\n"
                + "pb :: " + pb + "\n"
                + "eb :: " + eb + "\n"
                + "zb :: " + zb + "\n"
                + "yb :: " + yb + "\n";

        player.sendMessage(s);
    }

    public static String shortenString(String s, int maxCharacters) {
        return s.substring(0, Math.min(s.length(), maxCharacters));
    }

    public static String getBytes(BigInteger bits) {
        return formattedBits(bits)[0];
    }

    public static String getKiloBytes(BigInteger bits) {
        return formattedBits(bits)[1];
    }

    public static String getMegaBytes(BigInteger bits) {
        return formattedBits(bits)[2];
    }

    public static String getGigaBytes(BigInteger bits) {
        return formattedBits(bits)[3];
    }

    public static String getTerraBytes(BigInteger bits) {
        return formattedBits(bits)[4];
    }

    public static String getPetaBytes(BigInteger bits) {
        return formattedBits(bits)[5];
    }

    public static String getExaBytes(BigInteger bits) {
        return formattedBits(bits)[6];
    }

    public static String getZettaBytes(BigInteger bits) {
        return formattedBits(bits)[7];
    }

    public static String getYottaBytes(BigInteger bits) {
        return formattedBits(bits)[8];
    }

    public static String[] formattedBits(BigInteger bits) {
        int bytes = 8;
        int kiloByte = 1024;
        int megaByte = 1048576;
        int gigaByte = 1073741824;
        BigInteger terraByte = new BigInteger("1099511627776");
        BigInteger petaByte =  new BigInteger("1125899906842624");
        BigInteger exaByte =  new BigInteger("1152921504606846976");
        BigInteger zettaByte =  new BigInteger("1180591620717411303424");
        BigInteger yottaByte =  new BigInteger("1208925819614629174706176");

        BigInteger toByte = bits.divide(BigInteger.valueOf(bytes));
        BigInteger toKilo = bits.divide(BigInteger.valueOf(kiloByte));
        BigInteger toMega = bits.divide(BigInteger.valueOf(megaByte));
        BigInteger toGiga = bits.divide(BigInteger.valueOf(gigaByte));
        BigInteger toTerra = bits.divide(terraByte);
        BigInteger toPeta = bits.divide(petaByte);
        BigInteger toExa = bits.divide(exaByte);
        BigInteger toZetta = bits.divide(zettaByte);
        BigInteger toYotta = bits.divide(yottaByte);

        return new String[]{String.valueOf(toByte), String.valueOf(toKilo), String.valueOf(toMega), String.valueOf(toGiga), String.valueOf(toTerra), String.valueOf(toPeta), String.valueOf(toExa), String.valueOf(toZetta), String.valueOf(toYotta)};
    }
}
