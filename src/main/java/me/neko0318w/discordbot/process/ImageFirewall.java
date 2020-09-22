package me.neko0318w.discordbot.process;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class ImageFirewall {

    private static final HashMap<Integer, String> blacklist = new HashMap<>();

    synchronized public static void registerImage(String hash) {
        blacklist.put(blacklist.size(), hash);
    }

    synchronized public static void unregisterImage(int index) {
        blacklist.remove(index);
    }

    public static Map<Integer, String> map() {
        return new HashMap<>(blacklist);
    }

    public static boolean hasImage(String hash) {
        return blacklist.containsValue(hash);
    }

    public static void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("db.json"))) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            object.forEach((k, v) -> blacklist.put(Integer.parseInt((String) k), (String) v));
        } catch (IOException | ParseException ignored) {
            // pass
        }
    }

    public static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db.json"))) {
            writer.write(JSONObject.toJSONString(blacklist));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
