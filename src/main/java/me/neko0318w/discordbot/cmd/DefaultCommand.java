package me.neko0318w.discordbot.cmd;

import me.neko0318w.discordbot.core.Command;
import net.dv8tion.jda.api.entities.Message;

public abstract class DefaultCommand implements Command {

    protected static String getArg(String[] args, int index) {
        if (args.length > index) {
            return args[index];
        } else {
            return "";
        }
    }

    protected static void send(Message message, String str) {
        message.getChannel().sendMessage("> " + str).queue();
    }

    protected static void box(Message message, String str) {
        message.getChannel().sendMessage("```\n" + str + "\n```").queue();
    }
}