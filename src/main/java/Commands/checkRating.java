package Commands;

import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class checkRating implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            String word = args[0];


            double rating = JSONWordsDatabase.WordDetails.WordRating.GetWordsRating(word);
            System.out.println("rating - " + rating);
            if (rating < 0  || rating > 100 || rating == 50) {
                event.getTextChannel().sendMessage(getNotSureEmbed(word).build()).queue();
            } else if (rating > 50) {
                event.getTextChannel().sendMessage(getBadEmbed(word, (int) rating).build()).queue();
            } else {
                event.getTextChannel().sendMessage(getGoodEmbed(word, (int) rating).build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage("Please include a Word!").queue();
        }
    }

    private static EmbedBuilder getNotSureEmbed(String word) {
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

    private static EmbedBuilder getBadEmbed(String word, int percentage) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Rating for ``" + word + "``", "Confidence: " + percentage + "%", false)
                .addField("❌ Bad Word ❌", "`" + word + "` was classified as a bad word by user votes! ", false)
                .setFooter("Confidence: " + percentage + "%", null)
                .addBlankField(false)
                .setColor(Color.red)
                .addField("❌ Bad Word ❌", "`" + word + "` was classified as a bad word by user votes! ", false)
                .setAuthor("The users have decided!", null, null)
                .addField("All good!", "✅ = `" + word + "` is harmless", true)
                .addField("Thats a bad word!", "❌ = ``" + word + "`` is a Bad word", true)
                .setFooter(word);

        return eb;
    }

    private static EmbedBuilder getGoodEmbed(String word, int percentage) {
        EmbedBuilder eb = new EmbedBuilder();
        eb
                .addField("Rating for ``" + word + "``", "Confidence: " + (100-percentage) + "%", false)
                .addField("✅ Fine Word ✅", "`" + word + "` was classified as a harmless word by user votes!", false)
                .addBlankField(false)
                .setColor(Color.green)
                .setAuthor("The users have decided!", null, null)
                .addField("All good!", "✅ = `" + word + "` is harmless", true)
                .addField("Thats a bad word!", "❌ = ``" + word + "`` is a Bad word", true)
                .setFooter(word);

        return eb;
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
