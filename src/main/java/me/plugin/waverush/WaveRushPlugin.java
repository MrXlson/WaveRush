package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.ArenaListener;
import me.plugin.waverush.listener.DeathListener;
import me.plugin.waverush.listener.KillListener;
import me.plugin.waverush.listener.KitListener;
import me.plugin.waverush.listener.LobbyListener;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WaveRushPlugin extends JavaPlugin {

    private ArenaManager arenaManager;
    private SelectionManager selectionManager;

    @Override
    public void onEnable() {

        // 🔥 načtení configu
        saveDefaultConfig();

        // 🔧 manažeři
        this.arenaManager = new ArenaManager(this);
        this.selectionManager = new SelectionManager();

        // 📡 základní listenery
        getServer().getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager), this);

        getServer().getPluginManager().registerEvents(
                new KillListener(), this);

        getServer().getPluginManager().registerEvents(
                new DeathListener(), this);

        // 🎮 GUI listenery
        getServer().getPluginManager().registerEvents(
                new KitListener(arenaManager.getKitManager()), this);

        getServer().getPluginManager().registerEvents(
                new LobbyListener(), this);

        // ⚡ command (/ma)
        if (getCommand("ma") != null) {
            getCommand("ma").setExecutor(new MACommand());
        }

        getLogger().info("WaveRush zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WaveRush vypnut!");
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }
}
