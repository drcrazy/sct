/**
 * 
 */
package ru.wowhcb.sct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wowhcb.sct.proxy.CommonProxy;


/**
 * @author drcrazy
 *
 */
@Mod(modid = Sct.MODID, version = Sct.VERSION, name = Sct.MODNAME)
public class Sct {
	
    public static final String MODID = "sct";
    public static final String MODNAME = "Smart Crafting Table";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    
    @Mod.Instance
    public static Sct INSTANCE;

    @SidedProxy(serverSide = "ru.wowhcb.sct.proxy.CommonProxy", clientSide = "ru.wowhcb.sct.proxy.ClientProxy")
    public static CommonProxy PROXY;
    
    @GameRegistry.ObjectHolder("sct:smart_workbench")
    public static SCTBlock smart_workbench;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	INSTANCE = this;
    	PROXY.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	PROXY.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	PROXY.postInit(e);
    }
}
