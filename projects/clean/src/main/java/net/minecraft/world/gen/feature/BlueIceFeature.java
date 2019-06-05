package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BlueIceFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (pos.getY() > worldIn.getSeaLevel() - 1)
        {
            return false;
        }
        else if (worldIn.getBlockState(pos).getBlock() != Blocks.WATER && worldIn.getBlockState(pos.down()).getBlock() != Blocks.WATER)
        {
            return false;
        }
        else
        {
            boolean flag = false;

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == Blocks.PACKED_ICE)
                {
                    flag = true;
                    break;
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                worldIn.setBlockState(pos, Blocks.BLUE_ICE.getDefaultState(), 2);

                for (int i = 0; i < 200; ++i)
                {
                    int j = rand.nextInt(5) - rand.nextInt(6);
                    int k = 3;

                    if (j < 2)
                    {
                        k += j / 2;
                    }

                    if (k >= 1)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(k) - rand.nextInt(k), j, rand.nextInt(k) - rand.nextInt(k));
                        IBlockState iblockstate = worldIn.getBlockState(blockpos);
                        Block block = iblockstate.getBlock();

                        if (iblockstate.getMaterial() == Material.AIR || block == Blocks.WATER || block == Blocks.PACKED_ICE || block == Blocks.ICE)
                        {
                            for (EnumFacing enumfacing1 : EnumFacing.values())
                            {
                                Block block1 = worldIn.getBlockState(blockpos.offset(enumfacing1)).getBlock();

                                if (block1 == Blocks.BLUE_ICE)
                                {
                                    worldIn.setBlockState(blockpos, Blocks.BLUE_ICE.getDefaultState(), 2);
                                    break;
                                }
                            }
                        }
                    }
                }

                return true;
            }
        }
    }
}
