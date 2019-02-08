package deriktj.lightning_forge.common.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LootLoader {

    public LootLoader() {
        LootTableList.register(new ResourceLocation(ModLightningForge.MODID,"artifact_pieces_loot"));
    }

    @SubscribeEvent
    public static void loadLoot(LootTableLoadEvent event) {
        switch(event.getName().toString()) {
            case "minecraft:chests/stronghold_corridor":
            case "minecraft:chests/simple_dungeon":
            case "minecraft:chests/nether_bridge":
            case "minecraft:gameplay/fishing/treasure":
            case "minecraft:chests/igloo_chest":
            case "minecraft:chests/abandoned_mineshaft":
            case "minecraft:chests/stronghold_crossing":
            case "minecraft:chests/jungle_temple":
            case "minecraft:chests/desert_pyramid":
            case "minecraft:chests/stronghold_library":
            case "minecraft:chests/village_blacksmith":
            case "minecraft:chests/woodland_mansion":
            case "minecraft:chests/end_city_treasure":
                LootEntry entry = new LootEntryTable(new ResourceLocation(ModLightningForge.MODID, "artifact_pieces_loot"), 1, 0,new LootCondition[0], "lightning_forge_loot_entry");
                LootPool pool = new LootPool(new LootEntry[] {entry},new LootCondition[0], new RandomValueRange(1),new RandomValueRange(0, 1), "lightning_forge_loot_pool");
                event.getTable().addPool(pool);
                break;
            default:

        }
    }
}
