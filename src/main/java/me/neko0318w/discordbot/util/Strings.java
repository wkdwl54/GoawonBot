package me.neko0318w.discordbot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Strings {

    public static int to(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String digit(byte[] bytes, String type) {
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            byte[] result = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
