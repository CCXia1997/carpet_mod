package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class TallGrassFeature extends Feature<TallGrassConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, TallGrassConfig config)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(pos); (iblockstate.isAir() || iblockstate.isIn(BlockTags.LEAVES)) && pos.getY() > 0; iblockstate = worldIn.getBlockState(pos))
        {
            pos = pos.down();
        }

        int i = 0;

        for (int j = 0; j < 128; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && config.state.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, config.state, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
