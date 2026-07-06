package mdf.works.smartchat.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import mdf.works.smartchat.client.SmartchatConfigScreen;

// ModMenuApi を実装するのがポイントです
public class SmartchatModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // Mod Menuから設定ボタンが押されたら、さっき作った画面を返す
        return SmartchatConfigScreen::create;
    }
}