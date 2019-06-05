package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class ChorusPlantFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos).getBlock() == Blocks.END_STONE)
        {
            BlockChorusFlower.generatePlant(worldIn, pos.up(), rand, 8);
            return true;
        }
        else
        {
            return false;
        }
    }
}
