package com.aetherblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAether extends Block {

    private static final int RADIUS = 8;

    public BlockAether() {
        super(Material.rock);

        setHardness(2.0F);
        setResistance(10.0F);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            placeLight(world, pos);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            clearLight(world, pos);
        }
        super.breakBlock(world, pos, state);
    }

    private void placeLight(World world, BlockPos center) {

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {

                    if (dx*dx + dy*dy + dz*dz > RADIUS*RADIUS)
                        continue;

                    BlockPos p = center.add(dx, dy, dz);

                    if (world.isAirBlock(p)) {
                        world.setBlockState(p, ModBlocks.INVISIBLE_LIGHT.getDefaultState(), 2);
                    }
                }
            }
        }
    }

    private void clearLight(World world, BlockPos center) {

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {

                    BlockPos p = center.add(dx, dy, dz);

                    if (world.getBlockState(p).getBlock() == ModBlocks.INVISIBLE_LIGHT) {
                        world.setBlockToAir(p);
                    }
                }
            }
        }
    }
}