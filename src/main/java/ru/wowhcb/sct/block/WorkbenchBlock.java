package ru.wowhcb.sct.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import ru.wowhcb.sct.blockentity.WorkbenchBlockEntity;

/**
 * @author drcrazy
 *
 */
public class WorkbenchBlock extends BlockWithEntity {

	public WorkbenchBlock() {
		super(FabricBlockSettings.of(Material.WOOD).build());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new WorkbenchBlockEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState blockState) {
		return BlockRenderType.MODEL;
	}

}
