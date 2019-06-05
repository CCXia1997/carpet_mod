package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class BonusChestFeature extends Feature<NoFeatureConfig>
{
    public boolean place(IWorld worldIn, IChunkGenerator <? extends IChunkGenSettings > generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(pos); (iblockstate.isAir() || iblockstate.isIn(BlockTags.LEAVES)) && pos.getY() > 1; iblockstate = worldIn.getBlockState(pos))
        {
            pos = pos.down();
        }

        if (pos.getY() < 1)
        {
            return false;
        }
        else
        {
            pos = pos.up();

            for (int i = 0; i < 4; ++i)
            {
                BlockPos blockpos = pos.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(3) - rand.nextInt(3), rand.nextInt(4) - rand.nextInt(4));

                if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).isTopSolid())
                {
                    worldIn.setBlockState(blockpos, Blocks.CHEST.getDefaultState(), 2);
                    TileEntityLockableLoot.setLootTable(worldIn, rand, blockpos, LootTableList.CHESTS_SPAWN_BONUS_CHEST);
                    BlockPos blockpos1 = blockpos.east();
                    BlockPos blockpos2 = blockpos.west();
                    BlockPos blockpos3 = blockpos.north();
                    BlockPos blockpos4 = blockpos.south();

                    if (worldIn.isAirBlock(blockpos2) && worldIn.getBlockState(blockpos2.down()).isTopSolid())
                    {
                        worldIn.setBlockState(blockpos2, Blocks.TORCH.getDefaultState(), 2);
                    }

                    if (worldIn.isAirBlock(blockpos1) && worldIn.getBlockState(blockpos1.down()).isTopSolid())
                    {
                        worldIn.setBlockState(blockpos1, Blocks.TORCH.getDefaultState(), 2);
                    }

                    if (worldIn.isAirBlock(blockpos3) && worldIn.getBlockState(blockpos3.down()).isTopSolid())
                    {
                        worldIn.setBlockState(blockpos3, Blocks.TORCH.getDefaultState(), 2);
                    }

                    if (worldIn.isAirBlock(blockpos4) && worldIn.getBlockState(blockpos4.down()).isTopSolid())
                    {
                        worldIn.setBlockState(blockpos4, Blocks.TORCH.getDefaultState(), 2);
                    }

                    return true;
                }
            }

            return false;
        }
    }
}
