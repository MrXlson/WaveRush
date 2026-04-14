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

        switch (kit.toLowerCase()) {

            case "warrior":
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                player.getInventory().addItem(new ItemStack(Material.SHIELD));
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
                break;

            case "archer":
                player.getInventory().addItem(new ItemStack(Material.BOW));
                player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 8));
                break;

            case "tank":
                player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                break;

            case "mage":
                player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
                break;

            case "speed":
                player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                player.getInventory().addItem(new ItemStack(Material.SUGAR, 5));
                break;

            default:
                player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
                break;
        }
    }
}
