package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockKelpTop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class KelpFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        int i = 0;
        int j = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
        BlockPos blockpos = new BlockPos(pos.getX(), j, pos.getZ());

        if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER)
        {
            IBlockState iblockstate = Blocks.KELP.getDefaultState();
            IBlockState iblockstate1 = Blocks.KELP_PLANT.getDefaultState();
            int k = 1 + rand.nextInt(10);

            for (int l = 0; l <= k; ++l)
            {
                if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.WATER && iblockstate1.isValidPosition(worldIn, blockpos))
                {
                    if (l == k)
                    {
                        worldIn.setBlockState(blockpos, iblockstate.with(BlockKelpTop.AGE, Integer.valueOf(rand.nextInt(23))), 2);
                        ++i;
                    }
                    else
                    {
                        worldIn.setBlockState(blockpos, iblockstate1, 2);
                    }
                }
                else if (l > 0)
                {
                    BlockPos blockpos1 = blockpos.down();

                    if (iblockstate.isValidPosition(worldIn, blockpos1) && worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.KELP)
                    {
                        worldIn.setBlockState(blockpos1, iblockstate.with(BlockKelpTop.AGE, Integer.valueOf(rand.nextInt(23))), 2);
                        ++i;
                    }

                    break;
                }

                blockpos = blockpos.up();
            }
        }

        return i > 0;
    }
}
