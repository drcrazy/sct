package ru.wowhcb.sct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.wowhcb.sct.block.WorkbenchBlock;
import ru.wowhcb.sct.block.WorkbenchSlab;


/**
 * @author drcrazy
 *
 */
public class SCT implements ModInitializer {
	
	public static final String MOD_ID = "sct";
	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static final WorkbenchBlock SMART_WORKBENCH = new WorkbenchBlock();
	public static final WorkbenchSlab SMART_WORKBENCH_SLAB = new WorkbenchSlab();
	
	@Override
	public void onInitialize() {
		
			// Blocks
			Registry.register(Registry.BLOCK, new Identifier(SCT.MOD_ID, "smart_workbench"), SCT.SMART_WORKBENCH); 
			Registry.register(Registry.BLOCK, new Identifier(SCT.MOD_ID, "smart_workbench_slab"), SCT.SMART_WORKBENCH_SLAB);
			
			//ItemBlocks
			BlockItem smart_workbench = new BlockItem(SCT.SMART_WORKBENCH, new Item.Settings().group(ItemGroup.DECORATIONS));
			Registry.register(Registry.ITEM, new Identifier(SCT.MOD_ID, "smart_workbench"), smart_workbench);
			BlockItem smart_workbench_slab = new BlockItem(SCT.SMART_WORKBENCH_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS));
			Registry.register(Registry.ITEM, new Identifier(SCT.MOD_ID, "smart_workbench_slab"), smart_workbench_slab);
			
			
			//Items
			
			SCT.LOGGER.debug("Smart Crafting Table setup done!");
	}

}
