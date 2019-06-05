package net.minecraft.client.renderer.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChunkCache implements IWorldReader
{
    protected final int chunkStartX;
    protected final int chunkStartZ;
    protected final BlockPos cacheStartPos;
    protected final int cacheSizeX;
    protected final int cacheSizeY;
    protected final int cacheSizeZ;
    protected final Chunk[][] chunks;
    protected final RenderChunkCache.Entry[] cache;
    protected final World world;

    @Nullable
    public static RenderChunkCache generateCache(World worldIn, BlockPos from, BlockPos to, int padding)
    {
        int i = from.getX() - padding >> 4;
        int j = from.getZ() - padding >> 4;
        int k = to.getX() + padding >> 4;
        int l = to.getZ() + padding >> 4;
        Chunk[][] achunk = new Chunk[k - i + 1][l - j + 1];

        for (int i1 = i; i1 <= k; ++i1)
        {
            for (int j1 = j; j1 <= l; ++j1)
            {
                achunk[i1 - i][j1 - j] = worldIn.getChunk(i1, j1);
            }
        }

        boolean flag = true;

        for (int l1 = from.getX() >> 4; l1 <= to.getX() >> 4; ++l1)
        {
            for (int k1 = from.getZ() >> 4; k1 <= to.getZ() >> 4; ++k1)
            {
                Chunk chunk = achunk[l1 - i][k1 - j];

                if (!chunk.isEmptyBetween(from.getY(), to.getY()))
                {
                    flag = false;
                }
            }
        }

        if (flag)
        {
            return null;
        }
        else
        {
            int i2 = 1;
            BlockPos blockpos = from.add(-1, -1, -1);
            BlockPos blockpos1 = to.add(1, 1, 1);
            return new RenderChunkCache(worldIn, i, j, achunk, blockpos, blockpos1);
        }
    }

    public RenderChunkCache(World worldIn, int chunkStartXIn, int chunkStartZIn, Chunk[][] chunksIn, BlockPos startPos, BlockPos endPos)
    {
        this.world = worldIn;
        this.chunkStartX = chunkStartXIn;
        this.chunkStartZ = chunkStartZIn;
        this.chunks = chunksIn;
        this.cacheStartPos = startPos;
        this.cacheSizeX = endPos.getX() - startPos.getX() + 1;
        this.cacheSizeY = endPos.getY() - startPos.getY() + 1;
        this.cacheSizeZ = endPos.getZ() - startPos.getZ() + 1;
        this.cache = new RenderChunkCache.Entry[this.cacheSizeX * this.cacheSizeY * this.cacheSizeZ];

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(startPos, endPos))
        {
            this.cache[this.getIndex(blockpos$mutableblockpos)] = new RenderChunkCache.Entry(worldIn, blockpos$mutableblockpos);
        }
    }

    protected int getIndex(BlockPos pos)
    {
        int i = pos.getX() - this.cacheStartPos.getX();
        int j = pos.getY() - this.cacheStartPos.getY();
        int k = pos.getZ() - this.cacheStartPos.getZ();
        return k * this.cacheSizeX * this.cacheSizeY + j * this.cacheSizeX + i;
    }

    public IBlockState getBlockState(BlockPos pos)
    {
        return this.cache[this.getIndex(pos)].blockState;
    }

    public IFluidState getFluidState(BlockPos pos)
    {
        return this.cache[this.getIndex(pos)].fluidState;
    }

    public Biome getBiome(BlockPos pos)
    {
        int i = (pos.getX() >> 4) - this.chunkStartX;
        int j = (pos.getZ() >> 4) - this.chunkStartZ;
        return this.chunks[i][j].getBiome(pos);
    }

    private int getAndCacheLight(EnumLightType lightType, BlockPos pos)
    {
        return this.cache[this.getIndex(pos)].getAndCacheLight(lightType, pos);
    }

    public int getCombinedLight(BlockPos pos, int lightValue)
    {
        int i = this.getAndCacheLight(EnumLightType.SKY, pos);
        int j = this.getAndCacheLight(EnumLightType.BLOCK, pos);

        if (j < lightValue)
        {
            j = lightValue;
        }

        return i << 20 | j << 4;
    }

    @Nullable
    public TileEntity getTileEntity(BlockPos pos)
    {
        return this.getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
    }

    @Nullable
    public TileEntity getTileEntity(BlockPos pos, Chunk.EnumCreateEntityType creationType)
    {
        int i = (pos.getX() >> 4) - this.chunkStartX;
        int j = (pos.getZ() >> 4) - this.chunkStartZ;
        return this.chunks[i][j].getTileEntity(pos, creationType);
    }

    public float getBrightness(BlockPos pos)
    {
        return this.world.dimension.getLightBrightnessTable()[this.getLight(pos)];
    }

    public int getNeighborAwareLightSubtracted(BlockPos pos, int amount)
    {
        if (this.getBlockState(pos).useNeighborBrightness(this, pos))
        {
            int i = 0;

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                int j = this.getLightSubtracted(pos.offset(enumfacing), amount);

                if (j > i)
                {
                    i = j;
                }

                if (i >= 15)
                {
                    return i;
                }
            }

            return i;
        }
        else
        {
            return this.getLightSubtracted(pos, amount);
        }
    }

    public Dimension getDimension()
    {
        return this.world.getDimension();
    }

    public int getLightSubtracted(BlockPos pos, int amount)
    {
        if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() <= 30000000)
        {
            if (pos.getY() < 0)
            {
                return 0;
            }
            else if (pos.getY() >= 256)
            {
                int k = 15 - amount;

                if (k < 0)
                {
                    k = 0;
                }

                return k;
            }
            else
            {
                int i = (pos.getX() >> 4) - this.chunkStartX;
                int j = (pos.getZ() >> 4) - this.chunkStartZ;
                return this.chunks[i][j].getLightSubtracted(pos, amount);
            }
        }
        else
        {
            return 15;
        }
    }

    public boolean isChunkLoaded(int x, int z, boolean allowEmpty)
    {
        return this.isInsideCache(x, z);
    }

    public boolean canSeeSky(BlockPos pos)
    {
        return false;
    }

    public boolean isInsideCache(int x, int z)
    {
        int i = x - this.chunkStartX;
        int j = z - this.chunkStartZ;
        return i >= 0 && i < this.chunks.length && j >= 0 && j < this.chunks[i].length;
    }

    public int getHeight(Heightmap.Type heightmapType, int x, int z)
    {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    public WorldBorder getWorldBorder()
    {
        return this.world.getWorldBorder();
    }

    public boolean checkNoEntityCollision(@Nullable Entity entityIn, VoxelShape shape)
    {
        throw new RuntimeException("This method should never be called here. No entity logic inside Region");
    }

    @Nullable
    public EntityPlayer getClosestPlayer(double x, double y, double z, double distance, Predicate<Entity> predicate)
    {
        throw new RuntimeException("This method should never be called here. No entity logic inside Region");
    }

    public int getSkylightSubtracted()
    {
        return 0;
    }

    public boolean isAirBlock(BlockPos pos)
    {
        return this.getBlockState(pos).isAir();
    }

    public int getLightFor(EnumLightType type, BlockPos pos)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            int i = (pos.getX() >> 4) - this.chunkStartX;
            int j = (pos.getZ() >> 4) - this.chunkStartZ;
            return this.chunks[i][j].getLightFor(type, pos);
        }
        else
        {
            return type.defaultLightValue;
        }
    }

    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return this.getBlockState(pos).getStrongPower(this, pos, direction);
    }

    public boolean isRemote()
    {
        throw new RuntimeException("Not yet implemented");
    }

    public int getSeaLevel()
    {
        throw new RuntimeException("Not yet implemented");
    }

    @OnlyIn(Dist.CLIENT)
    public class Entry
    {
        protected final IBlockState blockState;
        protected final IFluidState fluidState;
        private int[] light;

        protected Entry(World worldIn, BlockPos pos)
        {
            this.blockState = worldIn.getBlockState(pos);
            this.fluidState = worldIn.getFluidState(pos);
        }

        protected int getAndCacheLight(EnumLightType lightType, BlockPos pos)
        {
            if (this.light == null)
            {
                this.cacheLight(pos);
            }

            return this.light[lightType.ordinal()];
        }

        private void cacheLight(BlockPos pos)
        {
            this.light = new int[EnumLightType.values().length];

            for (EnumLightType enumlighttype : EnumLightType.values())
            {
                this.light[enumlighttype.ordinal()] = this.getLightFor(enumlighttype, pos);
            }
        }

        private int getLightFor(EnumLightType lightType, BlockPos pos)
        {
            if (lightType == EnumLightType.SKY && !RenderChunkCache.this.world.getDimension().hasSkyLight())
            {
                return 0;
            }
            else if (pos.getY() >= 0 && pos.getY() < 256)
            {
                if (this.blockState.useNeighborBrightness(RenderChunkCache.this, pos))
                {
                    int l = 0;

                    for (EnumFacing enumfacing : EnumFacing.values())
                    {
                        int k = RenderChunkCache.this.getLightFor(lightType, pos.offset(enumfacing));

                        if (k > l)
                        {
                            l = k;
                        }

                        if (l >= 15)
                        {
                            return l;
                        }
                    }

                    return l;
                }
                else
                {
                    int i = (pos.getX() >> 4) - RenderChunkCache.this.chunkStartX;
                    int j = (pos.getZ() >> 4) - RenderChunkCache.this.chunkStartZ;
                    return RenderChunkCache.this.chunks[i][j].getLightFor(lightType, pos);
                }
            }
            else
            {
                return lightType.defaultLightValue;
            }
        }
    }
}
