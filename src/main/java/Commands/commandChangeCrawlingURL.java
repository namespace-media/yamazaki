package Commands;

import Database.Config;
import Testing.WordCrawler;
import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static Testing.WordCrawler.gatherWebsiteWords;

public class commandChangeCrawlingURL implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            String url = event.getMessage().getContentRaw().replace(Config.load("prefix") + "crawl ", "");
            try {
                System.out.println("[INFO] [COMMAND - CRAWL] URL = " + url);
                if (url.startsWith("https://") || url.startsWith("http://")) {
                    System.out.println("[INFO] New url has https://!");

                    WordCrawler.changeCatchingWordsURL(url);

                    List<String> words = gatherWebsiteWords(url);
                    int size = words.size();

                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("✅ Manual override successful. Going through " + size + " Words.").setColor(Color.green).setFooter(url).build()).queue();
                } else {
                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("✅ Manual override successful.").setColor(Color.green).setFooter(WordCrawler.changeCatchingWordsURLWiki(args[0])).build()).queue();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            event.getTextChannel().sendMessage("Please give us an URL to crawl through!").queue();
            return;
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
