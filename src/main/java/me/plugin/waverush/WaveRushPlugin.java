package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.*;
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

        // INIT
        this.arenaManager = new ArenaManager(this);
        this.selectionManager = new SelectionManager();
        this.kitManager = new KitManager();

        // CONFIG
        saveDefaultConfig();

        // COMMAND
        getCommand("ma").setExecutor(
                new MACommand(arenaManager, selectionManager, this)
        );

        // LISTENERS
        Bukkit.getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new DeathListener(arenaManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new SignListener(arenaManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new KitSignListener(kitManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new KillListener(arenaManager), this
        );

        // TASK
        new SignUpdateTask(arenaManager).runTaskTimer(this, 20L, 20L);

        getLogger().info("WaveRush plugin zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush plugin vypnut!");
    }

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
