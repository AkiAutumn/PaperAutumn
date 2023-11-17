package me.aki.paper_autumn.enchantments.economy;

import me.aki.paper_autumn.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;

import static me.aki.paper_autumn.economyGame.functions.displayMoney;
import static me.aki.paper_autumn.utils.functions.sendActionbar;

public class EconomySystem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        String money = (String) Config.get("currency");

        if(args.length == 0) {
            assert money != null;
            if(money.equalsIgnoreCase("bits")){
                displayMoney(player);
            } else {
                sendActionbar(player, "Your " + money + ": §6" + getMoney(player.getUniqueId().toString()));
            }

        } else if(args.length == 1) {
            Player target = sender.getServer().getPlayer(args[0]);
            sendActionbar(player, "§2" + target.getName() + "§a's balance is: §2" + getMoney(target.getUniqueId().toString()));
        }
        if(args.length == 3 && sender.isOp()) {
            Player target = player.getServer().getPlayer(args[1]);
            assert target != null;
            String targetUUID = target.getUniqueId().toString();
            if(args[0].equalsIgnoreCase("add")) {

            BigInteger amount = new BigInteger(args[2]);
            addMoney(targetUUID, amount);
                sendActionbar(player,"§aAdded §2" + amount + " " + money + "§a to §2" + (target.getName() + "§a's balance"));

            } else if(args[0].equalsIgnoreCase("remove")) {

                BigInteger amount = new BigInteger(args[2]);
                removeMoney(targetUUID, amount);
                sendActionbar(player,"§aTook §2" + amount + " " + money + "§a from §2" + (target.getName() + "§a's balance"));

            } else if(args[0].equalsIgnoreCase("set")) {

                BigInteger amount = new BigInteger(args[2]);
                setMoney(targetUUID, amount);
                sendActionbar(player,"§a" + target.getName() + "§a's balance was set to: §2" + amount + " " + money);

            } else {
                sendActionbar(player,"§cInvalid arguments");
            }
        }
        return false;
    }

    public static BigInteger getMoney(String uuid){
        File file = new File("plugins/AUtilsPlugin", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return new BigInteger(String.valueOf(config.get(uuid + ".money")));
    }

    public static void addMoney(String uuid, BigInteger amount){
        File file = new File("plugins/AUtilsPlugin", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        BigInteger money = getMoney(uuid);
        money = money.add(amount);
        config.set(uuid + ".money", money);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeMoney(String uuid, BigInteger amount){
        File file = new File("plugins/AUtilsPlugin", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        BigInteger money = getMoney(uuid);
        money = money.subtract(amount);
        config.set(uuid + ".money", money);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMoney (String uuid, BigInteger amount){
        File file = new File("plugins/AUtilsPlugin", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(uuid + ".money", amount);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasEnoughMoney(String uuid, BigInteger amount){
        File file = new File("plugins/AUtilsPlugin", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        BigInteger money = getMoney(uuid);
        if(money.compareTo(amount) >= 0) {
            return true;
        } else {
            return false;
        }
    }

}
