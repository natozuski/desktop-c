package com.aetherblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

@Mod(
    modid = AetherBlockMod.MODID,
    name = "AetherBlock",
    version = "1.0.2",
    useMetadata = true
)
public class AetherBlockMod {

    public static final String MODID = "aetherblock";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
    }
}

class ModBlocks {

    public static Block AETHER_BLOCK;
    public static Block INVISIBLE_LIGHT;
}

class ModRegistry {

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

class BlockAether extends Block {

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

class BlockInvisibleLight extends Block {
    
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    public BlockInvisibleLight() {
        super(Material.air);
        
        setLightLevel(1.0F);
        setLightOpacity(0);
        
        setHardness(0.0F);
        setResistance(0.0F);
    }
    
    // Make the block have no collision (can walk through)
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return EMPTY_AABB;
    }
    
    // Make the block have no selection box (no outline when looking at it)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return EMPTY_AABB;
    }
    
    // Prevent interaction
    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }
    
    // Optional: prevents the block from blocking entity movement
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, 
                                      AxisAlignedBB entityBox, java.util.List<AxisAlignedBB> collidingBoxes, 
                                      net.minecraft.entity.Entity entityIn) {
        // Do nothing - no collision boxes to add
    }
}