/**
 * 
 */
package ru.wowhcb.sct.proxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wowhcb.sct.SCTTileEntity;
import ru.wowhcb.sct.Sct;
import ru.wowhcb.sct.block.SCTBlock;
import ru.wowhcb.sct.block.SCTSlab;

/**
 * @author drcrazy
 *
 */
@Mod.EventBusSubscriber
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Sct.INSTANCE, new GuiProxy());
	}

	public void postInit(FMLPostInitializationEvent e) {
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(setBlockProperties(new SCTBlock(), "smart_workbench"));
		event.getRegistry().register(setBlockProperties(new SCTSlab(), "smart_workbench_slab"));	
		
		GameRegistry.registerTileEntity(SCTTileEntity.class, new ResourceLocation(Sct.MODID, "smart_workbench"));
	}
	
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(Sct.smart_workbench).setRegistryName(Sct.smart_workbench.getRegistryName()));
        event.getRegistry().register(new ItemBlock(Sct.smart_workbench_slab).setRegistryName(Sct.smart_workbench_slab.getRegistryName()));
    }
    
    private static Block setBlockProperties(Block block, String name) {
		block.setCreativeTab(CreativeTabs.DECORATIONS);
		block.setRegistryName(name);
		block.setUnlocalizedName(Sct.MODID + "." + name);

    	return block;
    }
}
