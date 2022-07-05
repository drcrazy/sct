package ru.wowhcb.sct;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.wowhcb.sct.block.WorkbenchBlock;
import ru.wowhcb.sct.block.WorkbenchSlab;
import ru.wowhcb.sct.blockentity.WorkbenchBlockEntity;
import ru.wowhcb.sct.blockentity.WorkbenchScreenHandler;


/**
 * @author drcrazy
 *
 */
public class SCT implements ModInitializer {
	
	public static final String MOD_ID = "sct";
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static Block SMART_WORKBENCH;
	public static Block SMART_WORKBENCH_SLAB;
	public static BlockEntityType<WorkbenchBlockEntity> SMART_WORKBENCH_BE_TYPE;
	public static ScreenHandlerType<WorkbenchScreenHandler> SMART_WORKBENCH_SCREEN_HANDLER;
	
	@Override
	public void onInitialize() {

		// Blocks
		Registry.register(Registry.BLOCK, new Identifier(SCT.MOD_ID, "smart_workbench"), SCT.SMART_WORKBENCH = new WorkbenchBlock());
		Registry.register(Registry.BLOCK, new Identifier(SCT.MOD_ID, "smart_workbench_slab"), SCT.SMART_WORKBENCH_SLAB = new WorkbenchSlab());

		// ItemBlocks
		BlockItem smart_workbench = new BlockItem(SCT.SMART_WORKBENCH, new Item.Settings().group(ItemGroup.DECORATIONS));
		Registry.register(Registry.ITEM, new Identifier(SCT.MOD_ID, "smart_workbench"), smart_workbench);
		BlockItem smart_workbench_slab = new BlockItem(SCT.SMART_WORKBENCH_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS));
		Registry.register(Registry.ITEM, new Identifier(SCT.MOD_ID, "smart_workbench_slab"), smart_workbench_slab);

		// BlockEntityType
		SCT.SMART_WORKBENCH_BE_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
				new Identifier(SCT.MOD_ID, "smart_workbench_blockentity"),
				FabricBlockEntityTypeBuilder.create(WorkbenchBlockEntity::new, SCT.SMART_WORKBENCH, SCT.SMART_WORKBENCH_SLAB).build(null));

		// ScreenHandler
		SCT.SMART_WORKBENCH_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER,
				new Identifier(SCT.MOD_ID, "smart_workbench_screenhandler"),
				new ScreenHandlerType<>(WorkbenchScreenHandler::new));


		SCT.LOGGER.debug("Smart Crafting Table setup done!");
	}
}
