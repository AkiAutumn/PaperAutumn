package me.aki.paper_autumn.commands;

import me.aki.paper_autumn.portals.portalConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static me.aki.paper_autumn.portals.portals.enabledPortals;

public class ConsoleCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {

        if (args.length == 1) {

            List<String> arguments = new ArrayList<>();
            arguments.add("world");
            arguments.add("bedskip");
            arguments.add("blood");
            arguments.add("itemframe");
            arguments.add("headdrop");
            arguments.add("oneheart");
            arguments.add("addMap");
            arguments.add("unbreakable");
            arguments.add("advancedBowDamage");
            arguments.add("setCoinFlipCoinLocation");
            arguments.add("give");
            arguments.add("earnEconomy");
            arguments.add("playerSpawnEffect");
            arguments.add("worldedgetp");
            arguments.add("deathGhost");
            arguments.add("antiwatermlg");
            arguments.add("becomeGhost");
            arguments.add("reviveGhost");
            arguments.add("customEndportalLocation");
            arguments.add("freezeInCold");

            return arguments;
        }

        if(args.length == 2){

            List<String> arguments = new ArrayList<>();

            switch (args[0]){
                case "world":
                    arguments.add("send");
                    break;
                case "unbreakable":
                    arguments.add("hideFlags");
                    break;
                case "liegewie":
                case "deathban":
                case "blood":
                case "headdrop":
                case "oneheart":
                case "advancedBowDamage":
                case "playerSpawnEffect":
                case "earnEconomy":
                case "deathGhost":
                case "antiwatermlg":
                case "freezeInCold":
                    arguments.add("true");
                    arguments.add("false");
                    break;
                case "itemframe":
                    arguments.add("");
                case "addMap":
                    arguments.add("<name>");
                case "give":
                    arguments.add("infinityblock");
                    arguments.add("tpbow");
                    arguments.add("infinitycart");
                    arguments.add("3x3pickaxe");
                    arguments.add("doubleJumpBoots");
                    arguments.add("specialFeather");
                    arguments.add("oneTimeElytra");
                    break;
                case "worldEdgeTP":
                    arguments.add("true");
                    arguments.add("false");
                    arguments.add("negZ");
                    break;
                case "customEndportalLocation":
                    arguments.add("true");
                    arguments.add("false");
                    arguments.add("here");
                    break;
            }
            return arguments;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("world")) {

            List<String> worldNames = new ArrayList<>();
            World[] worlds = new World[Bukkit.getWorlds().size()];
            Bukkit.getWorlds().toArray(worlds);

            for (int i = 0; i < worlds.length; i++) {
                worldNames.add(worlds[i].getName());
            }

            return worldNames;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("worldedgetp")) {

            List<String> arguments = new ArrayList<>();
            arguments.add("posX");

            return arguments;
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("worldedgetp")) {

            List<String> arguments = new ArrayList<>();
            arguments.add("posZ");

            return arguments;
        }

        if (args.length == 5 && args[0].equalsIgnoreCase("worldedgetp")) {

            List<String> arguments = new ArrayList<>();
            arguments.add("negX");

            return arguments;
        }

        if (args.length == 5 && args[0].equalsIgnoreCase("world")) {

            List<String> arguments = new ArrayList<>();

            if(args[0].equalsIgnoreCase("world")) {
                arguments.add("x");
            }
            return arguments;
        }

        if (args.length == 6 && args[0].equalsIgnoreCase("world")) {
            List<String> arguments = new ArrayList<>();

            if(args[0].equalsIgnoreCase("world")) {
                arguments.add("y");
            }
            return arguments;
        }

        if (args.length == 7 && args[0].equalsIgnoreCase("world")) {

            List<String> arguments = new ArrayList<>();
            arguments.add("z");

            return arguments;
        }

        return null;
    }
}
