package Frontend;

import Database.Config;
import Tools.WordsFile;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class WordSender{

    public static void sendWord(ReadyEvent event, int i) {

        try {
            JSONArray nouns = WordsFile.GetRegisteredArray("noun");

                event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(nouns.get(i).toString()).queue();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void sendWord(MessageReactionAddEvent event, int i) {

        try {
            JSONArray nouns = WordsFile.GetRegisteredArray("noun");

            event.getJDA().getTextChannelById(Config.load("channel")).sendMessage(nouns.get(i).toString()).queue();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
