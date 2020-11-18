package Listeners;

import Tools.Tags;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SentenceListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Tags.getMutualWords(/*event.getMessage().getContentRaw()*/ "there no");
    }
}
