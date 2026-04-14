package me.plugin.waverush.model;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    public String name;
    public Location pos1;
    public Location pos2;
    public Location playerSpawn;
    public List<Location> mobSpawns = new ArrayList<>();

    public List<org.bukkit.entity.Player> players = new ArrayList<>();

    public Arena(String name) {
        this.name = name;
    }
}
