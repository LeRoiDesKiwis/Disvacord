package fr.leroideskiwis.bot.entities;

import fr.leroideskiwis.bot.DiscordAPI;
import org.json.JSONObject;

public class Message {

    private String content;
    private User author;
    private TextChannel textChannel;

    public Message(JSONObject data, DiscordAPI discordAPI) {
        this.content = data.getString("content");
        this.author = new User(data.getJSONObject("author"));
        this.textChannel = new TextChannel(data, discordAPI);
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }
}
