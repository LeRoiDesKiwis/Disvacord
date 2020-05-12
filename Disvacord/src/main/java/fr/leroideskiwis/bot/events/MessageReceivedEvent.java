package fr.leroideskiwis.bot.events;

import fr.leroideskiwis.bot.DiscordAPI;
import fr.leroideskiwis.bot.entities.Message;
import org.json.JSONObject;

public class MessageReceivedEvent {

    private Message message;

    public MessageReceivedEvent(JSONObject d, DiscordAPI discordAPI) {
        this.message = new Message(d, discordAPI);
    }

    public Message getMessage() {
        return message;
    }
}
