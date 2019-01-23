/**
 * 
 */
package ru.wowhcb.sct.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.wowhcb.sct.SCTContainer;
import ru.wowhcb.sct.SCTTileEntity;
import ru.wowhcb.sct.gui.SCTGui;

/**
 * @author drcrazy
 *
 */
public class GuiProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
	    if (tile instanceof SCTTileEntity) {
	    	return new SCTContainer(player.inventory, (SCTTileEntity) tile);    	
	    }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
	    if (tile instanceof SCTTileEntity) {
	    	return new SCTGui(new SCTContainer(player.inventory, (SCTTileEntity) tile));
	    }
		return null;
	}
}
