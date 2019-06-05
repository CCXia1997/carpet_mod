package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomDefaultFeatureList extends Feature<RandomFeatureListConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, RandomFeatureListConfig config)
    {
        int i = rand.nextInt(5) - 3 + config.count;

        for (int j = 0; j < i; ++j)
        {
            int k = rand.nextInt(config.field_202454_a.length);
            this.func_202361_a(config.field_202454_a[k], config.configs[k], worldIn, generator, rand, pos);
        }

        return true;
    }

    <FC extends IFeatureConfig> boolean func_202361_a(Feature<FC> p_202361_1_, IFeatureConfig p_202361_2_, IWorld p_202361_3_, IChunkGenerator <? extends IChunkGenSettings > p_202361_4_, Random p_202361_5_, BlockPos p_202361_6_)
    {
        return p_202361_1_.place(p_202361_3_, p_202361_4_, p_202361_5_, p_202361_6_, (FC)p_202361_2_);
    }
}
