package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomFeatureWithConfigFeature extends Feature<RandomFeatureWithConfigConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, RandomFeatureWithConfigConfig config)
    {
        int i = rand.nextInt(config.features.length);
        return this.place(config.features[i], config.configs[i], worldIn, generator, rand, pos);
    }

    <FC extends IFeatureConfig> boolean place(Feature<FC> p_204627_1_, IFeatureConfig p_204627_2_, IWorld p_204627_3_, IChunkGenerator <? extends IChunkGenSettings > p_204627_4_, Random p_204627_5_, BlockPos p_204627_6_)
    {
        return p_204627_1_.place(p_204627_3_, p_204627_4_, p_204627_5_, p_204627_6_, (FC)p_204627_2_);
    }
}
