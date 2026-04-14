package me.plugin.waverush;

import me.plugin.waverush.listener.ArenaListener;
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

        // registrace listeneru
        getServer().getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager),
                this
        );

        // registrace commandu /ma
        if (getCommand("ma") != null) {
            getCommand("ma").setExecutor(new me.plugin.waverush.command.MACommand());
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
