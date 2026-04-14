package me.plugin.waverush;

import me.plugin.waverush.command.MACommand;
import me.plugin.waverush.listener.ArenaListener;
import me.plugin.waverush.listener.KillListener;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WaveRushPlugin extends JavaPlugin {

    private ArenaManager arenaManager;
    private SelectionManager selectionManager;

    @Override
    public void onEnable() {
        this.arenaManager = new ArenaManager();
        this.selectionManager = new SelectionManager();

        // 🔥 Listeners
        getServer().getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new KillListener(),
                this
        );

        // 🔥 Command
        if (getCommand("ma") != null) {
            getCommand("ma").setExecutor(new MACommand());
        } else {
            getLogger().severe("Command 'ma' není registrovaný v plugin.yml!");
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
