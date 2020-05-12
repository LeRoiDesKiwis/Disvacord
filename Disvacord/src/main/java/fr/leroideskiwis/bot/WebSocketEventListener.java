package fr.leroideskiwis.bot;

import fr.leroideskiwis.bot.util.Util;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class WebSocketEventListener extends WebSocketListener {

    private Gateway gateway;
    private boolean isClosed;
    private int lastSequence;
    private BotClient client;

    public WebSocketEventListener(Gateway gateway, BotClient client){
        this.gateway = gateway;
        this.client = client;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Util.log("Websocket is now closed !");
        this.isClosed = true;
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        t.printStackTrace();
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        Util.log("Message received : "+text);
        JSONObject message = new JSONObject(text);
        switch(message.getInt("op")){
            case 0:
                this.lastSequence = message.getInt("s");
                client.trigger(message);
                break;
            case 10:
                try {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (isClosed) {
                                cancel();
                                return;
                            }
                            send(webSocket, 1, lastSequence);
                            Util.log("heartbeat sended");
                        }
                    };

                    int heartbeatInterval = message.getJSONObject("d").getInt("heartbeat_interval");
                    new Timer().schedule(task, heartbeatInterval);
                    JSONObject properties = new JSONObject().put("$os", "Windows").put("$browser", "java").put("$device", "java");
                    send(webSocket, 2, gateway.appendToken("token", new JSONObject().put("properties", properties)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        this.isClosed = false;
        Util.log("Connected to websocket !");
    }

    public boolean send(WebSocket webSocket, int op, Object data){
        String s = new JSONObject().put("op", op).put("d", data).toString();
        Util.log("Paquet envoyé : "+s);
        return webSocket.send(s);
    }
}
