package me.hauno.bukkit.zcolor.util;

import me.hauno.bukkit.zcolor.ZColor;
import me.hauno.bukkit.zcolor.exception.ZColorException;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandUtil {

    private static HashMap<UUID, String> confirmingPlayers = new HashMap<UUID, String>();
    private static HashMap<UUID, Integer> nickCooldown = new HashMap<UUID, Integer>();
    private static HashMap<UUID, Integer> bookCooldown = new HashMap<UUID, Integer>();

    public static void addToConfirming(Player player, String nickname) {
        confirmingPlayers.put(player.getUniqueId(), nickname);
    }

    public static String getConfirmingNick(Player player) throws ZColorException {
        String nick = confirmingPlayers.get(player.getUniqueId());

        if (nick == null) throw new ZColorException("Player wasn't confirming their name!");

        return nick;
    }

    public static void removeFromConfirming(Player player) {
        confirmingPlayers.remove(player.getUniqueId());
    }

    public static void startNickCooldown(Player player) {
        nickCooldown.put(player.getUniqueId(), (int) (System.currentTimeMillis() / 1000));
    }

    public static void startBookCooldown(Player player) {
        bookCooldown.put(player.getUniqueId(), (int) (System.currentTimeMillis() / 1000));
    }

    public static boolean isOnNickCooldown(Player player) {
        Integer startTime = nickCooldown.get(player.getUniqueId());
        int currentTime = (int)(System.currentTimeMillis() / 1000);

        if (startTime == null) return false;

        if (currentTime - startTime <= ZColor.getInstance().getPlugin().getConfig().getInt("cooldown")) {
            return true;
        }

        return false;
    }

    public static boolean isOnBookCooldown(Player player) {
        Integer startTime = bookCooldown.get(player.getUniqueId());
        int currentTime = (int)(System.currentTimeMillis() / 1000);

        if (startTime == null) return false;

        if (currentTime - startTime <= ZColor.getInstance().getPlugin().getConfig().getInt("book-cooldown")) {
            return true;
        }

        return false;
    }

    public static void removeNickCooldown(Player player) {
        nickCooldown.remove(player.getUniqueId());
    }

    public static void removeBookCooldown(Player player) {
        bookCooldown.remove(player.getUniqueId());
    }
}
