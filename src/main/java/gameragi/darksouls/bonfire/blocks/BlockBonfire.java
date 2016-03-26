package gameragi.darksouls.bonfire.blocks;

import java.util.Random;

import gameragi.darksouls.bonfire.tileentities.TileEntityBonfire;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBonfire extends BlockContainer{
	
	public BlockBonfire() {
		super(Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setBlockUnbreakable();
		this.setLightLevel(1.0F);
	}

	/**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBonfire();
	}
	
	/**
     * Get the Item that this Block should drop when harvested.
     *  
     * @param fortune the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }
    
    /**
     * Spawns this Block's drops into the World as EntityItems.
     *  
     * @param chance The chance that each Item is actually spawned (1.0 = always, 0.0 = never)
     * @param fortune The player's fortune level
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, fortune);
    }

    @Override
    public int getExpDrop(net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return 0;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.spawnParticles(worldIn, pos);
    }

    private void spawnParticles(World worldIn, BlockPos pos)
    {
    	int kindleLevel = ((TileEntityBonfire)worldIn.getTileEntity(pos)).getKindleLevel();
        Random random = worldIn.rand;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + 0.45 + random.nextFloat()*(0.1));
            double d2 = (double)((float)pos.getY() + 0.2 + random.nextFloat()*(0.3 + kindleLevel*0.15));
            double d3 = (double)((float)pos.getZ() + 0.45 + random.nextFloat()*(0.1));
            
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
    
    public void kindleEvent(World worldIn, BlockPos pos){
    	Random random = worldIn.rand;

        for (int i = 0; i < 10; ++i)
        {
            double d1 = (double)((float)pos.getX() + 0.2 + random.nextFloat()*(0.6));
            double d2 = (double)((float)pos.getY() + 0.2 + random.nextFloat()*(0.8));
            double d3 = (double)((float)pos.getZ() + 0.2 + random.nextFloat()*(0.6));
            
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}
