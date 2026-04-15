package me.plugin.waverush.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {

    private final Map<String, ItemStack[]> kits = new HashMap<>();
    private final Map<Player, String> selectedKit = new HashMap<>();

    private final FileConfiguration config;

    public KitManager(FileConfiguration config) {
        this.config = config;
    }

    // ========================
    // LOAD KITS (FIX ERROR)
    // ========================

    public void loadKits() {
        kits.clear();

        if (!config.contains("kits")) {
            Bukkit.getLogger().warning("[WaveRush] No kits found in config!");
            return;
        }

        for (String kitName : config.getConfigurationSection("kits").getKeys(false)) {

            List<String> items = config.getStringList("kits." + kitName + ".items");

            ItemStack[] kitItems = new ItemStack[items.size()];

            for (int i = 0; i < items.size(); i++) {
                String matName = items.get(i);

                try {
                    Material mat = Material.valueOf(matName.toUpperCase());
                    kitItems[i] = new ItemStack(mat);
                } catch (Exception e) {
                    Bukkit.getLogger().warning("[WaveRush] Invalid material in kit " + kitName + ": " + matName);
                }
            }

            kits.put(kitName.toLowerCase(), kitItems);
        }

        Bukkit.getLogger().info("[WaveRush] Kits loaded: " + kits.size());
    }

    // ========================
    // GIVE KIT
    // ========================

    public void giveKit(Player player) {
        String kitName = selectedKit.get(player);

        if (kitName == null) {
            player.sendMessage("§cNemáš vybraný kit!");
            return;
        }

        ItemStack[] items = kits.get(kitName.toLowerCase());

        if (items == null) {
            player.sendMessage("§cKit neexistuje!");
            return;
        }

        player.getInventory().clear();

        for (ItemStack item : items) {
            if (item != null) {
                player.getInventory().addItem(item);
            }
        }
    }

    // ========================
    // SELECT KIT
    // ========================

    public void selectKit(Player player, String kitName) {
        if (!kits.containsKey(kitName.toLowerCase())) {
            player.sendMessage("§cTento kit neexistuje!");
            return;
        }

        selectedKit.put(player, kitName.toLowerCase());
        player.sendMessage("§aVybral jsi kit: §e" + kitName);
    }

    public boolean hasKit(Player player) {
        return selectedKit.containsKey(player);
    }

    // ========================
    // GETTERS
    // ========================

    public Map<String, ItemStack[]> getKits() {
        return kits;
    }
}
