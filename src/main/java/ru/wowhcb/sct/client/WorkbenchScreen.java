/**
 * 
 */
package ru.wowhcb.sct.client;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import ru.wowhcb.sct.container.WorkbenchContainer;

/**
 * @author drcrazy
 *
 */

@Environment(EnvType.CLIENT)
public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchContainer> {
	
	private static final Identifier BACKGROUND = new Identifier("minecraft", "textures/gui/container/crafting_table.png");

	public WorkbenchScreen(WorkbenchContainer container) {
		super(container, container.playerInventory, new TranslatableText("Smart Crafting Table"));
	}

	@Override
	protected void drawBackground(float partialTicks, int mouseX, int mouseY) {
		  GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	      minecraft.getTextureManager().bindTexture(BACKGROUND);
	      int y = (height - containerHeight) / 2;
	      blit(left, y, 0, 0, containerWidth, containerHeight);
	}
}
