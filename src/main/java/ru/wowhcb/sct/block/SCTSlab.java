/**
 * 
 */
package ru.wowhcb.sct.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @author drcrazy
 *
 */
public class SCTSlab extends SCTBlock {
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D);
	
	// SCTBlock
    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }
	
    // Block
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return AABB_BOTTOM_HALF;
    }
    
    @Override
    public boolean doesSideBlockChestOpening(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

}
