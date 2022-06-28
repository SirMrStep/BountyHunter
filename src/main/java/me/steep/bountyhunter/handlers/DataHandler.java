package me.steep.bountyhunter.handlers;

import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.morepersistentdatatypes.DataType;
import org.jetbrains.annotations.Nullable;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Uses Maven Dependancies inside this pom.xml: MorePersistentDataTypes, CustomBlockData
 */
@SuppressWarnings("all")
public class DataHandler {

    private static Plugin instance;

    public static void register(Plugin inst) {
        instance = inst;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param name The name to put on the ItemStack
     * @param lore The lore to put on the ItemStack
     */
    public static ItemStack setNameAndLore(ItemStack itemStack, String name, String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param name The name to put on the ItemStack
     * @param lore The lore to put on the ItemStack
     */
    public static ItemStack setNameAndLore(ItemStack itemStack, String name, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param lore The lore to put on the ItemStack
     */
    public static ItemStack setLore(ItemStack itemStack, String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param itemStack The ItemStack to edit.
     * @param name The name to put on the ItemStack.
     */
    public static ItemStack setName(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param itemStack The ItemStack to check.
     * @param key The key to check for.
     * @param dataType The PersistentDataType to check for.
     * @return Whether the specified ItemStack has the specified key in it's PersistentDataContainer.
     */
    public static boolean hasData(ItemStack itemStack, String key, PersistentDataType dataType) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(instance, key), dataType);
    }

    /**
     * @param ent The Entity to check.
     * @param key The key to check for.
     * @return Whether the specified Entity has the specified key in it's PersistentDataContainer.
     */
    public static boolean hasData(Entity ent, String key, PersistentDataType dataType) {
        return ent.getPersistentDataContainer().has(new NamespacedKey(instance, key), dataType);
    }

    /**
     * DISCLAIMER: THIS IS NOT FOR TileEntity BLOCKS, THIS IS FOR NORMAL BLOCK ONLY. FOR TileEntity BLOCK USE:
     * ((<BLOCK_CLASS>) <BLOCK>).getPersistentDataContainer().has(new NamespacedKey(<INSTANCE>, <KEY>), <PERSISTENTDATATYPE>)
     * @param b The Block to check.
     * @param key The key to check for.
     * @return Whether the specified Block has the specified key in it's PersistentDataContainer.
     */
    public static boolean hasData(Block b, String key, PersistentDataType dataType) {
        return new CustomBlockData(b, instance).has(new NamespacedKey(instance, key), dataType);
    }

    /**
     * @param itemStack The ItemStack to get the PersistentDataContainer from.
     * @return Returns the PersistentDataContainer of the specified ItemStack.
     */
    public static PersistentDataContainer getData(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }

    /**
     * @param ent The Entity to get the PersistentDataContainer from.
     * @return Returns the PersistentDataContainer of the specified Entity.
     */
    public static PersistentDataContainer getData(Entity ent) {
        return ent.getPersistentDataContainer();
    }

    /**
     * @param b The Block to get the PersistentDataContainer from.
     * @return Returns the PersistentDataContainer of the specified Block.
     */
    public static PersistentDataContainer getData(Block b) {
        return new CustomBlockData(b, instance);
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to remove from the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack removeData(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().remove(new NamespacedKey(instance, key));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit
     * @param key The key to remove from the specified Entity's PersistentDataContainer
     */
    public static Entity removeData(Entity ent, String key) {
        ent.getPersistentDataContainer().remove(new NamespacedKey(instance, key));
        return ent;
    }

    /**
     * @param b The Block to edit
     * @param key The key to remove from the specified Block's PersistentDataContainer
     */
    public static Block removeData(Block b, String key) {
        new CustomBlockData(b, instance).remove(new NamespacedKey(instance, key));
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the String stored in the ItemStack's PersistentDataContainer
     * @param s The String to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataString(ItemStack itemStack, String key, String s) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.STRING, s);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit
     * @param key The key to the String stored in the Entity's PersistentDataContainer
     * @param s The String to store in the specified Entity's PersistentDataContainer
     */
    public static Entity setDataString(Entity ent, String key, String s) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.STRING, s);
        return ent;
    }

    /**
     * @param b The Block to edit
     * @param key The key to the String stored in the Block's PersistentDataContainer
     * @param s The String to store in the specified Block's PersistentDataContainer
     */
    public static Block setDataString(Block b, String key, String s) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.STRING, s);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Boolean stored in the ItemStack's PersistentDataContainer
     * @param b The Boolean to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataBoolean(ItemStack itemStack, String key, Boolean b) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.BOOLEAN, b);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Boolean stored in the Entity's PersistentDataContainer.
     * @param b The Boolean to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataBoolean(Entity ent, String key, Boolean b) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.BOOLEAN, b);
        return ent;
    }

    /**
     * @param ent The Block to edit.
     * @param key The key to the Boolean stored in the Block's PersistentDataContainer.
     * @param b The Boolean to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataBoolean(Block block, String key, Boolean b) {
        new CustomBlockData(block, instance).set(new NamespacedKey(instance, key), DataType.BOOLEAN, b);
        return block;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Integer stored in the ItemStack's PersistentDataContainer
     * @param i The Integer to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataInt(ItemStack itemStack, String key, Integer i) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.INTEGER, i);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Integer stored in the Entity's PersistentDataContainer.
     * @param i The Integer to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataInt(Entity ent, String key, Integer i) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.INTEGER, i);
        return ent;
    }

    /**
     * @param b The Block to edit.
     * @param key The key to the Integer stored in the Block's PersistentDataContainer.
     * @param i The Integer to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataInt(Block b, String key, Integer i) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.INTEGER, i);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Double stored in the ItemStack's PersistentDataContainer
     * @param d The Double to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataDouble(ItemStack itemStack, String key, Double d) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.DOUBLE, d);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Double stored in the Entity's PersistentDataContainer.
     * @param d The Double to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataDouble(Entity ent, String key, Double d) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.DOUBLE, d);
        return ent;
    }

    /**
     * @param b The Block to edit.
     * @param key The key to the Double stored in the Block's PersistentDataContainer.
     * @param d The Double to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataDouble(Block b, String key, Double d) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.DOUBLE, d);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Float stored in the ItemStack's PersistentDataContainer
     * @param f The Float to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataFloat(ItemStack itemStack, String key, Float f) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.FLOAT, f);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Float stored in the Entity's PersistentDataContainer.
     * @param f The Float to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataFloat(Entity ent, String key, Float f) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.FLOAT, f);
        return ent;
    }

    /**
     * @param b The Block to edit.
     * @param key The key to the Float stored in the Block's PersistentDataContainer.
     * @param f The Float to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataFloat(Block b, String key, Float f) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.FLOAT, f);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Long stored in the ItemStack's PersistentDataContainer
     * @param l The Long to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataLong(ItemStack itemStack, String key, Long l) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.LONG, l);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Long stored in the Entity's PersistentDataContainer.
     * @param l The Long to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataLong(Entity ent, String key, Long l) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.LONG, l);
        return ent;
    }

    /**
     * @param b The Block to edit.
     * @param key The key to the Long stored in the Block's PersistentDataContainer.
     * @param l The Long to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataLong(Block b, String key, Long l) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.LONG, l);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Short stored in the ItemStack's PersistentDataContainer
     * @param b The Short to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataShort(ItemStack itemStack, String key, Short s) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.SHORT, s);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Short stored in the Entity's PersistentDataContainer.
     * @param s The Short to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataShort(Entity ent, String key, Short s) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.SHORT, s);
        return ent;
    }

    /**
     * @param b The Block to edit.
     * @param key The key to the Short stored in the Block's PersistentDataContainer.
     * @param s The Short to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataShort(Block b, String key, Short s) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.SHORT, s);
        return b;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the Byte stored in the ItemStack's PersistentDataContainer
     * @param b The Byte to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataByte(ItemStack itemStack, String key, Byte b) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.BYTE, b);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit.
     * @param key The key to the Byte stored in the Entity's PersistentDataContainer.
     * @param b The Byte to store in the specified Entity's PersistentDataContainer.
     */
    public static Entity setDataByte(Entity ent, String key, Byte b) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.BYTE, b);
        return ent;
    }

    /**
     * @param block The Block to edit.
     * @param key The key to the Byte stored in the Block's PersistentDataContainer.
     * @param b The Byte to store in the specified Block's PersistentDataContainer.
     */
    public static Block setDataByte(Block block, String key, Byte b) {
        new CustomBlockData(block, instance).set(new NamespacedKey(instance, key), DataType.BYTE, b);
        return block;
    }

    /**
     * @param itemStack The ItemStack to edit
     * @param key The key to the ItemStack stored in the ItemStack's PersistentDataContainer
     * @param toStore The ItemStack to store in the specified ItemStack's PersistentDataContainer
     */
    public static ItemStack setDataItemStack(ItemStack itemStack, String key, ItemStack toStore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.ITEM_STACK, toStore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * @param ent The Entity to edit
     * @param key The key to the ItemStack stored in the specified Entity's PersistentDataContainer
     * @param toStore The ItemStack to store in the specified Entity's PersistentDataContainer
     */
    public static Entity setDataItemStack(Entity ent, String key, ItemStack toStore) {
        ent.getPersistentDataContainer().set(new NamespacedKey(instance, key), DataType.ITEM_STACK, toStore);
        return ent;
    }

    /**
     * @param b The Block to edit
     * @param key The key to the ItemStack stored in the specified Block's PersistentDataContainer
     * @param toStore The ItemStack to store in the specified Block's PersistentDataContainer
     */
    public static Block setDataItemStack(Block b, String key, ItemStack toStore) {
        new CustomBlockData(b, instance).set(new NamespacedKey(instance, key), DataType.ITEM_STACK, toStore);
        return b;
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the String stored in the specified ItemStack's PersistentDataContainer
     * @return The requested String or null if not present
     */
    @Nullable
    public static String getDataString(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.STRING);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the String stored in the specified Entity's PersistentDataContainer
     * @return The requested String or null if not present
     */
    @Nullable
    public static String getDataString(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.STRING);
    }

    /**
     * @param b The Block to check
     * @param key The key to the String stored in the specified Block's PersistentDataContainer
     * @return The requested String or null if not present
     */
    @Nullable
    public static String getDataString(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.STRING);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Boolean stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Boolean or null if not present
     */
    @Nullable
    public static Boolean getDataBoolean(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.BOOLEAN);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Boolean stored in the specified Entity's PersistentDataContainer
     * @return The requested String or null if not present
     */
    @Nullable
    public static Boolean getDataBoolean(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.BOOLEAN);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Boolean stored in the specified Block's PersistentDataContainer
     * @return The requested String or null if not present
     */
    @Nullable
    public static Boolean getDataBoolean(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.BOOLEAN);
    }


    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Integer stored in the ItemStack's PersistentDataContainer
     * @return The requested Integer or null if not present
     */
    @Nullable
    public static Integer getDataInt(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.INTEGER);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Integer stored in the specified Entity's PersistentDataContainer
     * @return The requested Integer or null if not present
     */
    @Nullable
    public static Integer getDataInt(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.INTEGER);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Integer stored in the specified Block's PersistentDataContainer
     * @return The requested Integer or null if not present
     */
    @Nullable
    public static Integer getDataInt(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.INTEGER);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Double stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Double or null if not present
     */
    @Nullable
    public static Double getDataDouble(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.DOUBLE);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Double stored in the specified Entity's PersistentDataContainer
     * @return The requested Double or null if not present
     */
    @Nullable
    public static Double getDataDouble(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.DOUBLE);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Double stored in the specified Block's PersistentDataContainer
     * @return The requested Double or null if not present
     */
    @Nullable
    public static Double getDataDouble(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.DOUBLE);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Float stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Float or null if not present
     */
    @Nullable
    public static Float getDataFloat(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.FLOAT);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Float stored in the specified Entity's PersistentDataContainer
     * @return The requested Float or null if not present
     */
    @Nullable
    public static Float getDataFloat(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.FLOAT);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Float stored in the specified Block's PersistentDataContainer
     * @return The requested Float or null if not present
     */
    @Nullable
    public static Float getDataFloat(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.FLOAT);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Long stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Long or null if not present
     */
    @Nullable
    public static Long getDataLong(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.LONG);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Long stored in the specified Entity's PersistentDataContainer
     * @return The requested Long or null if not present
     */
    @Nullable
    public static Long getDataLong(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.LONG);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Long stored in the specified Block's PersistentDataContainer
     * @return The requested Long or null if not present
     */
    @Nullable
    public static Long getDataLong(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.LONG);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Short stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Short or null if not present
     */
    @Nullable
    public static Short getDataShort(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.SHORT);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Short stored in the specified Entity's PersistentDataContainer
     * @return The requested Short or null if not present
     */
    @Nullable
    public static Short getDataShort(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.SHORT);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Short stored in the specified Block's PersistentDataContainer
     * @return The requested Short or null if not present
     */
    @Nullable
    public static Short getDataShort(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.SHORT);
    }

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the Byte stored in the specified ItemStack's PersistentDataContainer
     * @return The requested Byte or null if not present
     */
    @Nullable
    public static Byte getDataByte(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.BYTE);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the Byte stored in the specified Entity's PersistentDataContainer
     * @return The requested Byte or null if not present
     */
    @Nullable
    public static Byte getDataByte(Entity ent, String key) {
       return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.BYTE);
    }

    /**
     * @param b The Block to check
     * @param key The key to the Byte stored in the specified Block's PersistentDataContainer
     * @return The requested Byte or null if not present
     */
    @Nullable
    public static Byte getDataByte(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.BYTE);
    }

    // this is where the fun starts

    /**
     * @param itemStack The ItemStack to check
     * @param key The key to the ItemStack stored in the specified ItemStack's PersistentDataContainer
     * @return The requested ItemStack or null if not present
     */
    @Nullable
    public static ItemStack getDataItemStack(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.ITEM_STACK);
    }

    /**
     * @param ent The Entity to check
     * @param key The key to the ItemStack stored in the specified Entity's PersistentDataContainer
     * @return The requested ItemStack or null if not present
     */
    @Nullable
    public static ItemStack getDataItemStack(Entity ent, String key) {
        return ent.getPersistentDataContainer().get(new NamespacedKey(instance, key), DataType.ITEM_STACK);
    }

    /**
     * @param b The Block to check
     * @param key The key to the ItemStack stored in the specified Block's PersistentDataContainer
     * @return The requested ItemStack or null if not present
     */
    @Nullable
    public static ItemStack getDataItemStack(Block b, String key) {
        return new CustomBlockData(b, instance).get(new NamespacedKey(instance, key), DataType.ITEM_STACK);
    }



}