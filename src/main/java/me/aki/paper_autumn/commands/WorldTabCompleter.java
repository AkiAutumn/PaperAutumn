package me.aki.paper_autumn.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WorldTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 1) {

            List<String> arguments = new ArrayList<>();
            arguments.add("create");
            arguments.add("load");
            arguments.add("current");
            arguments.add("join");
            arguments.add("unload");
            arguments.add("delete");
            arguments.add("list");
            arguments.add("rename");
            arguments.add("copy");

            return arguments;
        }

        if (args.length == 2) {

            List<String> worldNames = new ArrayList<>();
            World[] worlds = new World[Bukkit.getWorlds().size()];
            Bukkit.getWorlds().toArray(worlds);

            for (int i = 0; i < worlds.length; i++) {
                worldNames.add(worlds[i].getName());
            }

            return worldNames;
        }

        if(args[0].equalsIgnoreCase("create")) {
            if (args.length == 3) {

                List<String> arguments = new ArrayList<>();

                arguments.add("[worldType=normal,environment=normal]");
                arguments.add("[worldType=normal,environment=nether]");
                arguments.add("[worldType=normal,environment=the_end]");

                arguments.add("[worldType=amplified,environment=normal]");
                arguments.add("[worldType=amplified,environment=nether]");
                arguments.add("[worldType=amplified,environment=the_end]");

                arguments.add("[worldType=flat,environment=normal]");
                arguments.add("[worldType=flat,environment=nether]");
                arguments.add("[worldType=flat,environment=the_end]");

                arguments.add("[worldType=largeBiomes,environment=normal]");
                arguments.add("[worldType=largeBiomes,environment=nether]");
                arguments.add("[worldType=largeBiomes,environment=the_end]");

                return arguments;
            }
        }

        if (args.length > 3) {

            List<String> arguments = new ArrayList<>();
            arguments.add(" ");

            return arguments;
        }


        return null;
    }
}
