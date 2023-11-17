package me.aki.paper_autumn.portals;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class portalsCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("create");
            arguments.add("enable");
            arguments.add("disable");
            arguments.add("remove");
            arguments.add("addMember");
            arguments.add("removeMember");
            arguments.add("private");

            return arguments;
        }

        if (args.length == 2) {
            List<String> arguments = new ArrayList<>();
            arguments.add("<portal>");

            return arguments;
        }

        if (args.length == 3) {
            List<String> arguments = new ArrayList<>();
            if ("create".equalsIgnoreCase(args[0])) {
                arguments.add("true");
                arguments.add("false");
                arguments.add("<private>");

                return arguments;
            }

            if ("removeMember".equalsIgnoreCase(args[0]) || "addMember".equalsIgnoreCase(args[0])) {
                arguments.add("<member>");

                return arguments;
            }
        }

        if (args.length == 4) {
            List<String> arguments = new ArrayList<>();
            if ("create".equalsIgnoreCase(args[0])) {
                arguments.add("<owner>");

                return arguments;
            }
        }

        if (args.length == 5) {
            List<String> arguments = new ArrayList<>();
            if ("create".equalsIgnoreCase(args[0])) {
                arguments.add("<radius>");

                return arguments;
            }
        }

        if (args.length == 6) {
            List<String> arguments = new ArrayList<>();
            if ("create".equalsIgnoreCase(args[0])) {
                arguments.addAll(Collections.singleton(Arrays.toString(Particle.values())));

                return arguments;
            }
        }

        if (args.length == 7) {
            List<String> arguments = new ArrayList<>();
            if ("create".equalsIgnoreCase(args[0])) {
                arguments.addAll(Collections.singleton(Arrays.toString(Material.values())));

                return arguments;
            }
        }
        return null;
    }
}
