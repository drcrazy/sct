/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author drcrazy
 *
 */
public class SCTTileEntity extends TileEntity {
	
	public static final int INV_SIZE = 10;
	public static final int OUTPUT_SLOT_IDX = 9;
    private ItemStackHandler inventory; 
    private InventoryCrafting tempMatrix;
    private IRecipe cachedRecipe = null;
    
	public SCTTileEntity() {
		super();
		inventory = new ItemStackHandler(INV_SIZE) {
	        @Override
	        protected void onContentsChanged(int slot) {
	        	SCTTileEntity.this.markDirty();
	        }
	    };	
		tempMatrix = new InventoryCrafting(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return false;
			}
		}, 3, 3);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
        	inventory.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", inventory.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }
    
    public void refreshRecipe() {
    	if (world.isRemote) {
    		return;
    	}
		for (int i = 0; i < 9; i++) {
			tempMatrix.setInventorySlotContents(i, inventory.getStackInSlot(i));
		}
		if (tempMatrix.isEmpty()) {
			return;
		}
		if (cachedRecipe != null && cachedRecipe.matches(tempMatrix, world)) {
			return;
		}
		for (IRecipe testRecipe : CraftingManager.REGISTRY) {
			if (testRecipe.matches(tempMatrix, world)) {
				cachedRecipe = testRecipe;

			}
		}
    	
    }
	

}
