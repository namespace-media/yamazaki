package Core;

import Commands.commandBeginCatchingWords;
import Commands.commandChangeCrawlingURL;
import Commands.commandManualSaveDatabaseOverride;
import Commands.Processing.CommandHandler;
import Commands.Processing.CommandListener;
import Commands.checkRating;
import Database.*;
import Listeners.RateMessage;
import Listeners.Ready;
import Listeners.SentenceListener;
import Testing.WordCrawler;
import Tools.JSONWordsDatabase;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static JDABuilder builder;
    public static JDA mainJDA;

    public static void main(String[] args) throws LoginException, IOException, ParseException {




        Config.init();
        SaveTypeOfSpeech.init();

//        NeutralCount.init();
//        Messages.init();
//        BadCount.init();

        JSONWordsDatabase.InitialJsonStuff.init();

        builder = new JDABuilder(AccountType.BOT);

        builder.setStatus(OnlineStatus.IDLE);
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.playing("Learning Human behaviour."));

        builder.setToken(Config.load("token"));

        builder.addEventListeners(new Ready(), new RateMessage(), new CommandListener(), new SentenceListener());

        builder.build();

        Commands();
//        WordCrawler.beginCatchingWords("http://namespace.media/");
        WordCrawler.beginCatchingWords("");


    }

    public static void Commands() {
        CommandHandler.commands.put("check", new checkRating());
        CommandHandler.commands.put("c", new checkRating());
        CommandHandler.commands.put("override", new commandManualSaveDatabaseOverride());
        CommandHandler.commands.put("crawl", new commandChangeCrawlingURL());
        CommandHandler.commands.put("begin", new commandBeginCatchingWords());
    }

}
