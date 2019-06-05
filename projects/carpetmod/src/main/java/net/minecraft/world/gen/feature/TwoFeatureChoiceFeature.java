package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class TwoFeatureChoiceFeature extends Feature<TwoFeatureChoiceConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, TwoFeatureChoiceConfig config)
    {
        boolean flag = rand.nextBoolean();
        return flag ? this.place(config.trueFeature, config.trueConfig, worldIn, generator, rand, pos) : this.place(config.falseFeature, config.falseConfig, worldIn, generator, rand, pos);
    }

    <FC extends IFeatureConfig> boolean place(Feature<FC> p_202360_1_, IFeatureConfig p_202360_2_, IWorld p_202360_3_, IChunkGenerator <? extends IChunkGenSettings > p_202360_4_, Random p_202360_5_, BlockPos p_202360_6_)
    {
        return p_202360_1_.place(p_202360_3_, p_202360_4_, p_202360_5_, p_202360_6_, (FC)p_202360_2_);
    }
}
