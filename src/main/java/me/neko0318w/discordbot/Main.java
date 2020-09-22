package me.neko0318w.discordbot;

import me.neko0318w.discordbot.process.ImageFirewall;
import me.neko0318w.discordbot.listener.CommandListener;
import me.neko0318w.discordbot.listener.MessageListener;
import me.neko0318w.discordbot.listener.ReadyListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command
public class Main implements Callable<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Option(names = {"-t", "--token"}, required = true)
    public static String token;

    @Override
    public Integer call() throws Exception {
        return null;
    }

    public static void main(String[] args) throws Exception {
        // parse command line
        new CommandLine(Main.class).execute(args);

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setAutoReconnect(true);

        // added listeners
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new MessageListener());

        JDA jda = builder.build();
        jda.awaitReady();

        LOG.info("Started..");
        LOG.info("Token = " + token);

        ImageFirewall.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::stop));
    }

    public static void stop() {
        ImageFirewall.save();
        LOG.info("Stopped..");
    }
}
