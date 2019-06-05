package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class VinesFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pos);

        for (int i = pos.getY(); i < 256; ++i)
        {
            blockpos$mutableblockpos.setPos(pos);
            blockpos$mutableblockpos.move(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            blockpos$mutableblockpos.setY(i);

            if (worldIn.isAirBlock(blockpos$mutableblockpos))
            {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    IBlockState iblockstate = Blocks.VINE.getDefaultState().with(BlockVine.getPropertyFor(enumfacing), Boolean.valueOf(true));

                    if (iblockstate.isValidPosition(worldIn, blockpos$mutableblockpos))
                    {
                        worldIn.setBlockState(blockpos$mutableblockpos, iblockstate, 2);
                        break;
                    }
                }
            }
        }

        return true;
    }
}
