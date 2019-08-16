/**
 * 
 */
package ru.wowhcb.sct.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

/**
 * @author drcrazy
 *
 */
public class WorkbenchBlock extends Block {

	public WorkbenchBlock() {
		super(FabricBlockSettings.of(Material.WOOD).build());
	}

}
