/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * SlotCraftingSucks from FastWorkbench See:
 * https://github.com/Shadows-of-Fire/FastWorkbench/blob/master/src/main/java/shadows/fastbench/gui/SlotCraftingSucks.java
 *
 */
public class SCTCraftingSlot extends SlotCrafting {

	protected final SCTContainer container;

	/**
	 * @param container SCTContainer Container having this slot
	 * @param player    EntityPlayer Player who use workbench
	 * @param inv       InventoryCrafting Crafting grid
	 * @param holder    InventoryCraftingResult Crafting result
	 * @param slotIndex int Slot index for result slot
	 * @param xPosition int Top left corner for result slot
	 * @param yPosition int Top left corner for result slot
	 */
	public SCTCraftingSlot(SCTContainer container, EntityPlayer player, InventoryCrafting inv,
			InventoryCraftResult holder, int slotIndex, int xPosition, int yPosition) {
		super(player, inv, holder, slotIndex, xPosition, yPosition);
		this.container = container;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(amount, getStack().getCount());
		}
		return getStack();
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not
	 * ore and wood.
	 */
	@Override
	protected void onCrafting(ItemStack stack) {
		if (this.amountCrafted > 0) {
			stack.onCrafting(this.player.world, this.player, this.amountCrafted);
			FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
		}

		this.amountCrafted = 0;
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		ForgeHooks.setCraftingPlayer(player);
		NonNullList<ItemStack> list;
		if (container.lastRecipe != null && container.lastRecipe.matches(craftMatrix, container.world)) {
			list = container.lastRecipe.getRemainingItems(craftMatrix);
		}
		else {
			list = craftMatrix.stackList;
		}
		ForgeHooks.setCraftingPlayer(null);

		for (int i = 0; i < list.size(); ++i) {
			ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
			ItemStack itemstack1 = list.get(i);

			if (!itemstack.isEmpty()) {
				this.craftMatrix.decrStackSize(i, 1);
				itemstack = this.craftMatrix.getStackInSlot(i);
			}

			if (!itemstack1.isEmpty()) {
				if (itemstack.isEmpty()) {
					this.craftMatrix.setInventorySlotContents(i, itemstack1);
				} else if (ItemStack.areItemsEqual(itemstack, itemstack1)
						&& ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
					itemstack1.grow(itemstack.getCount());
					this.craftMatrix.setInventorySlotContents(i, itemstack1);
				} else if (!this.player.inventory.addItemStackToInventory(itemstack1)) {
					this.player.dropItem(itemstack1, false);
				}
			}
		}

		return stack;
	}

}
