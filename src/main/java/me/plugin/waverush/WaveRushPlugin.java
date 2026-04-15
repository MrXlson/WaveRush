package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.*;
import me.plugin.waverush.manager.*;
import org.bukkit.plugin.java.JavaPlugin;

public class WaveRushPlugin extends JavaPlugin {

    private ArenaManager arenaManager;
    private SelectionManager selectionManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        arenaManager = new ArenaManager(this);
        selectionManager = new SelectionManager();
        kitManager = new KitManager(this);

        // Load config data
        kitManager.loadKits();

        // Commands
        getCommand("ma").setExecutor(new MACommand());

        // Listeners
        getServer().getPluginManager().registerEvents(new ArenaListener(selectionManager), this);
        getServer().getPluginManager().registerEvents(new DeathListener(arenaManager), this);

        getLogger().info("WaveRush enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush disabled!");
    }

    // 🔥 RELOAD METHOD
    public void reloadPluginConfig() {
        reloadConfig();

        kitManager.loadKits();
        // pokud budeš ukládat arény do configu:
        // arenaManager.loadArenas();
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }
}
