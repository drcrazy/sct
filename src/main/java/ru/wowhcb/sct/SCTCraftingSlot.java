/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;

/**
 * SlotCraftingSucks from FastWorkbench See:
 * https://github.com/Shadows-of-Fire/FastWorkbench/blob/master/src/main/java/shadows/fastbench/gui/SlotCraftingSucks.java
 *
 */
public class SCTCraftingSlot extends SlotCrafting {

	/**
	 * @param player            EntityPlayer Player who use workbench
	 * @param craftingInventory InventoryCrafting Crafting grid
	 * @param inventoryIn       IInventory Crafting result
	 * @param slotIndex         int Slot index for result slot
	 * @param xPosition         int Top left corner for result slot
	 * @param yPosition         int Top left corner for result slot
	 */
	public SCTCraftingSlot(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn,
			int slotIndex, int xPosition, int yPosition) {
		super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
		// TODO Auto-generated constructor stub
	}

}
