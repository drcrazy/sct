package ru.wowhcb.sct.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

/**
 * @author drcrazy
 *
 */
public class WorkbenchSlab extends BlockWithEntity {

	public WorkbenchSlab() {
		super(FabricBlockSettings.of(Material.WOOD).build());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		// TODO Auto-generated method stub
		return null;
	}

}
