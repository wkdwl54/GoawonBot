import me.neko0318w.discordbot.process.ImageUtil;
import org.junit.Test;

import java.io.File;

public class HashGenerator {

    @Test
    public void run() {
        File file = new File("");
        System.out.println(ImageUtil.toHash(file));
    }
}
