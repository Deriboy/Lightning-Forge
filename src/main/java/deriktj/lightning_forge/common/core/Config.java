package deriktj.lightning_forge.common.core;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_ITEMS = "items";

    // general config

    // items config
    public static int prospectorsPickaxeCooldown = 100;
    public static int prospectorsPickaxeRange = 2;
    public static float prospectorsPickaxeChance = 0.25f;
    public static String[] prospectorsPickaxeOres = new String[] {
            "minecraft:coal_ore",
            "minecraft:lapis_ore",
            "minecraft:iron_ore",
            "minecraft:gold_ore",
            "minecraft:redstone_ore",
            "minecraft:diamond_ore",
            "minecraft:emerald_ore",
            "minecraft:quartz_ore",
    };


    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initItemsConfig(cfg);
        } catch (Exception e1) {
            ModLightningForge.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
    }

    private static void initItemsConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "Items configuration");
        prospectorsPickaxeCooldown = cfg.getInt("prospectorsPickaxeCooldown", CATEGORY_ITEMS, prospectorsPickaxeCooldown, 0,Integer.MAX_VALUE,"The cooldown, in ticks, of the prospector's pickaxe's special ability");
        prospectorsPickaxeRange = cfg.getInt("prospectorsPickaxeRange", CATEGORY_ITEMS, prospectorsPickaxeRange, 1,5,"The radius of the range of the prospector's pickaxe");
        prospectorsPickaxeChance = cfg.getFloat("prospectorsPickaxeChance",CATEGORY_ITEMS, prospectorsPickaxeChance,0.0f,1.0f,"The chance that the prospector's pickaxe's special ability activates when mining a block");
        prospectorsPickaxeOres = cfg.getStringList("prospectorsPickaxeOres", CATEGORY_ITEMS, prospectorsPickaxeOres, "List of valid ores for the prospector's pickaxe to prospect");
    }

}
