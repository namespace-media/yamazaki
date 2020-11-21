package Commands;

import Database.Config;
import Testing.WordCrawler;
import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;

public class commandBeginCatchingWords implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            try {
                String url = WordCrawler.getWordWiki(args[0]);
                event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("✅ Manual word crawler successfully started.").setColor(Color.green).setFooter(url).build()).queue();
                WordCrawler.beginCatchingWords(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = WordCrawler.getRandomWiki();
                event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("✅ Manual word crawler successfully started.").setColor(Color.green).setFooter(url).build()).queue();
                WordCrawler.beginCatchingWords(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String Description() {
        return "Check the Rating of any Word.";
    }

    @Override
    public String Example() {
        return "check fuck";
    }

    @Override
    public String Usage() {
        return "check <word>";
    }

    @Override
    public String Permissions() {
        return null;
    }
}
