package me.aki.paper_autumn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabEditTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 1) {

            List<String> arguments = new ArrayList<>();
            arguments.add("header");
            arguments.add("footer");

            return arguments;
        }

        if (args.length == 2) {

            List<String> arguments = new ArrayList<>();
            arguments.add("Type your own text here");

            return arguments;
        }

        return null;
    }
}
