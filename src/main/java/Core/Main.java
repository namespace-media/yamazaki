package Core;

import Commands.Processing.CommandHandler;
import Commands.Processing.CommandListener;
import Commands.checkRating;
import Database.*;
import Frontend.WordSender;
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

        WordsFile.InitialJsonStuff.init();

        builder = new JDABuilder(AccountType.BOT);

        builder.setStatus(OnlineStatus.IDLE);
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.playing("Learning Human behaviour."));

        builder.setToken(Config.load("token"));

        builder.addEventListeners(new Ready(), new RateMessage(), new CommandListener());

        builder.build();

        Commands();
        GatherStuff.beginCatchingWords("");


    }

    public static void Commands() {
        CommandHandler.commands.put("check", new checkRating());
        CommandHandler.commands.put("c", new checkRating());
    }

}
