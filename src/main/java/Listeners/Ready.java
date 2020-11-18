package Listeners;

import Core.Main;
import Frontend.WordSender;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Main.mainJDA = event.getJDA();
//        WordSender.sendWord(event);
    }
}
