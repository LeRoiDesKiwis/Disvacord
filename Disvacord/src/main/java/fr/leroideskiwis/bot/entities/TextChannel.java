package fr.leroideskiwis.bot.entities;

import fr.leroideskiwis.bot.DiscordAPI;
import fr.leroideskiwis.bot.RequestMethod;
import org.json.JSONObject;

public class TextChannel {

    private String id;
    private DiscordAPI discordAPI;

    public TextChannel(JSONObject data, DiscordAPI discordAPI) {
        this.discordAPI = discordAPI;
        this.id = data.getString("channel_id");
    }

    public String getId() {
        return id;
    }

    public String getAsMention(){
        return "<#"+id+">";
    }

    public void sendMessage(String text){
        JSONObject content = new JSONObject().put("content", text).put("tts", false).put("embed", new JSONObject());
        discordAPI.executeApiRequest("/v6/channels/"+id+"/messages", content, RequestMethod.POST);
    }

    public DiscordAPI getDiscordAPI() {
        return discordAPI;
    }
}
