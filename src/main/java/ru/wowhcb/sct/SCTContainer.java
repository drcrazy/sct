/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * @author drcrazy
 *
 */
public class SCTContainer extends Container {

	private SCTTileEntity tile;
	private final World world;
	private final EntityPlayer player;
	private IItemHandler tileInventory;
	
	/**
	 * @param playerInventory InventoryPlayer Inventory of a player who use our tile
	 * @param tile            SCTTileEntity Smart Workbench tile entity
	 * 
	 */
	public SCTContainer(InventoryPlayer playerInventory, SCTTileEntity tile) {
		this.tile = tile;
        this.world = tile.getWorld();
        this.player = playerInventory.player;
		this.tileInventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		//addSlotToContainer(new SlotItemHandler(tileInventory, SCTTileEntity.OUTPUT_SLOT_IDX, 124, 35));
		addSlotToContainer(new SCTCraftingSlot(player, tile.getCraftingGrid(), tile.getCraftResult(), 0, 124, 35));
		addCraftingMatrix();
		addPlayerInventory(playerInventory);
	}
	
	private void addCraftingMatrix() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlotToContainer(new SlotItemHandler(tileInventory, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}
	}
	
	private void addPlayerInventory(InventoryPlayer playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int k = 0; k < 9; ++k) {
			addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.canInteractWith(playerIn);
	}
	
    @Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
    	if (world.isRemote) {
    		return;
    	}
    	tile.refreshGrid((EntityPlayerMP) player, windowId);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player){
		
	}
}
