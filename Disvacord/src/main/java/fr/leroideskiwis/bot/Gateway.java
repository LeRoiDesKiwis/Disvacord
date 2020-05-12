package fr.leroideskiwis.bot;

import fr.leroideskiwis.bot.util.Util;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

public class Gateway {

    private DiscordAPI discordAPI;
    private String baseUrl;
    private OkHttpClient okHttpClient;

    public Gateway(DiscordAPI discordAPI, OkHttpClient okHttpClient){
        this.discordAPI = discordAPI;
        this.okHttpClient = okHttpClient;
    }

    /**
     * connect the bot to the discord api
     */
    public void connect(BotClient client){
        this.baseUrl = discordAPI.requestApi("/gateway", null, RequestMethod.GET).getString("url")+"?v=6&encoding=json";
        Util.log("Base url = "+baseUrl+"... Trying to open a websocket...");
        okHttpClient.newWebSocket(discordAPI.request(baseUrl, null, RequestMethod.GET), new WebSocketEventListener(this, client));
    }

    /**
     * @see DiscordAPI#appendToken(String, JSONObject)
    */
    public JSONObject appendToken(String key, JSONObject jsonObject){
        return discordAPI.appendToken(key, jsonObject);
    }

}
