/**
 * 
 */
package ru.wowhcb.sct.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.wowhcb.sct.gui.SCTGuiSideInventory;

/**
 * @author drcrazy
 *
 */
public class SCTSideContainer extends Container {
	
	private final SCTContainer parentContainer;
	private final IItemHandler inventory;
	public int numSlots;
	public int numRows;

	/**
	 * @param parentContainer SCTContainer Parent inventory container
	 * @param inventory 
	 * 
	 */
	public SCTSideContainer(SCTContainer parentContainer, IItemHandler inventory) {
		this.parentContainer = parentContainer;
		this.inventory = inventory;
		this.numSlots = inventory.getSlots();
		this.numRows = this.numSlots / SCTGuiSideInventory.NUM_COLUMNS;
		int partialRow = this.numSlots % SCTGuiSideInventory.NUM_COLUMNS;
		if (partialRow > 0 ) { this.numRows++; }
		int index = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j=0; j < SCTGuiSideInventory.NUM_COLUMNS; j++) {
				if (index >= numSlots ) {
					break;
				}
				addSlotToContainer(new SlotItemHandler(inventory, index, 1, 1));
				index++;
			}
			
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return parentContainer.canInteractWith(playerIn);
	}

}
