package me.plugin.waverush.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyGUI {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "§8Lobby Menu");

        inv.setItem(3, new ItemStack(Material.NETHER_STAR)); // Kit menu
        inv.setItem(5, new ItemStack(Material.CHEST)); // future (shop)

        player.openInventory(inv);
    }
}
