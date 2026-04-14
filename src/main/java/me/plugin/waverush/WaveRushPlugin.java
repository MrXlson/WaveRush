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

        getServer().getPluginManager().registerEvents(
                new ArenaListener(arenaManager, selectionManager),
                this
        );

        getLogger().info("WaveRush zapnut!");
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
