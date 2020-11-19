package Commands;

import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class checkWordInfos implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

    }

    private static EmbedBuilder getEmbed(String word, String tags, String ToS) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Rating for ``" + word + "``", "", false)
                .addField("❓ We are not sure yet ❓", "`" + word + "` could not yet be classified by user votes. ", false)
                .addField("", "", false)
                .setColor(Color.gray)
                .addField("All good!", "✅ = `" + word + "` is harmless", true)
                .addField("Thats a bad word!", "❌ = ``" + word + "`` is a Bad word", true)
                .setFooter(word);
//                .setFooter("We are "  + percentage + "% sure that this word is a bad one!", null)

        return eb;
    }


    //TODO Finish Info Command

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
