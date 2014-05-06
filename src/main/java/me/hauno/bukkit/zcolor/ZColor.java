package me.hauno.bukkit.zcolor;

import com.earth2me.essentials.Essentials;
import me.hauno.bukkit.zcolor.command.ColorbookCommand;
import me.hauno.bukkit.zcolor.command.NickColorCommand;
import me.hauno.bukkit.zcolor.command.ZColorCommand;
import net.lordsofcode.framework.CommandFramework;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.logging.Logger;

public class ZColor {

    private static ZColor instance;
    private ZColorPlugin plugin;

    private CommandFramework commandFramework;

    public ZColor(ZColorPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        this.commandFramework = new CommandFramework(this.plugin);
    }

    public void initialize() {
        this.checkEssentials();
        this.registerCommands();
        this.plugin.saveDefaultConfig();

        this.plugin.getServer().getPluginManager().addPermission(new Permission("zcolor.bypass", PermissionDefault.OP));
    }

    public void shutdown() {
        //Nothing for now.
    }

    private void registerCommands() {
        this.commandFramework.registerCommands(new NickColorCommand());
        this.commandFramework.registerCommands(new ColorbookCommand());
        this.commandFramework.registerCommands(new ZColorCommand());
        this.commandFramework.registerHelp();
    }

    private void checkEssentials() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Essentials") == null) {
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }
    }

    public String formatPluginMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return String.format("%s %s", ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("prefix")), message);
    }

    public Essentials getEssentials() {
        Essentials essentials = (Essentials)this.plugin.getServer().getPluginManager().getPlugin("Essentials");
        return essentials;
    }

    public CommandFramework getCommandFramework() {
        return this.commandFramework;
    }

    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    public ZColorPlugin getPlugin() {
        return this.plugin;
    }

    public static ZColor getInstance() {
        return instance;
    }
}
