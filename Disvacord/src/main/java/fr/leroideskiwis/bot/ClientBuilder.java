package fr.leroideskiwis.bot;

public class ClientBuilder {

    private String token;

    public ClientBuilder setToken(String token){
        this.token = token;
        return this;
    }

    public Client build(){
        return new BotClient(token);
    }
}
