package Listeners;

import Database.*;
import Tools.Rating;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class RateMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] words = event.getMessage().getContentRaw().split(" ");

        for (int i = 0; i < words.length; i++) {
            if(!SaveTypeOfSpeech.propExist(words[i]) && words[i].length() > 0) {
                SaveTypeOfSpeech.save(words[i], "0");
            }
        }

        if(event.getTextChannel().getId().contains(Config.load("channel"))) {
            event.getMessage().addReaction("✅").and(event.getMessage().addReaction("❌")).queue();
        }

        Messages.save(event.getMessageId(), event.getMessage().getContentRaw());
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        if(!event.getMember().getUser().isBot()) {
            if (event.getReactionEmote().getName().contains("✅")) {

                String[] upvoted = Messages.load(event.getMessageId()).split(" ");

                for (int i = 0; i < upvoted.length; i++) {
                    NeutralCount.save(upvoted[i], Rating.addNeutralScore(upvoted[i]));
                }

            } else if (event.getReactionEmote().getName().contains("❌")) {

                String[] downvoted = Messages.load(event.getMessageId()).split(" ");

                for (int i = 0; i < downvoted.length; i++) {
                    BadCount.save(downvoted[i], Rating.addBadScore(downvoted[i]));
                }

            }
        }

    }
}
