/**
 * 
 */
package ru.wowhcb.sct;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author drcrazy
 *
 */
public class SCTContainer extends ContainerWorkbench {

	private final SCTTileEntity tile;
	private final ItemStackHandler tileInventory;
	public final World world;
	public final BlockPos pos;
	public IRecipe lastRecipe;
	public ArrayList<IItemHandler> adjacentInventories = new ArrayList<IItemHandler>();
	
	/**
	 * @param playerInventory InventoryPlayer Inventory of a player who use our tile
	 * @param tile            SCTTileEntity Smart Workbench tile entity
	 * 
	 */
	public SCTContainer(InventoryPlayer playerInventory, SCTTileEntity tile) {
		super(playerInventory, tile.getWorld(), tile.getPos());
		this.tile = tile;
		this.world = tile.getWorld();
		this.pos = tile.getPos();
 		this.tileInventory = (ItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity adjacentTile = world.getTileEntity(pos.offset(face));
			if (adjacentTile == null) {
				continue;
			} else if (adjacentTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face.getOpposite())) {
				IItemHandler adjacentInventory = adjacentTile
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face.getOpposite());
				if (adjacentInventory != null) {
					adjacentInventories.add(adjacentInventory);
				}
			}
		}
 		inventorySlots.clear();
 		inventoryItemStacks.clear();
 		loadInventoryFromTile();
 		// Slot for craft result
 		addSlotToContainer(new SCTCraftingSlot(this, playerInventory.player, craftMatrix, craftResult, 0, 124, 35));
 		// Slots for craft matrix
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
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
	
	private void loadInventoryFromTile() {
		for (int i = 0; i < 9; i++) {
			craftMatrix.setInventorySlotContents(i, tileInventory.getStackInSlot(i));
		}
	}
	
	private void saveInventoryToTile() {
		for (int i = 0; i < 9; i++) {
			if (!tileInventory.getStackInSlot(i).isEmpty()) { tileInventory.setStackInSlot(i, ItemStack.EMPTY); }
			tileInventory.setStackInSlot(i, craftMatrix.removeStackFromSlot(i));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.canInteractWith(playerIn);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		if (this.world.isRemote) { return; }
		saveInventoryToTile();
	}
	
	@Override
	// Based on same method from FastWorkbench
	protected void slotChangedCraftingGrid(World world, EntityPlayer player, InventoryCrafting inv, InventoryCraftResult result) {
		if (this.world.isRemote) { return; }
		
		ItemStack currentResult = ItemStack.EMPTY;
		IRecipe currentRecipe = null;

		if (lastRecipe == null || !lastRecipe.matches(inv, world)) {
			currentRecipe = CraftingManager.findMatchingRecipe(inv, world);
		} else {
			currentRecipe = lastRecipe;
		}

		if (currentRecipe != null) {
			currentResult = currentRecipe.getCraftingResult(inv);
		}

		result.setInventorySlotContents(0, currentResult);
		EntityPlayerMP entityplayermp = (EntityPlayerMP) player;
		if (currentRecipe != lastRecipe) {
			entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, currentResult));
			lastRecipe = currentRecipe;
		} else if (currentRecipe != null && currentRecipe == lastRecipe
				&& !ItemStack.areItemStacksEqual(currentResult, lastRecipe.getCraftingResult(inv))) {
			entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, currentResult));
		}
	}
}
