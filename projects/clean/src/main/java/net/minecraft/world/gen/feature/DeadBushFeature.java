package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class DeadBushFeature extends Feature<NoFeatureConfig>
{
    private static final BlockDeadBush field_197166_a = (BlockDeadBush)Blocks.DEAD_BUSH;

    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(pos); (iblockstate.isAir() || iblockstate.isIn(BlockTags.LEAVES)) && pos.getY() > 0; iblockstate = worldIn.getBlockState(pos))
        {
            pos = pos.down();
        }

        IBlockState iblockstate1 = field_197166_a.getDefaultState();

        for (int i = 0; i < 4; ++i)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && iblockstate1.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, iblockstate1, 2);
            }
        }

        return true;
    }
}
