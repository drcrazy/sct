/**
 * 
 */
package ru.wowhcb.sct;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drcrazy
 *
 */

@SideOnly(Side.CLIENT)
public class SCTGui extends GuiContainer {
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/container/crafting_table.png");

	public SCTGui(SCTContainer container) {
		super(container);
	}


	  @Override
	  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		   GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		    mc.getTextureManager().bindTexture(BACKGROUND);
		    drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 165);
	  }

}
