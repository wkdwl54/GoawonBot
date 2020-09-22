package me.neko0318w.discordbot.core;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;

public interface Command {

    void open() throws Exception;

    void execute(Event event, Message message, String[] args);

    void close();

    default boolean access(Long id) {
        if (getDescription().access().length == 0) {
            return true;
        } else {
            for (long l : getDescription().access()) {
                if (l == id) return true;
            }
        }
        return false;
    }

    default boolean permission(Member member) {
        return true;
    }

    String help();

    default CommandDescription getDescription() {
        return getClass().getAnnotation(CommandDescription.class);
    }
}