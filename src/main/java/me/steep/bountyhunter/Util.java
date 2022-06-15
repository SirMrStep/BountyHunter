package me.steep.bountyhunter;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.steep.bountyhunter.handlers.ItemHandler;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Util {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String format_ItemListToString(List<ItemStack> r) {

        StringBuilder formatted = new StringBuilder("");

        r.forEach(item -> {

            net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = i.t() ? i.u() : new NBTTagCompound();

            formatted.append(item.getType() + "(" + item.getAmount() + ")[" + ItemHandler.getNBT(item).toString() + "],");

        });

        return formatted.toString();

    }

    public static List<ItemStack> format_StringToItemList(String formatted) {

        List<ItemStack> unformatted = new ArrayList<>();

        String[] items = formatted.split(",");

        for(int index = 0; index < formatted.split(",").length; index++) {

            String entry = items[index];

            Material material = Material.getMaterial(entry.split("\\[")[0].toUpperCase());

            NBTTagCompound nbt = null;

            try {
                nbt = MojangsonParser.a(entry.substring(entry.indexOf("[") + 1, entry.lastIndexOf("]")));
            } catch (CommandSyntaxException e) {
                throw new Error("&cError while trying to unformat bounty rewards: " + e.getMessage());
            }

            int amount = Integer.parseInt(entry.substring(entry.indexOf("(") + 1, entry.indexOf(")")));

            ItemStack item = new ItemStack(material, amount);

            ItemHandler.setNBT(item, nbt);
            unformatted.add(item);

        }

        return unformatted;

    }

}
