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
    private final CraftingInventory craftingInventory = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory resultInventory = new CraftingResultInventory();

    private final PlayerEntity player;
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
        this.addSlot(new SCTCraftingResultSlot(playerInventory.player, this.craftingInventory, this.resultInventory, 0, 124, 35));

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
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.craftingInventory.size()) {
                if (!this.insertItem(originalStack, this.craftingInventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.craftingInventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.blockEntityInventory.canPlayerUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        if (this.world.isClient) { return; }
        for (int i = 0; i < 9; i++) {
            if (!blockEntityInventory.getStack(i).isEmpty()) { blockEntityInventory.setStack(i, ItemStack.EMPTY); }
            blockEntityInventory.setStack(i, craftingInventory.removeStack(i));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack craftingResult;
        CraftingRecipe currentRecipe = null;

        // Check cache
        if (cachedRecipe != null && !cachedRecipe.matches(craftingInventory, world)){
            cachedRecipe = null;
        }

        // Find or set current recipe
        if (cachedRecipe == null){
            Optional<CraftingRecipe> optional = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()){
                currentRecipe = optional.get();
                cachedRecipe = currentRecipe;
            }
        }
        else {
            currentRecipe = cachedRecipe;
        }

        if (world.isClient){ return; }

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        if (currentRecipe != null && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, currentRecipe)){
            craftingResult = currentRecipe.craft(craftingInventory);
        }
        else {
            craftingResult = ItemStack.EMPTY;
        }

        resultInventory.setStack(0, craftingResult);
        //setPreviousTrackedSlot(0, craftingResult);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, nextRevision(), 0, craftingResult));

    }
}
