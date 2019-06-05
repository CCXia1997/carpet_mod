package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class JungleGrassFeature extends Feature<NoFeatureConfig>
{
    public IBlockState func_202357_a(Random p_202357_1_)
    {
        return p_202357_1_.nextInt(4) == 0 ? Blocks.FERN.getDefaultState() : Blocks.GRASS.getDefaultState();
    }

    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        IBlockState iblockstate = this.func_202357_a(rand);

        for (IBlockState iblockstate1 = worldIn.getBlockState(pos); (iblockstate1.isAir() || iblockstate1.isIn(BlockTags.LEAVES)) && pos.getY() > 0; iblockstate1 = worldIn.getBlockState(pos))
        {
            pos = pos.down();
        }

        int i = 0;

        for (int j = 0; j < 128; ++j)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && iblockstate.isValidPosition(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, iblockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
