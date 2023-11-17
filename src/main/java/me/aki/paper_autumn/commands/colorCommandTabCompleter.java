package me.aki.paper_autumn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class colorCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 1) {

            List<String> arguments = new ArrayList<>();
            if(args.length == 1) {
                arguments.add("r");
            }
            if(args.length == 2) {
                arguments.add("g");
            }
            if(args.length == 3) {
                arguments.add("b");
            }
            if(args.length > 4) {
                arguments.add("");
            }

            return arguments;
        }
        return null;
    }
}
