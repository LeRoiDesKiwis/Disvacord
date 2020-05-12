package fr.leroideskiwis.bot;

import fr.leroideskiwis.bot.events.MessageReceivedEvent;
import fr.leroideskiwis.bot.util.Util;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BotClient implements Client{

    private final String BASEURL = "https://discordapp.com/api/v6";
    private String token;
    private DiscordAPI discordAPI;
    private Gateway gateway;
    private OkHttpClient okHttpClient;
    private List<EventListener> eventListeners = new ArrayList<>();

    public BotClient(String token){
        this.token = token;
        this.okHttpClient = new OkHttpClient();
        this.discordAPI = new DiscordAPI(token, okHttpClient);
        this.gateway = new Gateway(discordAPI, okHttpClient);
    }

    public void start(){
        Util.log("Connecting to gateway...");
        gateway.connect(this);
    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void trigger(JSONObject event){
        String eventName = event.getString("t");
        switch(eventName){
            case "MESSAGE_CREATE":
                MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(event.getJSONObject("d"), discordAPI);
                eventListeners.forEach(eventListener -> eventListener.onMessageReceived(messageReceivedEvent));
                break;
        }
    }
}
