package mdf.works.smartchat.client;

import mdf.works.smartchat.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class SmartchatClient implements ClientModInitializer {
    private static SmartChatWSServer wsServer;

    @Override
    public void onInitializeClient() {

        ClientPlayConnectionEvents.JOIN.register((clientPacketListener,packetSender,minecraft) -> {
            ConfigManager.load();
            wsServer = new SmartChatWSServer(ConfigManager.INSTANCE.webSocketPort) {
                @Override
                public void onStart() {
                    super.onStart();
                    Minecraft client = Minecraft.getInstance();
                    client.execute(() -> {
                        if (client.player != null) {
                            Component message = Component.literal("https://macaronda4.github.io/SmartChat/").withStyle(style -> {
                                try {
                                    return style.withClickEvent(new ClickEvent.OpenUrl(new URI("https://macaronda4.github.io/SmartChat/")));
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            client.gui.getChat().addMessage(Component.literal("[SmartChat]: ポート "+ConfigManager.INSTANCE.webSocketPort+" でWebSocketサーバーを起動しました。"));
                            client.gui.getChat().addMessage(Component.literal("[SmartChat]: ").append(message).append(Component.literal(" をご利用ください。")));
                        }
                    });
                }

                @Override
                public void onMessage(org.java_websocket.WebSocket conn, String message) {
                    if (""  .equals(message)) return;
                    super.onMessage(conn, message);
                    Minecraft client = Minecraft.getInstance();
                    client.execute(() -> {
                        if (client.player != null) {
                            if (message.startsWith("/")) {
                                Objects.requireNonNull(client.getConnection()).sendCommand(message.substring(1));
                            }
                            else {
                                Objects.requireNonNull(client.getConnection()).sendChat(message);
                            }
                        }
                    });
                }
            };
            wsServer.start();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((clientPacketListener,minecraft) -> {
            if (wsServer != null) {
                try {
                    // ワールドを閉じる時にWebSocketサーバーも安全に終了させる
                    wsServer.stop();
                    System.out.println("[WS Server] WebSocketサーバーを停止しました。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        ReceveChatCallBack.EVENT.register((chatmsg , MessageTag) -> {
            if (wsServer != null) {
                wsServer.broadcast(MessageTag+","+chatmsg);
            }
            return InteractionResult.PASS;
        });
    }

};



