package me.neko0318w.discordbot.listener;

import me.neko0318w.discordbot.cmd.cmdFirewall;
import me.neko0318w.discordbot.core.Command;
import me.neko0318w.discordbot.core.CommandHandler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class CommandListener extends ListenerAdapter {
    private static final CommandHandler handler = new CommandHandler();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String line = event.getMessage().getContentDisplay();
        String[] args = line.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        Command command = handler.findCommand(args[0]);

        if (command != null) {
            handler.execute(command, event, args);
        }
    }

    static {
        handler.registerCommand(cmdFirewall.class);
    }
}
