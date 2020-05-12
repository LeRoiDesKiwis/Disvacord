package fr.leroideskiwis.bot;

public interface Client {

    /**
     * Start the bot
     * @see Gateway#connect(BotClient)
     */
    void start();

    /**
     * Add a eventlistener
     * @param listener the eventlistener to add
     */
    void addEventListener(EventListener listener);

}
