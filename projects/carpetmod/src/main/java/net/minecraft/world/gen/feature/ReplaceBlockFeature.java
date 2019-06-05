package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class ReplaceBlockFeature extends Feature<ReplaceBlockConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, ReplaceBlockConfig config)
    {
        if (config.target.test(worldIn.getBlockState(pos)))
        {
            worldIn.setBlockState(pos, config.state, 2);
        }

        return true;
    }
}
