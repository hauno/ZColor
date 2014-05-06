package me.hauno.bukkit.zcolor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ZColorPlugin extends JavaPlugin {

    private ZColor bootstrap;

    @Override
    public void onEnable() {
        this.bootstrap = new ZColor(this);
        this.bootstrap.initialize();
    }

    @Override
    public void onDisable() {
        this.bootstrap.shutdown();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, java.lang.String[] args) {
        return this.bootstrap.getCommandFramework().handleCommand(sender, label, command, args);
    }

    public ZColor getBootstrap() {
        return this.bootstrap;
    }

}
