package me.aki.paper_autumn.enchantments.economy;

import me.aki.paper_autumn.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        String money = (String) Config.get("currency");

        if(args.length == 0) {
            String bar = "Currency: " + ChatColor.GOLD + money;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        }
        if(args.length == 1 && sender.isOp()) {
            try {
                Config.set("currency", args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String bar = "§aCurrency is now §2" + args[0];
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));

        } else {
            String bar = "§cSomething went wrong";
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar));
        }

        return false;
    }
}