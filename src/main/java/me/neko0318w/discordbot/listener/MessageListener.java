package me.neko0318w.discordbot.listener;

import me.neko0318w.discordbot.process.ImageFirewall;
import me.neko0318w.discordbot.process.ImageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.File;

public class MessageListener extends ListenerAdapter {
    private static final long EMOJI_ID = 699944895561072700L;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        e.getMessage().getAttachments().forEach(attach -> {
            if (attach.isImage()) {
                try {
                    File file = attach.downloadToFile().get();
                    String hash = ImageUtil.toHash(file);

                    if (ImageFirewall.hasImage(hash)) {
                        System.out.println("[삭제됨] " + hash);
                        e.getMessage().delete().queue();
                    }

                    file.delete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        /*
        e.getMessage().getEmotes().forEach(emote -> {
            String hash = ImageUtil.toHash(emote.hashCode());

            if (ImageFirewall.hasImage(hash)) {
                System.out.println("[삭제됨] " + hash);
                e.getMessage().delete().queue();
            }
        });
        */
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        try {
            if (e.getReactionEmote().getIdLong() == EMOJI_ID) {
                if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    e.getChannel().retrieveMessageById(e.getMessageId()).queue(message -> {
                        message.getAttachments().forEach(attach -> {
                            if (attach.isImage()) {
                                try {
                                    File file = attach.downloadToFile().get();
                                    String hash = ImageUtil.toHash(file);

                                    ImageFirewall.registerImage(hash);
                                    System.out.println("[추가됨] " + hash);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                message.delete().queue();
                            }
                        });

                        /*
                        message.getEmotes().forEach(emote -> {
                            String hash = ImageUtil.toHash(emote.hashCode());

                            if (hash != null) {
                                ImageFirewall.registerImage(hash);
                                System.out.println("[추가됨] " + hash);
                                message.delete().queue();
                            }
                        });
                        */
                    });
                }
            }
        } catch (Throwable ignored) {
        }
    }
}