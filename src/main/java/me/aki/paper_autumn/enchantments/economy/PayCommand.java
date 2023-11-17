package me.aki.paper_autumn.enchantments.economy;

import me.aki.paper_autumn.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            String bar = "§cNo player specified";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        }

        if(args.length == 2) {

            Player target = sender.getServer().getPlayer(args[0]);

            if(!Bukkit.getOnlinePlayers().contains(args[0])) {
                String bar = "§cCouldn't find this player";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
                amount= 0;
            }

            if(EconomySystem.hasEnoughMoney(((Player) sender).getUniqueId().toString(), BigInteger.valueOf(amount))) {

                EconomySystem.addMoney(target.getUniqueId().toString(), BigInteger.valueOf(amount));
                EconomySystem.removeMoney(((Player) sender).getUniqueId().toString(), BigInteger.valueOf(amount));

                String bar = "You've payed §a" + target.getDisplayName() + " §6" + amount + " " + Config.get("currency");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

                String bar1 = "§a" + ((Player) sender).getDisplayName() + "§r payed you §6" + amount + " " + Config.get("currency");
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar1));
            } else {
                String bar = "§cYou dont have enough §6" + Config.get("currency") + "§c (Your balance: §6" + String.valueOf(EconomySystem.getMoney(((Player) sender).getUniqueId().toString())) + " " + Config.get("currency") + "§c)" ;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
            }
        } else {
            String bar = "§cSomething went wrong";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        }


    return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
