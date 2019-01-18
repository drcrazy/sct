/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketSetSlot;
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
	
	public static final int INV_SIZE = 9;
    private InventoryCrafting tempMatrix;
    private InventoryCraftResult craftResult;
    
    private IRecipe cachedRecipe = null;
    private ItemStackHandler inventory = new ItemStackHandler(INV_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
        	
        	SCTTileEntity.this.markDirty();
        }
    };
    
	public SCTTileEntity() {
		super();
		tempMatrix = new InventoryCrafting(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return false;
			}
		}, 3, 3);
		craftResult = new InventoryCraftResult();
	}
	
	public InventoryCrafting getCraftingGrid() {
		for (int i = 0; i < 9; i++) {
			tempMatrix.setInventorySlotContents(i, inventory.getStackInSlot(i));
		}
		return tempMatrix;
	}
	
	public InventoryCraftResult getCraftResult() {
		return craftResult;
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
    
	public void refreshGrid(EntityPlayerMP playerMP, int windowId) {
		if (world.isRemote) {
			return;
		}
		tempMatrix = getCraftingGrid();
		ItemStack itemstack = ItemStack.EMPTY;
		// Check if we can craft again
		if (cachedRecipe != null) {
			if (!cachedRecipe.matches(tempMatrix, world)) {
				cachedRecipe = null;
			}
		}
		// Try to find recipe
		if (cachedRecipe == null) {
			cachedRecipe = CraftingManager.findMatchingRecipe(tempMatrix, world);
		}

		if (cachedRecipe != null && (cachedRecipe.isDynamic() || !world.getGameRules().getBoolean("doLimitedCrafting")
				|| playerMP.getRecipeBook().isUnlocked(cachedRecipe))) {
			itemstack = cachedRecipe.getCraftingResult(tempMatrix);
		}

		craftResult.setInventorySlotContents(0, itemstack);
		playerMP.connection.sendPacket(new SPacketSetSlot(windowId, 0, itemstack));
	}
	

}
