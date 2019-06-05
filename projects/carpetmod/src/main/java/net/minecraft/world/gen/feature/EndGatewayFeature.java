package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndGatewayFeature extends Feature<EndGatewayConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, EndGatewayConfig config)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -2, -1), pos.add(1, 2, 1)))
        {
            boolean flag = blockpos$mutableblockpos.getX() == pos.getX();
            boolean flag1 = blockpos$mutableblockpos.getY() == pos.getY();
            boolean flag2 = blockpos$mutableblockpos.getZ() == pos.getZ();
            boolean flag3 = Math.abs(blockpos$mutableblockpos.getY() - pos.getY()) == 2;

            if (flag && flag1 && flag2)
            {
                BlockPos blockpos = blockpos$mutableblockpos.toImmutable();
                this.setBlockState(worldIn, blockpos, Blocks.END_GATEWAY.getDefaultState());

                if (config.func_209959_a())
                {
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntityEndGateway)
                    {
                        TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
                        tileentityendgateway.setExitPortal(EndDimension.SPAWN);
                    }
                }
            }
            else if (flag1)
            {
                this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
            }
            else if (flag3 && flag && flag2)
            {
                this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
            }
            else if ((flag || flag2) && !flag3)
            {
                this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
            }
            else
            {
                this.setBlockState(worldIn, blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
            }
        }

        return true;
    }
}
