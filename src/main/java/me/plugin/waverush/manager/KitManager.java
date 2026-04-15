package me.plugin.waverush.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final Map<Player, String> selectedKit = new HashMap<>();

    // ========================
    // LOAD KITS (fix error)
    // ========================
    public void loadKits() {
        // zatím prázdné (můžeš napojit na config později)
    }

    // ========================
    // SET KIT
    // ========================
    public void setKit(Player player, String kit) {
        selectedKit.put(player, kit);
    }

    // ========================
    // GET KIT
    // ========================
    public String getKit(Player player) {
        return selectedKit.getOrDefault(player, "none");
    }

    // ========================
    // APPLY KIT
    // ========================
    public void applyKit(Player player) {
        String kit = getKit(player);

        player.getInventory().clear();

        switch (kit.toLowerCase()) {

            case "warrior":
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                player.getInventory().addItem(new ItemStack(Material.SHIELD));
                break;

            case "archer":
                player.getInventory().addItem(new ItemStack(Material.BOW));
                player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                break;

            case "mage":
                player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
                break;

            default:
                // žádný kit
                break;
        }
    }

    // ========================
    // HAS KIT
    // ========================
    public boolean hasKit(Player player) {
        return selectedKit.containsKey(player) && !selectedKit.get(player).equalsIgnoreCase("none");
    }

    // ========================
    // REMOVE (optional)
    // ========================
    public void remove(Player player) {
        selectedKit.remove(player);
    }
}
