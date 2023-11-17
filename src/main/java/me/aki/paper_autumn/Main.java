package me.aki.paper_autumn;

import me.aki.paper_autumn.casino.cashierInventory;
import me.aki.paper_autumn.casino.games.blackjack;
import me.aki.paper_autumn.commands.*;
import me.aki.paper_autumn.enchantments.economy.EconomySystem;
import me.aki.paper_autumn.enchantments.economy.MoneyCommand;
import me.aki.paper_autumn.enchantments.economy.PayCommand;
import me.aki.paper_autumn.enchantments.DoubleJumpEnchantment;
import me.aki.paper_autumn.freezing.freezing;
import me.aki.paper_autumn.listeners.*;
import me.aki.paper_autumn.portals.portalConfig;
import me.aki.paper_autumn.portals.portals;
import me.aki.paper_autumn.portals.portalsCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static me.aki.paper_autumn.casino.casino.restoreCasinoStats;
import static me.aki.paper_autumn.freezing.freezing.attachRunnableToAllPlayers;
import static me.aki.paper_autumn.listeners.EntityDamageEvent.revertGhost;
import static me.aki.paper_autumn.utils.AsciiStuff.randomAsciiArtToConsole;
import static me.aki.paper_autumn.utils.CustomRecipes.addAllCustomRecipes;

public final class Main extends JavaPlugin {

    private static Main INSTANCE;
    public static Map<String, Long> playersCooldown = new HashMap<String, Long>();
    public static ArrayList<String> firstJoinAfterRestart = new ArrayList<String>();

    public static ArrayList<Enchantment> custom_enchants = new ArrayList<>();

    public static DoubleJumpEnchantment doubleJumpEnchantment;

