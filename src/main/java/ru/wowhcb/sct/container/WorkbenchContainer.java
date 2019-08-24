package ru.wowhcb.sct.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

/**
 * @author drcrazy
 *
 */
public class WorkbenchContainer extends Container {
	
	public final PlayerInventory playerInventory;

	public WorkbenchContainer(int syncId, PlayerEntity player) {
		super(null, syncId);
		this.playerInventory = player.inventory;
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
		}
	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		return true;
	}

}
