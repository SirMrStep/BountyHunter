package me.steep.bountyhunter.objects;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class GUIHolder implements InventoryHolder {
    @NotNull
    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 36);
    }
}
