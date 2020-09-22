package me.neko0318w.discordbot.listener;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent e) {
        e.getJDA().getPresence().setActivity(Activity.playing("VER 0.3"));
    }
}