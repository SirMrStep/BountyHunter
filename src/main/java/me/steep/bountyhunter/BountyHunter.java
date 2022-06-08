package me.steep.bountyhunter;

import me.steep.bountyhunter.commands.BountyCommand;
import me.steep.bountyhunter.handlers.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
public class BountyHunter extends JavaPlugin {

    private static BountyHunter instance;

    @Override
    public void onEnable() {
        initialize();
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    private void initialize() {

        registerEvents();
        registerCommands();

        SQLite.loadDefaultDatabase();

    }

    private void registerEvents() {

        PluginManager pm = Bukkit.getPluginManager();

    }

    private void registerCommands() {

        getCommand("bounty").setExecutor(new BountyCommand());

    }

    public static BountyHunter getInst() {
        return instance;
    }

    public static FileConfiguration getConf() {
        return instance.getConfig();
    }

    public static void send(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
