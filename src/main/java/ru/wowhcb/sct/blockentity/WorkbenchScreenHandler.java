package ru.wowhcb.sct.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import ru.wowhcb.sct.SCT;

import java.util.Optional;

public class WorkbenchScreenHandler extends ScreenHandler {
    public final CraftingInventory craftingInventory = new CraftingInventory(this, 3, 3);
    public final PlayerEntity player;
    private final CraftingResultInventory resultInventory = new CraftingResultInventory();
    private final Inventory blockEntityInventory;
    private final World world;
    public CraftingRecipe cachedRecipe = null;

    public WorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public WorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(SCT.SMART_WORKBENCH_SCREEN_HANDLER, syncId, playerInventory, inventory);
    }

    protected WorkbenchScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory beInventory) {
        super(type, syncId);
        this.player = playerInventory.player;
        this.world = playerInventory.player.world;
        this.blockEntityInventory = beInventory;
        checkSize(beInventory, 9);

        // Load blockentity inventory into crafting grid
        for (int i = 0; i < 9; i++) {
            craftingInventory.setStack(i, blockEntityInventory.getStack(i));
        }

        // Crafting Result Slot
        this.addSlot(new SCTCraftingResultSlot(this, playerInventory.player, this.craftingInventory, this.resultInventory, 0, 124, 35));

        int h;
        int w;
        // Workbench inventory
        for (h = 0; h < 3; ++h) {
            for (w = 0; w < 3; ++w) {
                this.addSlot(new Slot(this.craftingInventory, w + h * 3, 30 + w * 18, 17 + h * 18));
            }
        }

        //The player inventory
        for (h = 0; h < 3; ++h) {
            for (w = 0; w < 9; ++w) {
                this.addSlot(new Slot(playerInventory, w + h * 9 + 9, 8 + w * 18, 84 + h * 18));
            }
        }
        //The player Hotbar
        for (w = 0; w < 9; ++w) {
            this.addSlot(new Slot(playerInventory, w, 8 + w * 18, 142));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (!slot.hasStack()) {
            return itemStack;
        }
        ItemStack itemStack2 = slot.getStack();
        itemStack = itemStack2.copy();
        if (index == 0) {
            itemStack2.getItem().onCraft(itemStack2, world, player);
            if (!this.insertItem(itemStack2, 10, 46, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickTransfer(itemStack2, itemStack);
        } else if (index >= 10 && index < 46 ? !this.insertItem(itemStack2, 1, 10, false) && (index < 37 ? !this.insertItem(itemStack2, 37, 46, false) : !this.insertItem(itemStack2, 10, 37, false)) : !this.insertItem(itemStack2, 10, 46, false)) {
            return ItemStack.EMPTY;
        }
        if (itemStack2.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }
        if (itemStack2.getCount() == itemStack.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTakeItem(player, itemStack2);
        if (index == 0) {
            player.dropItem(itemStack2, false);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.blockEntityInventory.canPlayerUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        if (this.world.isClient) {
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (!blockEntityInventory.getStack(i).isEmpty()) {
                blockEntityInventory.setStack(i, ItemStack.EMPTY);
            }
            blockEntityInventory.setStack(i, craftingInventory.removeStack(i));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack craftingResult;
        CraftingRecipe currentRecipe = null;

        // Check cache
        if (cachedRecipe != null && !cachedRecipe.matches(craftingInventory, world)) {
            cachedRecipe = null;
        }

        // Find or set current recipe
        if (cachedRecipe == null) {
            Optional<CraftingRecipe> optional = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                currentRecipe = optional.get();
                cachedRecipe = currentRecipe;
            }
        } else {
            currentRecipe = cachedRecipe;
        }

        if (world.isClient) {
            return;
        }

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        if (currentRecipe != null && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, currentRecipe)) {
            craftingResult = currentRecipe.craft(craftingInventory);
        } else {
            craftingResult = ItemStack.EMPTY;
        }

        resultInventory.setStack(0, craftingResult);
        //setPreviousTrackedSlot(0, craftingResult);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, nextRevision(), 0, craftingResult));

    }
}
