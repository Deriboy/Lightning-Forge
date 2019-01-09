package deriktj.lightning_forge.common;

import deriktj.lightning_forge.common.core.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModLightningForge.MODID, name = ModLightningForge.NAME, version = ModLightningForge.VERSION)
public class ModLightningForge
{
    public static final String MODID = "lightning_forge";
    public static final String NAME = "Lightning Forge";
    public static final String VERSION = "0.1";
    public static final String CONFIGNAME = "lightning_forge";

    @SidedProxy(clientSide = "deriktj.lightning_forge.client.core.ClientProxy", serverSide = "deriktj.lightning_forge.common.core.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ModLightningForge instance;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
