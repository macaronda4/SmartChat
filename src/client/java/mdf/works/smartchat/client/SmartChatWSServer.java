package mdf.works.smartchat.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;

public class SmartChatWSServer extends WebSocketServer {
    public SmartChatWSServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("[WS Server] 新しい接続を受け付けました: " + conn.getRemoteSocketAddress());
        // 接続してきた相手に挨拶を送る
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("[WS Server] 接続が切れました: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("[WS Server] メッセージを受信: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("[WS Server] エラー発生");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("[WS Server] サーバーが" + getAddress() + " で起動しました！");
    }
}
