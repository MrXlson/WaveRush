package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    private final ArenaManager manager;

    public ArenaListener(ArenaManager manager) {
        this.manager = manager;
    }
}
