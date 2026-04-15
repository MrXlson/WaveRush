package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.ArenaListener;
import me.plugin.waverush.listener.DeathListener;
import me.plugin.waverush.listener.SignListener;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.KitManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.task.SignUpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WaveRushPlugin extends JavaPlugin {

    private ArenaManager arenaManager;
    private SelectionManager selectionManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {

        // 🔥 INIT
        this.arenaManager = new ArenaManager();
        this.selectionManager = new SelectionManager();
        this.kitManager = new KitManager(); // ✅ FIX

        // 🔥 CONFIG
        saveDefaultConfig();

        // 🔥 KITY
        kitManager.loadKits();

        // 🔥 COMMAND
        getCommand("ma").setExecutor(
                new MACommand(arenaManager, selectionManager, this)
        );

        // 🔥 LISTENERS
        Bukkit.getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new DeathListener(arenaManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new SignListener(arenaManager), this
        );

        // 🔥 TASK (1s update cedulek)
        new SignUpdateTask(arenaManager).runTaskTimer(this, 20L, 20L);

        getLogger().info("WaveRush plugin byl zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush plugin byl vypnut!");
    }

    // 🔥 /ma reload
    public void reloadPluginConfig() {
        reloadConfig();
    }

    // 🔥 GETTERY
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
