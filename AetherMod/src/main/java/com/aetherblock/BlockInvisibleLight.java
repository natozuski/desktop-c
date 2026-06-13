package com.aetherblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInvisibleLight extends Block {

    public BlockInvisibleLight() {
        super(Material.air);

        setLightLevel(1.0F);
        setLightOpacity(0);

        setHardness(0.0F);
        setResistance(6000000.0F);

        setUnlocalizedName("invisible_light");
        setRegistryName("aetherblock", "invisible_light");
    }
}