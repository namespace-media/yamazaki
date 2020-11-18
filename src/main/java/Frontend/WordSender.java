package Frontend;

import Database.Config;
import Tools.WordsFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class WordSender {

    public static void sendWord(ReadyEvent event, int i) {
        JSONArray nouns = WordsFile.GetRegisteredArray("noun");
        String word = nouns.get(i).toString();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription("🤔 Is ``" + word + "`` a \"bad word\"?\n\n🌟 Vote and get your reward!")
                .setColor(Color.CYAN)
                .setFooter(word, null)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setAuthor("We seek your opinion!", null, null);

        event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(eb.build()).queue();

    }

    public static void sendWord(MessageReactionAddEvent event, int i) {
        JSONArray nouns = WordsFile.GetRegisteredArray("noun");
        String word = nouns.get(i).toString();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription("🤔 Is ``" + word + "`` a \"bad word\"?\n\n🌟 Vote and get your reward!")
                .setColor(Color.CYAN)
                .setFooter(word, null)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setAuthor("We seek your opinion!", null, null)
                .build();

        event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(eb.build()).queue();

    }

}
