package me.plugin.waverush.manager;

import me.plugin.waverush.model.Selection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionManager {

    private final Map<UUID, Selection> selections = new HashMap<>();

    public Selection get(UUID uuid) {
        return selections.computeIfAbsent(uuid, k -> new Selection());
    }
}
