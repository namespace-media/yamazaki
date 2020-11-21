package Listeners;

import Database.*;
import Frontend.WordSender;
import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RateMessage extends ListenerAdapter {

    public static String ToS;

    /*@Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] words = event.getMessage().getContentRaw().split(" ");

        for (int i = 0; i < words.length; i++) {
            if(!SaveTypeOfSpeech.propExist(words[i]) && words[i].length() > 0) {
                SaveTypeOfSpeech.save(words[i], "0");
            }
        }

        if(event.getTextChannel().getId().contains(Config.load("channel"))) {
            event.getMessage().addReaction("✅").and(event.getMessage().addReaction("❌")).queue();
            wordCount++;
        }

        Messages.save(event.getMessageId(), event.getMessage().getContentRaw());
    }*/

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getEmbeds().size() != 1 || event.getMember().getUser() != event.getJDA().getSelfUser())
            return;

        String[] words = event.getMessage().getEmbeds().get(0).getFooter().getText().split(" ");

        for (int i = 0; i < words.length; i++) {
            if(!SaveTypeOfSpeech.propExist(words[i]) && words[i].length() > 0) {
                SaveTypeOfSpeech.save(words[i], "0");
            }
        }

//        if(event.getTextChannel().getId().contains(Config.load("channel"))) {
//        }
        event.getMessage().addReaction("✅").and(event.getMessage().addReaction("❌")).queue();
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if(!event.getMember().getUser().isBot() || event.retrieveMessage().complete().getEmbeds().size() != 1)
            if (event.getReactionEmote().getName().contains("❌")) {

                String[] voted = event.retrieveMessage().complete().getEmbeds().get(0).getFooter().getText().split(" ");

                for (int i = 0; i < voted.length; i++) {
//                    NeutralCount.save(upvoted[i], Rating.addNeutralScore(upvoted[i]));
                    JSONWordsDatabase.WordDetails.WordRating.RateWord(voted[i].toLowerCase(), false);
                }

//                WordSender.sendWord(event);
            } else if (event.getReactionEmote().getName().contains("✅")) {

                String[] voted = event.retrieveMessage().complete().getEmbeds().get(0).getFooter().getText().split(" ");

                for (int i = 0; i < voted.length; i++) {
//                    BadCount.save(downvoted[i], Rating.addBadScore(downvoted[i]));
                    JSONWordsDatabase.WordDetails.WordRating.RateWord(voted[i].toLowerCase(), true);
                }

//                WordSender.sendWord(event);
            }
    }
}
