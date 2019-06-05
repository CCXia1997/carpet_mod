package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BushFeature extends Feature<BushConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, BushConfig config)
    {
        int i = 0;
        IBlockState iblockstate = config.block.getDefaultState();

        for (int j = 0; j < 64; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.getDimension().isNether() || blockpos.getY() < 255) && iblockstate.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, iblockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
