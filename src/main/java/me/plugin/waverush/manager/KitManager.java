package me.plugin.waverush.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KitManager {

    private final Map<UUID, String> selectedKit = new HashMap<>();

    public void setKit(Player player, String kit) {
        selectedKit.put(player.getUniqueId(), kit);
    }

    public String getKit(Player player) {
        return selectedKit.getOrDefault(player.getUniqueId(), "warrior");
    }

    public void giveKit(Player player) {
        String kit = getKit(player);

        player.getInventory().clear();

        if (kit.equalsIgnoreCase("warrior")) {
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
        }

        if (kit.equalsIgnoreCase("archer")) {
            player.getInventory().addItem(new ItemStack(Material.BOW));
            player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
        }

        if (kit.equalsIgnoreCase("tank")) {
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        }
    }
}
