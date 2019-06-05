package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndPodiumFeature extends Feature<NoFeatureConfig>
{
    public static final BlockPos END_PODIUM_LOCATION = BlockPos.ORIGIN;
    private final boolean activePortal;

    public EndPodiumFeature(boolean activePortalIn)
    {
        this.activePortal = activePortalIn;
    }

    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4), new BlockPos(pos.getX() + 4, pos.getY() + 32, pos.getZ() + 4)))
        {
            double d0 = blockpos$mutableblockpos.getDistance(pos.getX(), blockpos$mutableblockpos.getY(), pos.getZ());

            if (d0 <= 3.5D)
            {
                if (blockpos$mutableblockpos.getY() < pos.getY())
                {
                    if (d0 <= 2.5D)
                    {
                        this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
                    }
                    else if (blockpos$mutableblockpos.getY() < pos.getY())
                    {
                        this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.END_STONE.getDefaultState());
                    }
                }
                else if (blockpos$mutableblockpos.getY() > pos.getY())
                {
                    this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
                }
                else if (d0 > 2.5D)
                {
                    this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
                }
                else if (this.activePortal)
                {
                    this.setBlockState(worldIn, new BlockPos(blockpos$mutableblockpos), Blocks.END_PORTAL.getDefaultState());
                }
                else
                {
                    this.setBlockState(worldIn, new BlockPos(blockpos$mutableblockpos), Blocks.AIR.getDefaultState());
                }
            }
        }

        for (int i = 0; i < 4; ++i)
        {
            this.setBlockState(worldIn, pos.up(i), Blocks.BEDROCK.getDefaultState());
        }

        BlockPos blockpos = pos.up(2);

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            this.setBlockState(worldIn, blockpos.offset(enumfacing), Blocks.WALL_TORCH.getDefaultState().with(BlockTorchWall.HORIZONTAL_FACING, enumfacing));
        }

        return true;
    }
}
