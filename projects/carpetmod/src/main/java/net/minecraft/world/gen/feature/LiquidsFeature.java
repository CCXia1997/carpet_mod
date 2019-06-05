package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class LiquidsFeature extends Feature<LiquidsConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, LiquidsConfig config)
    {
        if (!Block.isRock(worldIn.getBlockState(pos.up()).getBlock()))
        {
            return false;
        }
        else if (!Block.isRock(worldIn.getBlockState(pos.down()).getBlock()))
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (!iblockstate.isAir() && !Block.isRock(iblockstate.getBlock()))
            {
                return false;
            }
            else
            {
                int i = 0;
                int j = 0;

                if (Block.isRock(worldIn.getBlockState(pos.west()).getBlock()))
                {
                    ++j;
                }

                if (Block.isRock(worldIn.getBlockState(pos.east()).getBlock()))
                {
                    ++j;
                }

                if (Block.isRock(worldIn.getBlockState(pos.north()).getBlock()))
                {
                    ++j;
                }

                if (Block.isRock(worldIn.getBlockState(pos.south()).getBlock()))
                {
                    ++j;
                }

                int k = 0;

                if (worldIn.isAirBlock(pos.west()))
                {
                    ++k;
                }

                if (worldIn.isAirBlock(pos.east()))
                {
                    ++k;
                }

                if (worldIn.isAirBlock(pos.north()))
                {
                    ++k;
                }

                if (worldIn.isAirBlock(pos.south()))
                {
                    ++k;
                }

                if (j == 3 && k == 1)
                {
                    worldIn.setBlockState(pos, config.fluid.getDefaultState().getBlockState(), 2);
                    worldIn.getPendingFluidTicks().scheduleTick(pos, config.fluid, 0);
                    ++i;
                }

                return i > 0;
            }
        }
    }
}
