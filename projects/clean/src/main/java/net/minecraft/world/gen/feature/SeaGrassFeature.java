package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockSeaGrassTall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class SeaGrassFeature extends Feature<SeaGrassConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, SeaGrassConfig config)
    {
        int i = 0;

        for (int j = 0; j < config.count; ++j)
        {
            int k = rand.nextInt(8) - rand.nextInt(8);
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + k, pos.getZ() + l);
            BlockPos blockpos = new BlockPos(pos.getX() + k, i1, pos.getZ() + l);

            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER)
            {
                boolean flag = rand.nextDouble() < config.field_203238_b;
                IBlockState iblockstate = flag ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();

                if (iblockstate.isValidPosition(worldIn, blockpos))
                {
                    if (flag)
                    {
                        IBlockState iblockstate1 = iblockstate.with(BlockSeaGrassTall.field_208065_c, DoubleBlockHalf.UPPER);
                        BlockPos blockpos1 = blockpos.up();

                        if (worldIn.getBlockState(blockpos1).getBlock() == Blocks.WATER)
                        {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            worldIn.setBlockState(blockpos1, iblockstate1, 2);
                        }
                    }
                    else
                    {
                        worldIn.setBlockState(blockpos, iblockstate, 2);
                    }

                    ++i;
                }
            }
        }

        return i > 0;
    }
}
