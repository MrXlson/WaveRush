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
    private KitManager kitManager;
    private SelectionManager selectionManager;

    @Override
    public void onEnable() {

        // 🔥 INIT
        this.arenaManager = new ArenaManager();
        this.kitManager = new KitManager(this);
        this.selectionManager = new SelectionManager();

        // 🔥 CONFIG
        saveDefaultConfig();

        // 🔥 LOAD KITS
        kitManager.loadKits();

        // 🔥 COMMAND
        getCommand("ma").setExecutor(new MACommand(arenaManager, selectionManager, this));

        // 🔥 LISTENERS
        Bukkit.getPluginManager().registerEvents(new ArenaListener(arenaManager), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(arenaManager), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(arenaManager), this);

        // 🔥 TASKS (cedulky update každou sekundu)
        new SignUpdateTask(arenaManager).runTaskTimer(this, 20L, 20L);

        getLogger().info("WaveRush plugin byl zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush plugin byl vypnut!");
    }

    // 🔥 GETTERY (pokud někde používáš)
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }
}
