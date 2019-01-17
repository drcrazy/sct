/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

/**
 * @author drcrazy
 *
 */
public class SCTContainer extends Container {

	private SCTTileEntity tile;
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	
	/**
	 * @param playerInventory InventoryPlayer Inventory of a player who use our tile
	 * @param tile            SCTTileEntity Smart Workbench tile entity
	 * 
	 */
	public SCTContainer(InventoryPlayer playerInventory, SCTTileEntity tile) {
		this.tile = tile;
		addSlotToContainer(new SlotCrafting(playerInventory.player, craftMatrix, craftResult, 0, 124, 35));

		// Tile slots
	    for(int i = 0; i < 3; ++i) {
	        for(int j = 0; j < 3; ++j) {
	          addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
	        }
	      }

		// Player slots
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
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < SCTTileEntity.INV_SIZE) {
				if (!this.mergeItemStack(itemstack1, SCTTileEntity.INV_SIZE, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, SCTTileEntity.INV_SIZE, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

}
