package me.hauno.bukkit.zcolor.util;

import me.hauno.bukkit.zcolor.ZColor;
import me.hauno.bukkit.zcolor.exception.ZColorException;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandUtil {

    private static HashMap<UUID, String> confirmingPlayers = new HashMap<UUID, String>();
    private static HashMap<UUID, Integer> onCooldown = new HashMap<UUID, Integer>();

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

    public static void startCooldown(Player player) {
        onCooldown.put(player.getUniqueId(), (int)(System.currentTimeMillis() / 1000));
    }

    public static boolean isOnCooldown(Player player) {
        Integer startTime = onCooldown.get(player.getUniqueId());
        int currentTime = (int)(System.currentTimeMillis() / 1000);

        if (startTime == null) return false;

        if (currentTime - startTime <= ZColor.getInstance().getPlugin().getConfig().getInt("cooldown")) {
            return true;
        }

        return false;
    }

    public static void removeCooldown(Player player) {
        onCooldown.remove(player.getUniqueId());
    }
}
