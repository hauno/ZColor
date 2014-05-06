package me.hauno.bukkit.zcolor.command;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.hauno.bukkit.zcolor.ZColor;
import me.hauno.bukkit.zcolor.exception.ZColorException;
import me.hauno.bukkit.zcolor.util.CommandUtil;
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

        for (int i = 0; i < illegalCodes.size(); i++) {
            String code = illegalCodes.get(i);
            illegalCodes.set(i, code.toUpperCase());
        }

        for (String code : codesUsed) {
            if (illegalCodes.contains(code.replaceFirst("&", "").toUpperCase())) {
                player.sendMessage(ZColor.getInstance().formatPluginMessage(config.getString("msg.illegal-code")));
                return;
            }
        }

        newName = ChatColor.translateAlternateColorCodes('&', newName);

        player.sendMessage(ZColor.getInstance().formatPluginMessage(String.format("How your name will look: %s", newName)));
        player.sendMessage(ZColor.getInstance().formatPluginMessage("Type &4/"+args.getCommand().getName()+" confirm&r to confirm your nickname."));
        player.sendMessage(ZColor.getInstance().formatPluginMessage("Type &4/"+args.getCommand().getName()+" cancel&r to stop changing your nickname."));

        CommandUtil.addToConfirming(player, newName);
    }

    @Command(name = "nickcolor.confirm", aliases = {"nc.confirm", "color.confirm"}, permission = "zcolor.use")
    public void handleConfirm(CommandArgs args) {
        if (!args.isPlayer()) return;

        Player player = args.getPlayer();
        Essentials essentials = ZColor.getInstance().getEssentials();

        if (CommandUtil.isOnCooldown(player) && !player.hasPermission("zcolor.bypass")) {
            player.sendMessage(ZColor.getInstance().formatPluginMessage("You cannot change your nickname again so soon!"));
            return;
        }

        try {
            String nickName = CommandUtil.getConfirmingNick(player);
            User user = essentials.getUser(args.getPlayer());
            user.setNickname(nickName);
            user.setDisplayNick();
            CommandUtil.removeFromConfirming(player);
            CommandUtil.startCooldown(player);
            player.sendMessage(ZColor.getInstance().formatPluginMessage("Your nickname has been changed."));
        } catch (ZColorException e) {
            //Ignore, player wasn't confirming.
        }
    }

    @Command(name = "nickcolor.cancel", aliases = {"nc.cancel", "color.cancel"}, permission = "zcolor.use")
    public void handleCancel(CommandArgs args) {
        if (!args.isPlayer()) return;

        Player player = args.getPlayer();

        try {
            String nickName = CommandUtil.getConfirmingNick(player);
            CommandUtil.removeFromConfirming(player);
            player.sendMessage(ZColor.getInstance().formatPluginMessage("You stopped changing your nickname."));
        } catch(ZColorException e) {
            //Ignore, player wasn't confirming.
        }
    }
}
