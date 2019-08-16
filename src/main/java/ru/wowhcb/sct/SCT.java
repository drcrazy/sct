/**
 * 
 */
package ru.wowhcb.sct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;


/**
 * @author drcrazy
 *
 */
public class SCT implements ModInitializer {
	
	public static final String MOD_ID = "sct";
	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	@Override
	public void onInitialize() {
			SCT.LOGGER.info("Hello from SCT");
	}

}
