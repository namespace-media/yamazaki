package Core;

import Commands.Processing.CommandHandler;
import Commands.Processing.CommandListener;
import Commands.checkRating;
import Database.*;
import Listeners.RateMessage;
import Testing.GatherStuff;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static JDABuilder builder;

    public static void main(String[] args) throws LoginException, IOException {

        Config.init();
        SaveTypeOfSpeech.init();

        builder = new JDABuilder(AccountType.BOT);

        builder.setStatus(OnlineStatus.IDLE);
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.playing("CARRION"));

        builder.setToken(Config.load("token"));

        builder.addEventListeners(new RateMessage(), new CommandListener());

        builder.build();

        Commands();


        GatherStuff.beginCatchingWords("https://en.wikipedia.org/wiki/Anime");

    }

    public static void Commands() {
        CommandHandler.commands.put("check", new checkRating());
        CommandHandler.commands.put("c", new checkRating());
    }

}
