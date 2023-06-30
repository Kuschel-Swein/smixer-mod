package de.kuschel_swein.minecraft.smixer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = SmixerMod.MOD_ID,
        name = SmixerMod.MOD_NAME,
        version = SmixerMod.VERSION
)
public class SmixerMod {
        public static final String MOD_ID = "smixer";
    public static final String  MOD_NAME = "Smixer";
    public static final String VERSION = "1.0";


    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {

    }
}
