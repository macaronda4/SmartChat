package mdf.works.smartchat.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import mdf.works.smartchat.client.SmartchatConfigScreen;

public class SmartchatModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SmartchatConfigScreen::create;
    }
}