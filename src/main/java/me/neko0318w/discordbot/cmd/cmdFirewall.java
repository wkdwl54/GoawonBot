package me.neko0318w.discordbot.cmd;

import me.neko0318w.discordbot.core.CommandDescription;
import me.neko0318w.discordbot.process.ImageFirewall;
import me.neko0318w.discordbot.process.ImageUtil;

import me.neko0318w.discordbot.util.Strings;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;

import java.util.Map;

@CommandDescription(name = "Image Firewall", triggers = {"ifw"})
public class cmdFirewall extends DefaultCommand {

    @Override
    public void open() {

    }

    @Override
    public void execute(Event event, Message message, String[] args) {
        String subCmd = getArg(args, 1);
        String value = getArg(args, 2);

        switch (subCmd) {
            default:
                box(message, help());
                break;

            case "list":
                int page = Strings.to(value, 1);
                box(message, list(page));
                break;

            case "add":
                if (add(value)) {
                    send(message, "해당 이미지가 차단 목록에 추가되었습니다.");
                }
                break;

            case "remove":
            case "delete":
                if (remove(Strings.to(value, -1))) {
                    send(message, "해당 이미지가 차단 목록에서 삭제되었습니다.");
                }
                break;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean permission(Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }

    @Override
    public boolean access(Long id) {
        return id == 247012699106443273L;
    }

    @Override
    public String help() {
        return "" +
                "usage: ifw <mode> <value>\n" +
                "\n" +
                "mode:\n" +
                "  list <page>      차단 목록 보기\n" +
                "  add <hash, url>  차단 목록에 등록하기\n" +
                "  remove <index>   차단 목록에서 제거하기\n";
    }

    // FIXME: 페이징 기능을 추가 해야함.
    private String list(int page) {
        Map<Integer, String> map = ImageFirewall.map();

        if (map.isEmpty()) {
            return "Empty list.";
        } else {
            StringBuilder sb = new StringBuilder();
            map.forEach((k, v) -> {
                sb.append(String.format("%s. %s", k, v));
                sb.append("\n");
            });
            return sb.toString();
        }
    }

    private boolean add(String str) {
        if (str.startsWith("http")) {
            String hash = ImageUtil.toHash(str);
            if (hash == null) return false;
            ImageFirewall.registerImage(hash);
        } else {
            if (str.length() != 40) return false;
            ImageFirewall.registerImage(str);
        }
        return true;
    }

    private boolean remove(int index) {
        ImageFirewall.unregisterImage(index);
        return false;
    }
}