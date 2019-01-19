/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author drcrazy
 *
 */
public class SCTContainer extends ContainerWorkbench {

	private final SCTTileEntity tile;
	private final ItemStackHandler tileInventory;
	public final World world;
	public IRecipe lastRecipe;
	
	/**
	 * @param playerInventory InventoryPlayer Inventory of a player who use our tile
	 * @param tile            SCTTileEntity Smart Workbench tile entity
	 * 
	 */
	public SCTContainer(InventoryPlayer playerInventory, SCTTileEntity tile) {
		super(playerInventory, tile.getWorld(), tile.getPos());
		this.tile = tile;
		this.world = tile.getWorld();
 		this.tileInventory = (ItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
 		inventorySlots.clear();
 		inventoryItemStacks.clear();
 		updateCraftMatrix();
 		addSlotToContainer(new SCTCraftingSlot(this, playerInventory.player, craftMatrix, craftResult, 0, 124, 35));
		addCraftingMatrix();
		addPlayerInventory(playerInventory);
	}
	
	private void updateCraftMatrix() {
		for (int i = 0; i < 9; i++) {
			craftMatrix.setInventorySlotContents(i, tileInventory.getStackInSlot(i));
		}
	}
	
	private void addCraftingMatrix() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
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
	public void onContainerClosed(EntityPlayer player) {
		if (world.isRemote) { return; }
		for (int i = 0; i < 9; i++) {
			if (!tileInventory.getStackInSlot(i).isEmpty()) { tileInventory.setStackInSlot(i, ItemStack.EMPTY); }
			tileInventory.setStackInSlot(i, craftMatrix.removeStackFromSlot(i));
		}
	}
	
	@Override
	protected void slotChangedCraftingGrid(World world, EntityPlayer player, InventoryCrafting inv, InventoryCraftResult result) {
		
	}
	
}
