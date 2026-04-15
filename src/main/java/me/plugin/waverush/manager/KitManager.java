package me.plugin.waverush.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final Map<Player, String> selectedKit = new HashMap<>();

    // ========================
    // SET KIT
    // ========================

    public void setKit(Player player, String kit) {
        selectedKit.put(player, kit.toLowerCase());
        player.sendMessage("§aVybral sis kit: §e" + kit);
    }

    public String getKit(Player player) {
        return selectedKit.getOrDefault(player, "warrior");
    }

    // ========================
    // APPLY KIT
    // ========================

    public void applyKit(Player player) {

        player.getInventory().clear();

        String kit = getKit(player);

        switch (kit) {

            case "archer":
                player.getInventory().addItem(new ItemStack(Material.BOW));
                player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                break;

            case "tank":
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                break;

            case "mage":
                player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
                break;

            case "healer":
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
                break;

            default: // warrior
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                break;
        }
    }
}
