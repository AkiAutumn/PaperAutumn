package me.aki.paper_autumn.utils;

import me.aki.paper_autumn.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomRecipes {

    public static void addAllCustomRecipes(){
        editChains();
        addAcaciaSapling();
        addDarkOakSapling();
        addDeadCoral();
        addHeartOfSea();
        addPrismarineCrystal();
        addPrismarineShards();
        addTropicalFishBucket();
        addCalcite();
        addCopper();
        addSaddle();
        addHorseArmorDiamond();
        addHorseArmorIron();
        addHorseArmorGold();
        addHorseArmorLeather();
        addSponge();
        addSandstone();
        addDeepslate();
        addSwiftcart();
    }

    public static void editChains() {

        ItemStack item = new ItemStack(Material.CHAIN, 8);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "CHAIN");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" N ", " I ", " N ");

        recipe.setIngredient('N', Material.IRON_NUGGET);
        recipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static void addHeartOfSea() {

        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "HEART_OF_THE_SEA");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("WNW", "NAN", "WNW");

        recipe.setIngredient('W', Material.WATER_BUCKET);
        recipe.setIngredient('A', Material.PUFFERFISH_BUCKET);
        recipe.setIngredient('N', Material.NAUTILUS_SHELL);

        Bukkit.addRecipe(recipe);
    }

    public static void addAcaciaSapling() {

        ItemStack item = new ItemStack(Material.ACACIA_SAPLING);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "ACACIA_SAPLING");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" O ", "OSO", " O ");

        recipe.setIngredient('S', Material.BIRCH_SAPLING);
        recipe.setIngredient('O', Material.ORANGE_DYE);

        Bukkit.addRecipe(recipe);
    }

    public static void addDarkOakSapling() {

        ItemStack item = new ItemStack(Material.DARK_OAK_SAPLING);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "DARK_OAK_SAPLING");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" B ", "BSB", " B ");

        recipe.setIngredient('S', Material.OAK_SAPLING);
        recipe.setIngredient('B', Material.BLACK_DYE);

        Bukkit.addRecipe(recipe);
    }

    public static void addDeadCoral() {

        ItemStack item = new ItemStack(Material.DEAD_BRAIN_CORAL_BLOCK, 2);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "DEAD_BRAIN_CORAL_BLOCK");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" B ", "BCB", " B ");

        recipe.setIngredient('C', Material.COBBLESTONE);
        recipe.setIngredient('B', Material.BONE_MEAL);

        Bukkit.addRecipe(recipe);
    }

    public static void addTropicalFishBucket() {

        ItemStack item = new ItemStack(Material.TROPICAL_FISH_BUCKET);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "TROPICAL_FISH_BUCKET");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", " BT", "   ");

        recipe.setIngredient('T', Material.TROPICAL_FISH);
        recipe.setIngredient('B', Material.WATER_BUCKET);

        Bukkit.addRecipe(recipe);
    }

    public static void addPrismarineShards() {

        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD, 9);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "PRISMARINE_SHARD");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", " P ", "   ");

        recipe.setIngredient('P', Material.PACKED_ICE);

        Bukkit.addRecipe(recipe);
    }

    public static void addPrismarineCrystal() {

        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS, 9);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "PRISMARINE_CRYSTALS");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", " B ", "   ");

        recipe.setIngredient('B', Material.BLUE_ICE);

        Bukkit.addRecipe(recipe);
    }

    public static void addCalcite() {

        ItemStack item = new ItemStack(Material.CALCITE, 4);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "CALCITE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", " CD", " DC");

        recipe.setIngredient('C', Material.COBBLESTONE);
        recipe.setIngredient('D', Material.DIORITE);

        Bukkit.addRecipe(recipe);
    }

    public static void addCopper() {

        ItemStack item = new ItemStack(Material.RAW_COPPER, 4);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "COPPER_ORE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", " IG", " GI");

        recipe.setIngredient('I', Material.RAW_IRON);
        recipe.setIngredient('G', Material.GRANITE);

        Bukkit.addRecipe(recipe);
    }

    public static void addSaddle() {

        ItemStack item = new ItemStack(Material.SADDLE);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "SADDLE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "LLL", "HSH");

        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        recipe.setIngredient('S', Material.STRING);

        Bukkit.addRecipe(recipe);
    }

    public static void addHorseArmorDiamond() {

        ItemStack item = new ItemStack(Material.DIAMOND_HORSE_ARMOR);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "DIAMOND_HORSE_ARMOR");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "DBD", "DHD");

        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        recipe.setIngredient('D', Material.DIAMOND);

        Bukkit.addRecipe(recipe);
    }

    public static void addHorseArmorIron() {

        ItemStack item = new ItemStack(Material.IRON_HORSE_ARMOR);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "IRON_HORSE_ARMOR");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "DBD", "DHD");

        recipe.setIngredient('B', Material.IRON_BLOCK);
        recipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        recipe.setIngredient('D', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static void addHorseArmorGold() {

        ItemStack item = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "GOLDEN_HORSE_ARMOR");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "DBD", "DHD");

        recipe.setIngredient('B', Material.GOLD_BLOCK);
        recipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        recipe.setIngredient('D', Material.GOLD_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static void addHorseArmorLeather() {

        ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "LEATHER_HORSE_ARMOR");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "DBD", "BHB");

        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('H', Material.TRIPWIRE_HOOK);

        Bukkit.addRecipe(recipe);
    }

    public static void addSponge() {

        ItemStack item = new ItemStack(Material.SPONGE, 9);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "DRY_SPONGE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("WSW", "SWS", "WSW");

        recipe.setIngredient('W', Material.YELLOW_WOOL);
        recipe.setIngredient('S', Material.STRING);

        Bukkit.addRecipe(recipe);
    }

    public static void addSandstone() {

        ItemStack item = new ItemStack(Material.SANDSTONE, 18);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "SANDSTONE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("SSS", "SSS", "SSS");

        recipe.setIngredient('S', Material.SAND);

        Bukkit.addRecipe(recipe);
    }

    public static void addDeepslate() {

        ItemStack item = new ItemStack(Material.DEEPSLATE, 9);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "DEEPSLATE");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("SSS", "SSS", "SSS");

        recipe.setIngredient('S', Material.COBBLESTONE);

        Bukkit.addRecipe(recipe);
    }

    public static void addSwiftcart() {

        ItemStack item = new ItemStack(Material.MINECART);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Swifty Minecart");
        item.setItemMeta(itemMeta);
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "SWIFTCART");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "SBS", "SSS");

        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('S', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
    }
}
