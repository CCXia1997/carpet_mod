package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BlockWithContextFeature extends Feature<BlockWithContextConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, BlockWithContextConfig config)
    {
        if (config.placeOn.contains(worldIn.getBlockState(pos.down())) && config.placeIn.contains(worldIn.getBlockState(pos)) && config.placeUnder.contains(worldIn.getBlockState(pos.up())))
        {
            worldIn.setBlockState(pos, config.state, 2);
            return true;
        }
        else
        {
            return false;
        }
    }
}
