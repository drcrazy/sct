package ru.wowhcb.sct.blockentity;

import java.util.Iterator;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import ru.wowhcb.sct.SCT;

public class WorkbenchBlockEntity extends BlockEntity implements Inventory {
	private DefaultedList<ItemStack> inventory;
	

	public WorkbenchBlockEntity() {
		super(SCT.SMART_WORKBENCH_BE_TYPE);
		this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
	}
	
	public void openContainer(PlayerEntity playerEntity) {
		ContainerProviderRegistry.INSTANCE.openContainer(SCT.SCT_CONTAINER, playerEntity, packetByteBuf -> packetByteBuf.writeBlockPos(pos));
	}
	
	// BlockEntity
	public void fromTag(CompoundTag compoundTag) {
		super.fromTag(compoundTag);
		Inventories.fromTag(compoundTag, inventory);
	}

	public CompoundTag toTag(CompoundTag compoundTag) {
		super.toTag(compoundTag);
		Inventories.toTag(compoundTag, inventory);

		return compoundTag;
	}

	// Inventory
	@Override
	public void clear() {
	      inventory.clear();
	      markDirty();
	}

	@Override
	public int getInvSize() {
		return 9;
	}

	@Override
	public boolean isInvEmpty() {
	      Iterator<ItemStack> iterator = inventory.iterator();

	      ItemStack stack;
	      do {
	         if (!iterator.hasNext()) {
	            return true;
	         }

	         stack = (ItemStack)iterator.next();
	      } while(stack.isEmpty());

	      return false;
	}

	@Override
	public ItemStack getInvStack(int index) {
	      return index >= 0 && index < inventory.size() ? inventory.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack takeInvStack(int index, int amount) {
	      ItemStack stack = Inventories.splitStack(inventory, index, amount);
	      if (!stack.isEmpty()) {
	         markDirty();
	      }

	      return stack;
	}

	@Override
	public ItemStack removeInvStack(int index) {
		return Inventories.removeStack(inventory, index);
	}

	@Override
	public void setInvStack(int index, ItemStack stack) {
	      inventory.set(index, stack);
	      if (!stack.isEmpty() && stack.getCount() > getInvMaxStackAmount()) {
	         stack.setCount(getInvMaxStackAmount());
	      }

	      markDirty();
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player) {
	      if (world.getBlockEntity(pos) != this) {
	          return false;
	       }
		return player.squaredDistanceTo((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
	}
}
