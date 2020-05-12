package fr.leroideskiwis.client;

import fr.leroideskiwis.bot.Client;
import fr.leroideskiwis.bot.ClientBuilder;
import fr.leroideskiwis.client.listeners.Listener;

public class Main {

    public static void main(String... args){
        Client client = new ClientBuilder().setToken(Vars.token).build();
        client.addEventListener(new Listener());
        client.start();
    }

}
