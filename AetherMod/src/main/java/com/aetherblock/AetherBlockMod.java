package com.aetherblock;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = AetherBlockMod.MODID,
    name = "AetherBlock",
    version = "1.0.1"
)
public class AetherBlockMod {

    public static final String MODID = "aetherblock";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
    }
}