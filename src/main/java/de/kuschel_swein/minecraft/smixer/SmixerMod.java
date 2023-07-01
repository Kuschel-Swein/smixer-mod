package de.kuschel_swein.minecraft.smixer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = SmixerMod.MOD_ID,
        name = SmixerMod.MOD_NAME,
        version = SmixerMod.VERSION
)
public class SmixerMod {

    public static final String MOD_ID = "smixer";

    public static final String MOD_NAME = "Smixer";

    public static final String VERSION = "$VERSION";

    private static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        LOGGER.info("Successfully enabled " + MOD_NAME + "!");
    }
}
