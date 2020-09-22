package me.neko0318w.discordbot.process;

import me.neko0318w.discordbot.util.Strings;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtil {

    public static String toHash(int i) {
        return Strings.digit(Integer.toHexString(i).getBytes(), "sha1");
    }

    public static String toHash(String url) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(ImageIO.read(new URL(url)), "PNG", baos);
            return Strings.digit(baos.toByteArray(), "sha1");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toHash(File file) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(ImageIO.read(file), "PNG", baos);
            return Strings.digit(baos.toByteArray(), "sha1");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
