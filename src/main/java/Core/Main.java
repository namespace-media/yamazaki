package Core;

import Commands.Processing.CommandHandler;
import Commands.Processing.CommandListener;
import Commands.checkRating;
import Database.*;
import Listeners.RateMessage;
import Listeners.Ready;
import Testing.GatherStuff;
import Tools.WordsFile;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static JDABuilder builder;

    public static void main(String[] args) throws LoginException, IOException, ParseException {

        Config.init();
        SaveTypeOfSpeech.init();

        BadCount.init();
        NeutralCount.init();
        Messages.init();

        builder = new JDABuilder(AccountType.BOT);

        builder.setStatus(OnlineStatus.IDLE);
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.playing("encrypt."));

        builder.setToken(Config.load("token"));

        builder.addEventListeners(new RateMessage(), new CommandListener(), new Ready());

        builder.build();

        Commands();

        WordsFile.InitialJsonStuff.init();
//        System.out.println(WordsFile.WordRegistered("verb", "msg 3"));
//        WordsFile.RegisterWord("BigTOS", "hihiws");

        GatherStuff.beginCatchingWords("https://github.com/stleary/JSON-java");


    }

    public static void Commands() {
        CommandHandler.commands.put("check", new checkRating());
        CommandHandler.commands.put("c", new checkRating());
    }

}
