/**
 * 
 */
package ru.wowhcb.sct.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import ru.wowhcb.sct.inventory.SCTSideContainer;

/**
 * @author drcrazy
 *
 */
@SideOnly(Side.CLIENT)
public class SCTGuiSideInventory extends GuiContainer {
	public static final int NUM_COLUMNS = 5;
	private static final int MARGIN_TOP = 5;
	private static final int PADDING = 6;
	private static final int WIDTH = PADDING * 2 + NUM_COLUMNS * 18;
	private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/demo_background.png");
	private SCTSideContainer adjacentContainer;

	/**
	 * @param adjacentContainer 
	 */
	public SCTGuiSideInventory(SCTSideContainer adjacentContainer) {
		super(adjacentContainer);
		this.adjacentContainer = adjacentContainer;
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		if (adjacentContainer.numRows <= 0) { return; }
		mc.getTextureManager().bindTexture(BACKGROUND);
		drawTexturedModalRect(guiLeft - WIDTH, guiTop + MARGIN_TOP, 0, 0, WIDTH, PADDING);
		for (int i = 0; i < adjacentContainer.numRows; i++) {
			drawTexturedModalRect(guiLeft - WIDTH, guiTop + MARGIN_TOP + PADDING + i * 18, 0, 0 + PADDING, WIDTH, 18);
		}
		drawTexturedModalRect(guiLeft - WIDTH, guiTop + MARGIN_TOP + PADDING + adjacentContainer.numRows * 18, 0, 165 - PADDING, WIDTH, PADDING);
		mc.getTextureManager().bindTexture(SCTGui.BACKGROUND);
		int index = 0;
		for (int i = 0; i < adjacentContainer.numRows; i++) {
			int y = guiTop + MARGIN_TOP + PADDING + i * 18;
			for (int j=0; j < NUM_COLUMNS; j++) {
				if (index >= adjacentContainer.numSlots ) {
					break;
				}
				int x = guiLeft - WIDTH + PADDING + j * 18;
				drawTexturedModalRect(x, y, 7, 83, 18, 18);
				index++;
			}
			
		}
	}

}
