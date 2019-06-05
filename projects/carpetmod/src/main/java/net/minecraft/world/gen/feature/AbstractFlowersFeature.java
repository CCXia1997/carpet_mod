package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class AbstractFlowersFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        IBlockState iblockstate = this.getRandomFlower(rand, pos);
        int i = 0;

        for (int j = 0; j < 64; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && iblockstate.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, iblockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }

    public abstract IBlockState getRandomFlower(Random p_202355_1_, BlockPos p_202355_2_);
}
