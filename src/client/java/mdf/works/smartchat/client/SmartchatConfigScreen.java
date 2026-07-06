package mdf.works.smartchat.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import mdf.works.smartchat.config.ConfigManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SmartchatConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("SmartChat 設定"))
                .setSavingRunnable(ConfigManager::save);

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("一般設定"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startIntField(Component.literal("WebSocket ポート番号"), ConfigManager.INSTANCE.webSocketPort)
                .setDefaultValue(8080)
                .setSaveConsumer(newValue -> ConfigManager.INSTANCE.webSocketPort = newValue)
                .build());



        return builder.build();
    }
}