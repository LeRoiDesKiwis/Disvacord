package fr.leroideskiwis.client.listeners;

import fr.leroideskiwis.bot.EventListener;
import fr.leroideskiwis.bot.events.MessageReceivedEvent;

public class Listener extends EventListener {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getAuthor().getId().equals("709726939254095892")) return;
        event.getMessage().getTextChannel().sendMessage("Hey "+event.getMessage().getAuthor().getAsMention()+" ! Tu parles actuellement dans le "+event.getMessage().getTextChannel().getAsMention()+" et tu as dit "+event.getMessage().getContent()+".");
    }
}
