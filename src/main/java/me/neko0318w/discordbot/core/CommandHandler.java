package me.neko0318w.discordbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class CommandHandler {
    private final Set<Class<?>> commands = new HashSet<>();

    public void registerCommand(Class<?> _class) {
        if (!_class.isInstance(Command.class)) {
            commands.add(_class);
        }
    }

    public void unregisterCommand(Class<?> _class) {
        if (_class.isInstance(Command.class)) {
            commands.remove(_class);
        }
    }

    public Command findCommand(String trigger) {
        return commands
                .stream()
                .map(CommandHandler::apply)
                .filter(Objects::nonNull)
                .filter(cmd -> Arrays.asList(cmd.getDescription().triggers()).contains(trigger))
                .findFirst()
                .orElse(null);
    }

    public void execute(Command command, MessageReceivedEvent event, String[] args) {
        try {
            if (command.access(event.getGuild().getIdLong())) {
                command.open();
                if (command.permission(event.getMember())) {
                    command.execute(event, event.getMessage(), args);
                }
            }
        } catch (Throwable e) {
            event.getChannel().sendMessage("An error has occurred.").queue();
            e.printStackTrace();
        } finally {
            command.close();
        }
    }

    private static Command apply(Class<?> _class) {
        try {
            Object object = _class.newInstance();
            return object instanceof Command ? (Command) object : null;
        } catch (Throwable e) {
            throw new RuntimeException(e.toString() + " " + e.getMessage());
        }
    }
}