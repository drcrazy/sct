package ru.wowhcb.sct.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.util.collection.DefaultedList;

public class SCTCraftingResultSlot extends CraftingResultSlot {
    protected final WorkbenchScreenHandler screenHandler;
    public SCTCraftingResultSlot(WorkbenchScreenHandler screenHandler, PlayerEntity player, CraftingInventory input, Inventory inventory, int index, int x, int y) {
        super(player, input, inventory, index, x, y);
        this.screenHandler = screenHandler;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        DefaultedList<ItemStack> remainingStacks;

        if (screenHandler.cachedRecipe != null && screenHandler.cachedRecipe.matches(screenHandler.craftingInventory, player.world)) {
            remainingStacks = screenHandler.cachedRecipe.getRemainder(screenHandler.craftingInventory);
        }
        else {
            remainingStacks = player.world.getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, screenHandler.craftingInventory, player.world);
        }

        for (int i = 0; i < remainingStacks.size(); ++i) {
            ItemStack inventoryStack = screenHandler.craftingInventory.getStack(i);
            ItemStack remainingStack = remainingStacks.get(i);
            if (!inventoryStack.isEmpty()) {
                screenHandler.craftingInventory.removeStack(i, 1);
                inventoryStack = screenHandler.craftingInventory.getStack(i);
            }
            if (remainingStack.isEmpty()) continue;
            if (inventoryStack.isEmpty()) {
                screenHandler.craftingInventory.setStack(i, remainingStack);
                continue;
            }
            if (ItemStack.areItemsEqualIgnoreDamage(inventoryStack, remainingStack) && ItemStack.areNbtEqual(inventoryStack, remainingStack)) {
                remainingStack.increment(inventoryStack.getCount());
                screenHandler.craftingInventory.setStack(i, remainingStack);
                continue;
            }
            if (screenHandler.player.getInventory().insertStack(remainingStack)) continue;
            screenHandler.player.dropItem(remainingStack, false);
        }
    }
}
