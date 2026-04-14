package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class KitManager {

    private final WaveRushPlugin plugin;
    private final Map<UUID, String> selectedKit = new HashMap<>();

    public KitManager(WaveRushPlugin plugin) {
        this.plugin = plugin;
    }

    public void setKit(Player player, String kit) {
        selectedKit.put(player.getUniqueId(), kit);
    }

    public String getKit(Player player) {
        return selectedKit.getOrDefault(player.getUniqueId(), "warrior");
    }

    public void giveKit(Player player) {
        String kit = getKit(player);

        player.getInventory().clear();

        String path = "kits." + kit + ".items";

        if (!plugin.getConfig().contains(path)) return;

        List<Map<?, ?>> items = plugin.getConfig().getMapList(path);

        for (Map<?, ?> map : items) {
            String material = (String) map.get("material");
            int amount = (int) map.getOrDefault("amount", 1);

            player.getInventory().addItem(
                    new ItemStack(Material.valueOf(material), amount)
            );
        }
    }
}
