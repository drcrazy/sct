/**
 * 
 */
package ru.wowhcb.sct.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wowhcb.sct.inventory.SCTContainer;

/**
 * @author drcrazy
 *
 */

@SideOnly(Side.CLIENT)
public class SCTGui extends GuiContainer {

	public static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/container/crafting_table.png");
	
	private SCTGuiSideInventory sideGUI = null;
	//private SCTContainer container;

	public SCTGui(SCTContainer container) {
		super(container);
		if (container.adjacentContainer != null) {
			//this.sideGUI = new SCTGuiSideInventory(container.adjacentContainer);
		}
		//this.container = container;
	}
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		super.setWorldAndResolution(mc, width, height);
		if (sideGUI != null) {
			sideGUI.setWorldAndResolution(mc, width, height);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BACKGROUND);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 165);
		if (sideGUI != null) {
			sideGUI.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);			
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
