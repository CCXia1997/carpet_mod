package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class SphereReplaceFeature extends Feature<SphereReplaceConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, SphereReplaceConfig config)
    {
        if (!worldIn.getFluidState(pos).isTagged(FluidTags.WATER))
        {
            return false;
        }
        else
        {
            int i = 0;
            int j = rand.nextInt(config.radius - 2) + 2;

            for (int k = pos.getX() - j; k <= pos.getX() + j; ++k)
            {
                for (int l = pos.getZ() - j; l <= pos.getZ() + j; ++l)
                {
                    int i1 = k - pos.getX();
                    int j1 = l - pos.getZ();

                    if (i1 * i1 + j1 * j1 <= j * j)
                    {
                        for (int k1 = pos.getY() - config.ySize; k1 <= pos.getY() + config.ySize; ++k1)
                        {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            Block block = worldIn.getBlockState(blockpos).getBlock();

                            if (config.targets.contains(block))
                            {
                                worldIn.setBlockState(blockpos, config.block.getDefaultState(), 2);
                                ++i;
                            }
                        }
                    }
                }
            }

            return i > 0;
        }
    }
}
