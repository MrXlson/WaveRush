package me.plugin.waverush;

import me.plugin.waverush.command.ArenaCommand;
import me.plugin.waverush.listener.ArenaListener;
import me.plugin.waverush.listener.SelectionListener;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WaveRushPlugin extends JavaPlugin {

    private static WaveRushPlugin instance;

    private ArenaManager arenaManager;
    private SelectionManager selectionManager;

    @Override
    public void onEnable() {
        instance = this;

        arenaManager = new ArenaManager();
        selectionManager = new SelectionManager();

        getCommand("ma").setExecutor(new ArenaCommand(arenaManager, selectionManager));

        getServer().getPluginManager().registerEvents(new SelectionListener(selectionManager), this);
        getServer().getPluginManager().registerEvents(new ArenaListener(arenaManager), this);

        getLogger().info("WaveRush loaded!");
    }

    public static WaveRushPlugin get() {
        return instance;
    }
}
