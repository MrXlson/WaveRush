package me.plugin.waverush.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitGUI {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "§8Výběr Kitu");

        inv.setItem(2, createItem(Material.IRON_SWORD, "§aWarrior"));
        inv.setItem(4, createItem(Material.BOW, "§aArcher"));
        inv.setItem(6, createItem(Material.BLAZE_ROD, "§aMage"));

        player.openInventory(inv);
    }

    private static ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
