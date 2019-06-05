package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomFeatureList extends Feature<RandomDefaultFeatureListConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, RandomDefaultFeatureListConfig config)
    {
        for (int i = 0; i < config.features.length; ++i)
        {
            if (rand.nextFloat() < config.chances[i])
            {
                return this.place(config.features[i], config.configs[i], worldIn, generator, rand, pos);
            }
        }

        return this.place(config.defaultFeature, config.defaultConfig, worldIn, generator, rand, pos);
    }

    <FC extends IFeatureConfig> boolean place(Feature<FC> featureIn, IFeatureConfig config, IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos)
    {
        return featureIn.place(worldIn, generator, rand, pos, (FC)config);
    }
}
