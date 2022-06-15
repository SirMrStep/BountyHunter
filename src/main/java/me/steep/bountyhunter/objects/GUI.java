package me.steep.bountyhunter.objects;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.Util;
import me.steep.bountyhunter.handlers.BountyHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
public class GUI {

    private final GUIType type;
    private UUID wanted = null;
    private Inventory inv;

    public GUI(GUIType type) {

        this.inv = Bukkit.createInventory(new GUIHolder(), 36);
        this.type = type;

    }

    public GUI(GUIType type, UUID wanted) {

        this.inv = Bukkit.createInventory(new GUIHolder(), 36);
        this.type = type;
        this.wanted = wanted;

    }

    public void show(Player p) {

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemStack filler = new ItemStack(new ItemStack(Material.BLACK_STAINED_GLASS, 1));
        ItemMeta closemeta = close.getItemMeta();
        ItemMeta fillermeta = filler.getItemMeta();

        fillermeta.setDisplayName("");

        filler.setItemMeta(fillermeta);

        for (int index = 0; index < 36; index++) {

            if (index == 31) return;

            if (index > 26 && index < 31) {

                this.inv.setItem(index, filler);
                return;

            }

            if (index > 31) {

                this.inv.setItem(index, filler);
                return;

            }

        }

        switch (this.type) {

            case BOUNTYLIST -> {

                closemeta.setDisplayName(color("&cClose"));

                // load bounty's

            }

            case REWARDSTORAGE -> {

                closemeta.setDisplayName(color("&cCancel"));

                ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
                ItemMeta meta = confirm.getItemMeta();

                meta.setDisplayName(color("&aConfirm"));
                // We will create the bounty itself after the confirm button has been clicked.
                meta.getPersistentDataContainer().set(new NamespacedKey(BountyHunter.getInst(), "wanted"), PersistentDataType.STRING, this.wanted.toString());

                confirm.setItemMeta(meta);
                this.inv.setItem(30, confirm);

            }

            case REWARDCLAIM -> {

                // instead just use the claim command to give them as much of the rewards as their inventory fits at that time and keep the rest saved

            }

        }

        close.setItemMeta(closemeta);
        this.inv.setItem(31, close);

        p.openInventory(this.inv);

    }

    public enum GUIType {

        BOUNTYLIST,
        REWARDSTORAGE,
        REWARDCLAIM

    }

    @NotNull
    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
