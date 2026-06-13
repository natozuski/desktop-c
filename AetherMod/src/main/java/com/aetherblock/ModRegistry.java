package com.aetherblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRegistry {

    public static void init() {

        ModBlocks.AETHER_BLOCK = new BlockAether()
                .setUnlocalizedName("aether_block")
                .setRegistryName("aetherblock", "aether_block");

        ModBlocks.INVISIBLE_LIGHT = new BlockInvisibleLight()
                .setUnlocalizedName("invisible_light")
                .setRegistryName("aetherblock", "invisible_light");

        GameRegistry.register(ModBlocks.AETHER_BLOCK);
        GameRegistry.register(ModBlocks.INVISIBLE_LIGHT);

        GameRegistry.register(new ItemBlock(ModBlocks.AETHER_BLOCK)
                .setRegistryName(ModBlocks.AETHER_BLOCK.getRegistryName()));
    }
}