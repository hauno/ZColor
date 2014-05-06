package me.hauno.bukkit.zcolor.command;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.hauno.bukkit.zcolor.ZColor;
import me.hauno.bukkit.zcolor.util.FilterUtil;
import net.lordsofcode.framework.Command;
import net.lordsofcode.framework.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class NickColorCommand {

    @Command(name = "nickcolor", aliases = {"nc", "color"}, permission = "zcolor.use")
    public void handleNickColor(CommandArgs args) {
        if (!args.isPlayer()) return;

        Player player = args.getPlayer();
        Essentials essentials = ZColor.getInstance().getEssentials();
        FileConfiguration config = ZColor.getInstance().getPlugin().getConfig();

        if (args.getArgs().length != 1) {
            player.sendMessage(ZColor.getInstance().formatPluginMessage("You must supply a nickname!"));
            return;
        }

        String newName = args.getArgs()[0];
        Set<String> codesUsed = FilterUtil.getUniqueMatches(Pattern.compile("&\\w"), newName);

        if (codesUsed.size() > config.getInt("max-codes")) {
            player.sendMessage(ZColor.getInstance().formatPluginMessage(config.getString("msg.code-limit")));
            return;
        }

        if (!ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', newName)).equals(player.getName())) {
            player.sendMessage(ZColor.getInstance().formatPluginMessage(config.getString("msg.invalid-name")));
            return;
        }

        List<String> illegalCodes = config.getStringList("illegal-codes");

        for (String code : codesUsed) {
            if (illegalCodes.contains(code.replaceFirst("&", ""))) {
                player.sendMessage(ZColor.getInstance().formatPluginMessage(config.getString("msg.illegal-code")));
                return;
            }
        }

        User user = essentials.getUser(args.getPlayer());
        user.setNickname(ChatColor.translateAlternateColorCodes('&', newName));
        user.setDisplayNick();
    }

}
