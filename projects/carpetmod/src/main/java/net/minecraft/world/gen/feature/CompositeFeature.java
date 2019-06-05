package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class CompositeFeature<F extends IFeatureConfig, D extends IPlacementConfig> extends Feature<NoFeatureConfig>
{
    protected final Feature<F> feature;
    protected final F featureConfig;
    protected final BasePlacement<D> basePlacement;
    protected final D placementConfig;

    public CompositeFeature(Feature<F> featureIn, F featureConfigIn, BasePlacement<D> basePlacementIn, D placementConfigIn)
    {
        this.featureConfig = featureConfigIn;
        this.placementConfig = placementConfigIn;
        this.basePlacement = basePlacementIn;
        this.feature = featureIn;
    }

    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        return this.basePlacement.generate(worldIn, generator, rand, pos, this.placementConfig, this.feature, this.featureConfig);
    }

    public String toString()
    {
        return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), this.basePlacement, this.feature);
    }

    public Feature<F> getFeature()
    {
        return this.feature;
    }
}
