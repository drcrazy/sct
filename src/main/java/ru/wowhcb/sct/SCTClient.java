package ru.wowhcb.sct;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import ru.wowhcb.sct.client.WorkbenchScreen;

public class SCTClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(SCT.SMART_WORKBENCH_SCREEN_HANDLER, WorkbenchScreen::new);
    }
}
