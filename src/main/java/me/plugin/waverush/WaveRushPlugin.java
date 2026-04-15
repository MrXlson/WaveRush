package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.ArenaListener;
import me.plugin.waverush.listener.DeathListener;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.KitManager;
import me.plugin.waverush.manager.SelectionManager;
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

        // Commands
        getCommand("ma").setExecutor(new MACommand());

        // Listeners
        getServer().getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager), this);

        getServer().getPluginManager().registerEvents(
                new DeathListener(), this);

        getLogger().info("WaveRush enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush disabled!");
    }

    // 🔥 RELOAD
    public void reloadPluginConfig() {
        reloadConfig();
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
