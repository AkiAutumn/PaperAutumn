package me.aki.paper_autumn.listeners;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import me.aki.paper_autumn.PlayerConfig;
import me.aki.paper_autumn.utils.SavedItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static me.aki.paper_autumn.Main.firstJoinAfterRestart;
import static me.aki.paper_autumn.casino.casino.restoreCasinoGameStats;
import static me.aki.paper_autumn.commands.HideCommand.hidden;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.ghosts;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.revertGhost;
import static me.aki.paper_autumn.utils.functions.getCustomChatColor;
import static me.aki.paper_autumn.utils.particleEffects.startPlayerSpawnEffect;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {

        Player player = event.getPlayer();
        Object oneHeartJoin = Config.get("oneheart");
        Object playerSpawnEffect = Config.get("playerSpawnEffect");

        PlayerConfig.set(player.getUniqueId() + ".safetyQuestion", false);

        Object firstJoin = PlayerConfig.get(player.getUniqueId() + ".firstJoin");
        if(firstJoin == null) {
            PlayerConfig.set(player.getUniqueId() + ".firstJoin", true);
            if (Config.get("elytraFirstTimeSpawn").toString().equalsIgnoreCase("true")) {
                player.getInventory().setChestplate(SavedItems.oneTimeElytra());
            }
        }

        if(!firstJoinAfterRestart.contains(player.getUniqueId().toString())){
            firstJoinAfterRestart.add(player.getUniqueId().toString());
            revertGhost(player.getUniqueId());
        }

        // Hide hidden players
        for(Player player1 : hidden){
            player.hidePlayer(player1);
        }

        if(hidden.contains(player) || ghosts.containsKey(player.getUniqueId().toString())){
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
            }
            hidden.add(player);
        }

        if(playerSpawnEffect.equals("true")){
            startPlayerSpawnEffect(player);
        }

        if(oneHeartJoin.equals("true")){
            player.setMaxHealth(2);
        } else {
            player.setMaxHealth(20);
        }

        //restoreCasinoGameStats(player);

        net.md_5.bungee.api.ChatColor color = getCustomChatColor(player);
        player.setPlayerListName(getCustomChatColor(player) + player.getName());

        // send welcome message
        ArrayList<String> DiscordMessages = new ArrayList<String>() {
            {
                add(color + player.getName() + ChatColor.GRAY + " just joined the server - glhf!");
                add(color + player.getName() + ChatColor.GRAY + " just joined. Everyone, look busy!");
                add(color + player.getName() + ChatColor.GRAY + " just joined. Can I get a heal?");
                add(color + player.getName() + ChatColor.GRAY + " joined your party.");
                add(color + player.getName() + ChatColor.GRAY + " joined. You must construct additional pylons.");
                add(ChatColor.GRAY + "Ermagherd. " + color + player.getName() + ChatColor.GRAY + " is here.");
                add(ChatColor.GRAY + "Welcome, " + color + player.getName() + ChatColor.GRAY + " Stay awhile and listen.");
                add(ChatColor.GRAY + "Welcome, " + color + player.getName() + ChatColor.GRAY + " We were expecting you ( ͡° ͜ʖ ͡°)");
                add(ChatColor.GRAY + "Welcome, " + color + player.getName() + ChatColor.GRAY + " We hope you brought pizza.");
                add(ChatColor.GRAY + "Welcome, " + color + player.getName() + ChatColor.GRAY + " Leave your weapons by the door.");
                add(ChatColor.GRAY + "A wild " + color + player.getName() + ChatColor.GRAY + " appeared.");
                add(ChatColor.GRAY + "Swoooosh. " + color + player.getName() + ChatColor.GRAY + " just landed.");
                add(color + player.getName() + ChatColor.GRAY + " just joined. Hide your bananas.");
                add(color + player.getName() + ChatColor.GRAY + " just arrived. Seems OP - please nerf.");
                add(color + player.getName() + ChatColor.GRAY + " just slid into the server.");
                add(ChatColor.GRAY + "A " + color + player.getName() + ChatColor.GRAY + " has spawned in the server.");
                add(ChatColor.GRAY + "Big " + color + player.getName() + ChatColor.GRAY + " showed up!");
                add(ChatColor.GRAY + "Where’s " + color + player.getName() + ChatColor.GRAY + "? In the server!");
                add(color + player.getName() + ChatColor.GRAY + " hopped into the server. Kangaroo!!");
                add(color + player.getName() + ChatColor.GRAY + " just showed up. Hold my beer.");
                add(ChatColor.GRAY + "Challenger approaching - " + color + player.getName() + ChatColor.GRAY + " has appeared!");
                add(ChatColor.GRAY + "It's a bird! It's a plane! Nevermind, it's just " + color + player.getName());
                add(ChatColor.GRAY + "It's " + color + player.getName() + ChatColor.GRAY + "! Praise the sun!");
                add(ChatColor.GRAY + "Never gonna give " + color + player.getName() + ChatColor.GRAY + " up. Never gonna let " + color + player.getName() + ChatColor.GRAY + " down.");
                add(ChatColor.GRAY + "Ha! " + color + player.getName() + ChatColor.GRAY + " has joined! You activated my trap card!");
                add(ChatColor.GRAY + "Cheers, love! " + color + player.getName() + ChatColor.GRAY + "'s here!");
                add(ChatColor.GRAY + "Hey! Listen! " + color + player.getName() + ChatColor.GRAY + " has joined!");
                add(ChatColor.GRAY + "We've been expecting you " + color + player.getName());
                add(ChatColor.GRAY + "It's dangerous to go alone, take " + color + player.getName());
                add(color + player.getName() + ChatColor.GRAY + " has joined the server! It's super effective!");
                add(ChatColor.GRAY + "Cheers, love! " + color + player.getName() + ChatColor.GRAY + " is here!");
                add(color + player.getName() + ChatColor.GRAY + " is here, as the prophecy foretold.");
                add(color + player.getName() + ChatColor.GRAY + " has arrived. Party's over.");
                add(ChatColor.GRAY + "Ready player " + color + player.getName());
                add(color + player.getName() + ChatColor.GRAY + ChatColor.GRAY + " is here to kick butt and chew bubblegum. And " + color + player.getName() + ChatColor.GRAY + " is all out of gum.");
                add(ChatColor.GRAY + "Hello. Is it " + color + player.getName() + ChatColor.GRAY + " you're looking for?");
                add(color + player.getName() + ChatColor.GRAY + " has joined. Stay a while and listen!");
                add(ChatColor.GRAY + "Roses are red, violets are blue, " + color + player.getName() + ChatColor.GRAY + " joined this server with you");
            }
        };

        Random random = new Random();
        for (int i = 0; i < DiscordMessages.size(); i++)
            event.setJoinMessage("> " + DiscordMessages.get(random.nextInt(DiscordMessages.size())));
        
    }
}