    //on enable
    @Override
    public void onEnable() {
        doubleJumpEnchantment = new DoubleJumpEnchantment("double_jump");
        custom_enchants.add(doubleJumpEnchantment);
        registerEnchantment(doubleJumpEnchantment);
        this.getServer().getPluginManager().registerEvents(doubleJumpEnchantment,this);

        new portalConfig();
        new PlayerConfig();
        new Config();
        try {
            //restoreCasinoStats();
            restoreConfig();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        listenerRegistration();
        commandRegistration();
        addAllCustomRecipes();

        for(Player players : Bukkit.getOnlinePlayers()){
            if(!firstJoinAfterRestart.contains(players.getUniqueId().toString())){
                firstJoinAfterRestart.add(players.getUniqueId().toString());
                revertGhost(players.getUniqueId());
            }
        }
        randomAsciiArtToConsole();
        portals.enablePortals();
        //attachRunnableToAllPlayers();
    }

    public static Main getInstance() {
        return INSTANCE;
    }

    //on disable
    @Override
    public void onDisable() {
        //unregister custom enchants
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : custom_enchants){
                if(byKey.containsKey(enchantment.getKey())) {
                    byKey.remove(enchantment.getKey());
                }
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : custom_enchants){
                if(byName.containsKey(enchantment.getName())) {
                    byName.remove(enchantment.getName());
                }
            }
        } catch (Exception ignored) { }
    }

    //Listener Registration
    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(),this);
        pluginManager.registerEvents(new QuitListener(),this);
        pluginManager.registerEvents(new ChatListener(),this);
        pluginManager.registerEvents(new PlayerBedEnterListener(),this);
        pluginManager.registerEvents(new PlayerBedLeaveListener(),this);
        pluginManager.registerEvents(new ItemInteractListener(),this);
        pluginManager.registerEvents(new BlockPlaceEvent(),this);
        pluginManager.registerEvents(new BlockBreakListener(),this);
        pluginManager.registerEvents(new PlayerDeathListener(),this);
        pluginManager.registerEvents(new PlayerInteractAtEntityListener(),this);
        pluginManager.registerEvents(new PlayerMoveListener(),this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(),this);
        pluginManager.registerEvents(new EntityDamageEvent(),this);
        //pluginManager.registerEvents(new cashierInventory(),this);
        //pluginManager.registerEvents(new blackjack(),this);
        pluginManager.registerEvents(new ProjectileLaunchListener(),this);
        pluginManager.registerEvents(new BlockBurnListener(),this);
        pluginManager.registerEvents(new PlayerPreCommandListener(),this);
        pluginManager.registerEvents(new PlayerExpChangeListener(),this);
        pluginManager.registerEvents(new VehicleLeaveListener(),this);
        pluginManager.registerEvents(new VehicleEnterListener(),this);
        pluginManager.registerEvents(new PickupItemListener(),this);
        pluginManager.registerEvents(new EntityExplodeListener(),this);
        pluginManager.registerEvents(new VehicleMoveListener(),this);
        pluginManager.registerEvents(new EntityMoveListener(),this);
        pluginManager.registerEvents(new EntityDismountEvent(),this);
        pluginManager.registerEvents(new PlayerItemConsumeListener(),this);
        pluginManager.registerEvents(new portals(),this);
        //pluginManager.registerEvents(new freezing(),this);
    }

    //Command Registration
    private void commandRegistration() {
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("inviolability").setExecutor(new noDamageCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("walkspeed").setExecutor(new WalkSpeedCommand());
        getCommand("balance").setExecutor(new EconomySystem());
        getCommand("currency").setExecutor(new MoneyCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpTabCompleter());
        getCommand("hide").setExecutor(new HideCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("world").setTabCompleter(new WorldTabCompleter());
        getCommand("tab").setExecutor(new TabEditCommand());
        getCommand("tab").setTabCompleter(new TabEditTabCompleter());
        getCommand("system").setExecutor(new ConsoleCommand());
        getCommand("system").setTabCompleter(new ConsoleCommandTabCompleter());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("cancel").setExecutor(new CancelGameCommand());
        getCommand("e").setExecutor(new EmoteCommand());
        getCommand("e").setTabCompleter(new EmoteTabCompleter());
        getCommand("shrug").setExecutor(new ShrugCommand());
        getCommand("colour").setExecutor(new colorCommand());
        getCommand("colour").setTabCompleter(new colorCommandTabCompleter());
        getCommand("status").setExecutor(new StatusCommand());
        getCommand("ticket").setExecutor(new TicketCommand());
        getCommand("rc").setExecutor(new RemoteChatCommand());
        getCommand("portal").setExecutor(new portals());
        getCommand("portal").setTabCompleter(new portalsCommandTabCompleter());
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered){
            // It's been registered!
        }
    }


    public void restoreConfig() throws IOException {
        if(!Config.contains("oneheart"))
            Config.set("oneheart", false);
        if(!Config.contains("playerSpawnEffect"))
            Config.set("playerSpawnEffect", false);
        if(!Config.contains("headdrop"))
            Config.set("headdrop", false);
        if(!Config.contains("deathGhost"))
            Config.set("deathGhost", false);
        if(!Config.contains("deathban"))
            Config.set("deathban", false);
        if(!Config.contains("event.advancedBowDamage"))
            Config.set("event.advancedBowDamage", false);
        if(!Config.contains("worldedgetp.negZ"))
            Config.set("worldedgetp.negZ", 0);
        if(!Config.contains("worldedgetp.negX"))
            Config.set("worldedgetp.negX", 0);
        if(!Config.contains("worldedgetp.posZ"))
            Config.set("worldedgetp.posZ", 0);
        if(!Config.contains("worldedgetp.posX"))
            Config.set("worldedgetp.posX", 0);
        if(!Config.contains("antiwatermlg.enabled"))
            Config.set("antiwatermlg.enabled", false);
        if(!Config.contains("antiwatermlg.divideDamageBy"))
            Config.set("antiwatermlg.divideDamageBy", 5);
        if(!Config.contains("homeCooldown_onDamage"))
            Config.set("homeCooldown_onDamage", "60");
        if(!Config.contains("homeCooldown_onCommand"))
            Config.set("homeCooldown_onCommand", "300");
        if(!Config.contains("projectileKnockbackMultiplier"))
            Config.set("projectileKnockbackMultiplier", "0.1");
        if(!Config.contains("portalCooldown"))
            Config.set("portalCooldown", "20");
        if(!Config.contains("customEndportalLocation.enabled"))
            Config.set("customEndportalLocation.enabled", "false");
        if(!Config.contains("elytraFirstTimeSpawn"))
            Config.set("elytraFirstTimeSpawn", "false");
        if(!Config.contains("freezeincold"))
            Config.set("freezeincold", false);
        if(!portalConfig.contains("portalList")){
            portalConfig.set("portalList", new ArrayList<String>());
        }
    }
}