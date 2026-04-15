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

        // 🔥 INIT (bez parametrů – FIX constructor error)
        this.arenaManager = new ArenaManager();
        this.selectionManager = new SelectionManager();
        this.kitManager = new KitManager(this);

        // 🔥 CONFIG
        saveDefaultConfig();

        // 🔥 KITY (FIX loadKits error – musí existovat prázdná metoda)
        kitManager.loadKits();

        // 🔥 COMMAND
        getCommand("ma").setExecutor(
                new MACommand(arenaManager, selectionManager, this)
        );

        // 🔥 LISTENERS (FIX constructor mismatch)
        Bukkit.getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new DeathListener(arenaManager), this
        );

        // Pokud máš SignListener → nech, jinak klidně smaž
        Bukkit.getPluginManager().registerEvents(
                new SignListener(arenaManager), this
        );

        // 🔥 TASK (už NEPROCHÁZÍ svět → safe)
        new SignUpdateTask(arenaManager).runTaskTimer(this, 20L, 20L);

        getLogger().info("WaveRush plugin byl zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush plugin byl vypnut!");
    }

    // 🔥 FIX pro /ma reload
    public void reloadPluginConfig() {
        reloadConfig();
    }

    // 🔥 GETTERY (pro další classy pokud používáš)
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
