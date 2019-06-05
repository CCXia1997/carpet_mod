package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockSeaPickle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.placement.CountConfig;

public class SeaPickleFeature extends Feature<CountConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator<?> generator, Random rand, BlockPos pos, CountConfig config)
    {
        int i = 0;

        for (int j = 0; j < config.count; ++j)
        {
            int k = rand.nextInt(8) - rand.nextInt(8);
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + k, pos.getZ() + l);
            BlockPos blockpos = new BlockPos(pos.getX() + k, i1, pos.getZ() + l);
            IBlockState iblockstate = Blocks.SEA_PICKLE.getDefaultState().with(BlockSeaPickle.PICKLES, Integer.valueOf(rand.nextInt(4) + 1));

            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && iblockstate.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, iblockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
