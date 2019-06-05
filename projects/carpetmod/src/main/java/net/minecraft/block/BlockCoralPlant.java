package net.minecraft.block;

import java.util.Random;

import carpet.CarpetSettings;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.CoralClawFeature;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.CoralMushroomFeature;
import net.minecraft.world.gen.feature.CoralTreeFeature;

public class BlockCoralPlant extends BlockCoralPlantBase implements IGrowable
{
    private final Block deadBlock;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    protected BlockCoralPlant(Block p_i49809_1_, Block.Properties p_i49809_2_)
    {
        super(p_i49809_2_);
        this.deadBlock = p_i49809_1_;
    }

    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState)
    {
        this.updateIfDry(state, worldIn, pos);
    }

    public void tick(IBlockState state, World worldIn, BlockPos pos, Random random)
    {
        if (!isInWater(state, worldIn, pos))
        {
            worldIn.setBlockState(pos, this.deadBlock.getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)), 2);
        }
    }

    public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (facing == EnumFacing.DOWN && !stateIn.isValidPosition(worldIn, currentPos))
        {
            return Blocks.AIR.getDefaultState();
        }
        else
        {
            this.updateIfDry(stateIn, worldIn, currentPos);

            if (stateIn.get(WATERLOGGED))
            {
                worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }

            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return CarpetSettings.getBool("renewableCoral") && state.get(WATERLOGGED) && worldIn.getFluidState(pos.up()).isTagged(FluidTags.WATER);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.15D;
    }

    public void grow(World worldIn, Random random, BlockPos pos, IBlockState blockUnder)
    {
        CoralFeature coral;
        int variant = random.nextInt(3);
        if (variant == 0)
            coral = new CoralClawFeature();
        else if (variant == 1)
            coral = new CoralTreeFeature();
        else
            coral = new CoralMushroomFeature();

        MaterialColor color = blockUnder.getMaterialColor(worldIn, pos);
        IBlockState proper_block = blockUnder;
        for (Block block: BlockTags.CORAL_BLOCKS.getAllElements())
        {
            proper_block = block.getDefaultState();
            if (proper_block.getMaterialColor(worldIn,pos) == color)
            {
                break;
            }
        }
        worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 4);

        if (!coral.growSpecific(worldIn, random, pos, proper_block))
        {
            worldIn.setBlockState(pos, blockUnder, 3);
        }
        else
        {
            if (worldIn.rand.nextInt(10)==0)
            {
                BlockPos randomPos = pos.add(worldIn.rand.nextInt(16)-8,worldIn.rand.nextInt(8),worldIn.rand.nextInt(16)-8  );
                if (BlockTags.CORAL_BLOCKS.contains(worldIn.getBlockState(randomPos).getBlock()))
                {
                    worldIn.setBlockState(pos, Blocks.WET_SPONGE.getDefaultState(), 3);
                }
            }
        }
    }

    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return SHAPE;
    }
}
