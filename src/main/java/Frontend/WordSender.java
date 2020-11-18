package Frontend;

import Core.Main;
import Database.Config;
import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.json.simple.JSONArray;

import java.awt.*;
import java.util.Random;

public class WordSender {

    public static void sendWord(ReadyEvent event) throws java.lang.StackOverflowError {
        String word = getRandomWord();
        if (word == null){
                sendWord(event);
                return;
        }

        event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(getEmbed(word).build()).queue();

    }

    public static void sendWord(MessageReactionAddEvent event) {
        String word = getRandomWord();
        if (word == null){
            sendWord(event);
            return;
        }
        event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(getEmbed(word).build()).queue();

    }

    private static EmbedBuilder getEmbed(String word) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription("🤔 Is ``" + word + "`` a \"bad word\"?\n\n🌟 Vote and get your reward!")
                .addField("All good!", "✅ = `" + word + "` is harmless", true)
                .addField("Thats a bad word!", "❌ = ``" + word + "`` is a Bad word", true)
                .setColor(Color.CYAN)
                .setFooter(word, null)
                .setThumbnail(Main.mainJDA.getSelfUser().getAvatarUrl())
                .setAuthor("We seek your opinion!", null, null)
                .build();

        return eb;
    }

    private static String getRandomWord() throws java.lang.StackOverflowError {
        JSONArray words;

        switch (new Random().nextInt(4)) {
            case 0:
                words = JSONWordsDatabase.WordRegister.GetRegisteredArray("noun");
                break;
            case 1:
                words = JSONWordsDatabase.WordRegister.GetRegisteredArray("verb");
                break;
            case 2:
                words = JSONWordsDatabase.WordRegister.GetRegisteredArray("adjective");
                break;
            case 3:
                words = JSONWordsDatabase.WordRegister.GetRegisteredArray("adverb");
                break;
            default:
                words = JSONWordsDatabase.WordRegister.GetRegisteredArray("unspecified");
                break;
        }

        if (words == null)
            return null;


        return words.get(new Random().nextInt(words.size())).toString();
    }

}
