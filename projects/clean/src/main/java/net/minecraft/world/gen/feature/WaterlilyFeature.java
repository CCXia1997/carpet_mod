package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class WaterlilyFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        BlockPos blockpos1;

        for (BlockPos blockpos = pos; blockpos.getY() > 0; blockpos = blockpos1)
        {
            blockpos1 = blockpos.down();

            if (!worldIn.isAirBlock(blockpos1))
            {
                break;
            }
        }

        for (int i = 0; i < 10; ++i)
        {
            BlockPos blockpos2 = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            IBlockState iblockstate = Blocks.LILY_PAD.getDefaultState();

            if (worldIn.isAirBlock(blockpos2) && iblockstate.isValidPosition(worldIn, blockpos2))
            {
                worldIn.setBlockState(blockpos2, iblockstate, 2);
            }
        }

        return true;
    }
}
