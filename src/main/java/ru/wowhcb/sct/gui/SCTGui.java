/**
 * 
 */
package ru.wowhcb.sct.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import ru.wowhcb.sct.SCTContainer;

/**
 * @author drcrazy
 *
 */

@SideOnly(Side.CLIENT)
public class SCTGui extends GuiContainer {

	private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/container/crafting_table.png");
	private static final int SIDE_WIDTH = 7 + 54 + 7;
	//private SCTContainer container;
	private int numSideSlots = 0;

	public SCTGui(SCTContainer container) {
		super(container);
		//this.container = container;
		if (!container.adjacentInventories.isEmpty()) {
			for (IItemHandler handler : container.adjacentInventories) {
				numSideSlots += handler.getSlots();
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BACKGROUND);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 165);
		if (numSideSlots > 0) {
			drawTexturedModalRect(guiLeft - SIDE_WIDTH, guiTop, 0, 0, SIDE_WIDTH, 6);
			int i = 0;
			for (i=0; i < numSideSlots / 5; i++) {
				drawTexturedModalRect(guiLeft - SIDE_WIDTH, guiTop + 6 + i * 18, 0, 5, SIDE_WIDTH, 9);
				drawTexturedModalRect(guiLeft - SIDE_WIDTH, guiTop + 6 + i * 18 + 9, 0, 5, SIDE_WIDTH, 9);
			}
			drawTexturedModalRect(guiLeft - SIDE_WIDTH, guiTop + 6 + i * 18, 0, 165 - 6, SIDE_WIDTH, 6 );		
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRenderer.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
