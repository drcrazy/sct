package ru.wowhcb.sct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.wowhcb.sct.block.WorkbenchBlock;
import ru.wowhcb.sct.block.WorkbenchSlab;
import ru.wowhcb.sct.blockentity.WorkbenchBlockEntity;
import ru.wowhcb.sct.client.WorkbenchScreen;
import ru.wowhcb.sct.container.WorkbenchContainer;


/**
 * @author drcrazy
 *
 */
@EnvironmentInterface(itf = ClientModInitializer.class, value = EnvType.CLIENT)
public class SCT implements ModInitializer, ClientModInitializer {
	
	public static final String MOD_ID = "sct";
	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static Block SMART_WORKBENCH;
	public static Block SMART_WORKBENCH_SLAB;
	public static BlockEntityType<WorkbenchBlockEntity> SMART_WORKBENCH_BE_TYPE;
	public static Identifier SCT_CONTAINER;
	
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

		// BlockEntityTypr
		SCT.SMART_WORKBENCH_BE_TYPE = Registry.register(Registry.BLOCK_ENTITY,
				new Identifier(SCT.MOD_ID, "smart_workbench_blockentity"),
				BlockEntityType.Builder.create(WorkbenchBlockEntity::new, SCT.SMART_WORKBENCH, SCT.SMART_WORKBENCH_SLAB).build(null));

		// Container
		SCT.SCT_CONTAINER = new Identifier(SCT.MOD_ID, "smart_workbench_container");
		ContainerProviderRegistry.INSTANCE.registerFactory(SCT.SCT_CONTAINER , (syncId, identifier, player, buf) -> {
			return new WorkbenchContainer(syncId, player);
		});

		SCT.LOGGER.debug("Smart Crafting Table setup done!");
	}
	
    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
    	// GUI
    	ScreenProviderRegistry.INSTANCE.registerFactory(SCT.SCT_CONTAINER, WorkbenchScreen::new);
    }
    

}
