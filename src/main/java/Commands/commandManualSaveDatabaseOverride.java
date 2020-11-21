package Commands;

import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class commandManualSaveDatabaseOverride implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Preparing for Manual Save Database Override! Override ordered by: " + event.getMember().getUser().getName()).queue();
        System.out.println("[WARNING] Preparing for Manual Save Database Override! Override ordered by: " + event.getMember().getUser().getName());
        JSONWordsDatabase.InitialJsonStuff.SaveToFile();
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
