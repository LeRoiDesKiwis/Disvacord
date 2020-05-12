package fr.leroideskiwis.bot.entities;

import org.json.JSONObject;

public class User {

    private String id;
    private String name;

    public User(JSONObject data) {
        this.id = data.getString("id");
        this.name = data.getString("username");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getAsMention(){
        return "<@"+id+">";
    }
}
