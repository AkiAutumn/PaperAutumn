package me.aki.paper_autumn.portals;

import me.aki.paper_autumn.Config;
import me.aki.paper_autumn.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.*;

import static me.aki.paper_autumn.utils.functions.*;

public class portals implements Listener, CommandExecutor {


    public static Map<String, Boolean> enabledPortals = new HashMap<String, Boolean>();
    public static ArrayList<String> preTeleportedPlayers = new ArrayList<String>();
    public static Map<String, Long> portalPlayerCooldown= new HashMap<String, Long>();
    public static Long portalCooldown = Long.valueOf((String) Config.get("portalCooldown"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        switch (args[0]){
            case "create":
                if(args.length >= 6 && sender.isOp()){

                    String name = args[1];
                    boolean access = Boolean.parseBoolean(args[2]);
                    String owner = null;
                    if(access) {
                         owner = sender.getServer().getPlayer(args[3]).getUniqueId().toString();
                    }
                    Location location = player.getLocation();
                    double radius = Double.parseDouble(args[4]);
                    String particle = args[5].toUpperCase();
                    String material = args[6].toUpperCase();

                    String[]croppedArgs = Arrays.copyOfRange(args, 7, args.length);
                    String lore = String.join(" ", croppedArgs);

                    try {
                        createPortal(name, location, radius, particle, material, lore, access, owner);
                        sendActionbarSuccess(player);
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                } else {
                    sendActionbarInvalidArguments(player);
                }

                break;
            case "enable":
                if(args.length == 2){
                    String name = args[1];
                    String owner = String.valueOf(portalConfig.get("portals." + name + ".owner"));
                    try {
                        if(Objects.equals(player.getUniqueId().toString(), owner) || !portalIsPrivate(name)){
                            portals.enablePortal(name);
                            sendActionbarSuccess(player);
                        } else {
                            sendActionbarNotOP(player);
                        }
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                } else {
                    sendActionbarInvalidArguments(player);
                }
                break;
            case "disable":
                if(args.length == 2){
                String name = args[1];
                String owner = String.valueOf(portalConfig.get("portals." + name + ".owner"));
                    try {
                        if(Objects.equals(player.getUniqueId().toString(), owner) || !portalIsPrivate(name)){
                            portals.disablePortal(name);
                            sendActionbarSuccess(player);
                        } else {
                            sendActionbarNotOP(player);
                        }
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                } else {
                sendActionbarInvalidArguments(player);
            }
                break;
            case "remove":
                if(args.length == 2 & sender.isOp()){
                    String name = args[1];
                    try {
                        portals.removePortal(name);
                        sendActionbarSuccess(player);
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                } else {
                    sendActionbarInvalidArguments(player);
                }
                break;
            case "addMember":
                String name = args[1];
                Player member = sender.getServer().getPlayer(args[2]);
                String owner = String.valueOf(portalConfig.get("portals." + name + ".owner"));
                if(Objects.equals(player.getUniqueId().toString(), owner)) {
                    try {
                        assert member != null;
                        addPortalMember(name, member);
                        sendActionbarSuccess(player);
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "removeMember":
                String name1 = args[1];
                Player member1 = sender.getServer().getPlayer(args[2]);
                String owner1 = String.valueOf(portalConfig.get("portals." + name1 + ".owner"));
                if(Objects.equals(player.getUniqueId().toString(), owner1)) {
                    try {
                        assert member1 != null;
                        removePortalMember(name1, member1);
                        sendActionbarSuccess(player);
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "private":
                String name2 = args[1];
                boolean access = Boolean.parseBoolean(args[2]);
                String owner2 = String.valueOf(portalConfig.get("portals." + name2 + ".owner"));
                if(Objects.equals(player.getUniqueId().toString(), owner2)) {
                    try {
                        portalConfig.set("portals." + name2 + ".private", access);
                        sendActionbarSuccess(player);
                    } catch (IOException e) {
                        sendActionbarError(player);
                        throw new RuntimeException(e);
                    }
                    break;
                }
        }
        return false;
    }

    private static final Inventory inv = Bukkit.createInventory(null, 9, "Select Destination");

    public static void createPortal(String name, Location location, double radius, String particle, String material, String lore, boolean access, String owner) throws IOException {
        portalConfig.set("portals." + name + ".location", location);
        portalConfig.set("portals." + name + ".radius", radius);
        portalConfig.set("portals." + name + ".particle", particle);
        portalConfig.set("portals." + name + ".material", material);
        portalConfig.set("portals." + name + ".lore", lore);
        portalConfig.set("portals." + name + ".private", access);
        portalConfig.set("portals." + name + ".owner", owner);

        ArrayList<String> portals = (ArrayList<String>) portalConfig.get("portalList");
        portals.add(name);
        portalConfig.set("portalList", portals);
    }

    public static boolean playerIsPortalMember(String name, Player player) throws IOException {
        Object object = portalConfig.get("portals." + name + ".members." + player.getUniqueId().toString());
        if(object == null){
            return false;
        } else return object.toString().equalsIgnoreCase("true");
    }

    public static boolean portalIsPrivate(String name) throws IOException {
        return (boolean) portalConfig.get("portals." + name + ".private");
    }

    public static void addPortalMember(String name, Player player) throws IOException {
        portalConfig.set("portals." + name + ".members." + player.getUniqueId().toString(), true);
    }

    public static void removePortalMember(String name, Player player) throws IOException {
        portalConfig.set("portals." + name + ".members." + player.getUniqueId().toString(), null);
    }

    public static void removePortal(String name) throws IOException {
        disablePortal(name);
        portalConfig.set("portals." + name, null);

        ArrayList<String> portals = (ArrayList<String>) portalConfig.get("portalList");
        portals.remove(name);
        portalConfig.set("portalList", portals);
    }

    public static void disablePortal(String name) {
        enabledPortals.put(name, true);
    }

    public static void preTeleportPlayer(Player player) throws IOException {
        if(!preTeleportedPlayers.contains(player.getUniqueId().toString())){
            preTeleportedPlayers.add(player.getUniqueId().toString());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0, false, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 0, false, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 0, false, true, false));
            player.openInventory(inv);
            initializeItems(player);
        }
    }

    public static void teleportPlayerToPortal(Player player, String portalName){
        Particle particle = Particle.valueOf(String.valueOf(portalConfig.get("portals." + portalName + ".particle")));

        player.closeInventory();
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 2);
        player.getWorld().spawnParticle(particle, player.getLocation(), 100, 1, 0, 1, 0.1, null);
        Location newLocation = (Location) portalConfig.get("portals." + portalName + ".location");
        Vector vector = player.getLocation().getDirection().multiply(-1);
        player.teleport(newLocation);
        player.getWorld().spawnParticle(particle, player.getLocation(), 100, 1, 0, 1, 0.1, null);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 2);
        vector.setY(0.75);
        player.setVelocity(vector);
        portalPlayerCooldown.put(player.getUniqueId().toString(), System.currentTimeMillis() + (portalCooldown * 1000));
    }

    public static void enablePortals(){
        ArrayList<String> portals = (ArrayList<String>) portalConfig.get("portalList");
        for(String portalName : portals){
            enablePortal(portalName);
        }
    }

    public static void enablePortal(String name){
        enabledPortals.put(name, false);
        Bukkit.getLogger().info("Portal " + name + " enabled");

        Long timeInTicks = 2L;
        Location location = (Location) portalConfig.get("portals." + name + ".location");
        double radius = (Double) (portalConfig.get("portals." + name + ".radius"));
        Particle particle = Particle.valueOf(String.valueOf(portalConfig.get("portals." + name + ".particle")));

        new BukkitRunnable() {
            @Override
            public void run() {

                if (enabledPortals.get(name)) {
                    enabledPortals.remove(name);
                    Bukkit.getLogger().info("Portal " + name + " disabled");
                    this.cancel();
                }

                location.getWorld().spawnParticle(particle, location, (int) Math.round(radius * 50), (radius / 2), 0, (radius / 2), 0.01, null);
                for(Entity entity : location.getNearbyEntities(radius, 0.25, radius)){
                    if(entity instanceof Player) {
                        try {
                            if(playerIsPortalMember(name, (Player) entity) || !portalIsPrivate(name)){
                                if(portalPlayerCooldown.containsKey(entity.getUniqueId().toString())){
                                    if (portalPlayerCooldown.get(entity.getUniqueId().toString()) > System.currentTimeMillis()) {
                                        //still on cooldown
                                        long time = (portalPlayerCooldown.get(entity.getUniqueId().toString()) - System.currentTimeMillis()) / 1000;
                                        sendActionbar((Player) entity, ChatColor.RED + "" + ChatColor.BOLD + "× " + ChatColor.RESET + time + "s" + ChatColor.RED + "" + ChatColor.BOLD + " ×");
                                    } else {
                                        preTeleportPlayer((Player) entity);
                                    }
                                } else {
                                    preTeleportPlayer((Player) entity);
                                }
                            } else {
                                sendActionbar((Player) entity, ChatColor.RED + "" + ChatColor.BOLD + "× " + ChatColor.RESET + "no access" + ChatColor.RED + "" + ChatColor.BOLD + " ×");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), timeInTicks, timeInTicks);
    }

    public static void initializeItems(Player player) throws IOException {
        inv.clear();
        for(String name : enabledPortals.keySet()){
            if(playerIsPortalMember(name, player) || !portalIsPrivate(name)){
                Material material = Material.valueOf(String.valueOf(portalConfig.get("portals." + name + ".material")));
                String lore = String.valueOf(portalConfig.get("portals." + name + ".lore"));
                inv.addItem(createGuiItem(material, name, lore));
            }
        }
    }

    protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(List.of(ChatColor.WHITE + "" + Arrays.toString(lore)));

        item.setItemMeta(meta);

        return item;
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Player player = (Player) e.getWhoClicked();
        String portalName = clickedItem.getItemMeta().getDisplayName();

        teleportPlayerToPortal(player, portalName);
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryLeave(final InventoryCloseEvent e) {
        if (e.getInventory().equals(inv)) {
            HumanEntity humanEntity = e.getPlayer();
            preTeleportedPlayers.remove(humanEntity.getUniqueId().toString());
            portalPlayerCooldown.put(humanEntity.getUniqueId().toString(), System.currentTimeMillis() + (2000));

            humanEntity.removePotionEffect(PotionEffectType.BLINDNESS);
            humanEntity.removePotionEffect(PotionEffectType.INVISIBILITY);
            humanEntity.removePotionEffect(PotionEffectType.SLOW);

            Vector vector = humanEntity.getLocation().getDirection().multiply(-1);
            vector.setY(0.75);
            humanEntity.setVelocity(vector);
        }
    }
}
