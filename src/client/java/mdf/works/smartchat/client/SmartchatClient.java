package mdf.works.smartchat.client;

import mdf.works.smartchat.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

import java.util.Objects;

public class SmartchatClient implements ClientModInitializer {
    private static SmartChatWSServer wsServer;

    @Override
    public void onInitializeClient() {

        ClientPlayConnectionEvents.JOIN.register((clientPacketListener,packetSender,minecraft) -> {
            ConfigManager.load();
            wsServer = new SmartChatWSServer(ConfigManager.INSTANCE.webSocketPort) {
                @Override
                public void onMessage(org.java_websocket.WebSocket conn, String message) {
                    if (""  .equals(message)) return;
                    super.onMessage(conn, message);
                    Minecraft client = Minecraft.getInstance();
                    client.execute(() -> {
                        if (client.player != null) {
                            Objects.requireNonNull(client.getConnection()).sendChat(message);
                        }
                    });
                }
            };
            wsServer.start();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((clientPacketListener,minecraft) -> {
            if (wsServer != null) {
                try {
                    wsServer.stop();
                    System.out.println("[WS Server] WebSocketサーバーを停止しました。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        ReceveChatCallBack.EVENT.register((chatmsg, MessageTag) -> {
            if (wsServer != null) {
                wsServer.broadcast(MessageTag+","+chatmsg);
            }
            return InteractionResult.PASS;
        });
    }

};



