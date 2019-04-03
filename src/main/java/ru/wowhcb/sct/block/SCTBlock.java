/**
 * 
 */
package ru.wowhcb.sct.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.wowhcb.sct.SCTTileEntity;
import ru.wowhcb.sct.Sct;

/**
 * @author drcrazy
 *
 */
public class SCTBlock extends BlockContainer {

	public SCTBlock() {
		super(Material.WOOD);
		setHardness(2.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new SCTTileEntity();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof SCTTileEntity) {
			playerIn.openGui(Sct.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		return false;
	}

}
