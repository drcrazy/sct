package ru.wowhcb.sct.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import ru.wowhcb.sct.blockentity.WorkbenchBlockEntity;

/**
 * @author drcrazy
 *
 */
public class WorkbenchSlab extends BlockWithEntity {
	protected VoxelShape shape;

	public WorkbenchSlab() {
		super(FabricBlockSettings.of(Material.WOOD).build());
		shape = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
	}

	// BlockWithEntity
	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new WorkbenchBlockEntity();
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	// Block
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext entityContext) {
		return shape;
	}
	
	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof WorkbenchBlockEntity) {
				((WorkbenchBlockEntity) blockEntity).openContainer(playerEntity);
			}
		}
		return true;
	}
}
