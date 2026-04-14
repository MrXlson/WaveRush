package me.plugin.waverush.model;

import org.bukkit.Location;

public class Arena {

    private final String name;
    private final Location pos1;
    private final Location pos2;

    public Arena(String name, Location pos1, Location pos2) {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public String getName() {
        return name;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }
}
