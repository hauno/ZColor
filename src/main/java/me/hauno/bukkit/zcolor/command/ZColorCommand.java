package me.hauno.bukkit.zcolor.command;

import me.hauno.bukkit.zcolor.ZColor;
import net.lordsofcode.framework.Command;
import net.lordsofcode.framework.CommandArgs;

public class ZColorCommand {

    @Command(name = "zcolor", aliases = {"zc"}, permission = "zcolor.info")
    public void handleZC(CommandArgs args) {
        args.getSender().sendMessage(ZColor.getInstance().formatPluginMessage(String.format("ZColor v%s", ZColor.getInstance().getPlugin().getDescription().getVersion())));
    }

    @Command(name = "zcolor.reload", aliases = "zc.reload", permission = "zcolor.reload")
    public void handleReload(CommandArgs args) {
        ZColor.getInstance().getPlugin().reloadConfig();
        args.getSender().sendMessage(ZColor.getInstance().formatPluginMessage("ZColor config reloaded."));
    }

}
