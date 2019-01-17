/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * @author drcrazy
 *
 */
public class SCTContainer extends Container {

	private SCTTileEntity tile;

	/**
	 * @param playerInventory InventoryPlayer Inventory of a player who use our tile
	 * @param tile            SCTTileEntity Smart Workbench tile entity
	 * 
	 */
	public SCTContainer(InventoryPlayer playerInventory, SCTTileEntity tile) {
		this.tile = tile;

		// Tile slots
		IItemHandler itemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 10;
		int y = 6;

		int slotIndex = 0;
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
			slotIndex++;
			x += 18;
		}

		// Player slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
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
