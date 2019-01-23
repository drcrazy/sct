/**
 * 
 */
package ru.wowhcb.sct.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import ru.wowhcb.sct.SCTContainer;
import ru.wowhcb.sct.Sct;
import ru.wowhcb.sct.gui.SCTGui;

/**
 * @author drcrazy
 *
 */

@mezz.jei.api.JEIPlugin
public class SCTJeiPlugin implements IModPlugin {

	@Override
	public void register(@Nonnull IModRegistry registry) {
		registry.addRecipeClickArea(SCTGui.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(SCTContainer.class,
				VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
		registry.addRecipeCatalyst(new ItemStack(Sct.smart_workbench), VanillaRecipeCategoryUid.CRAFTING);
	}
}
