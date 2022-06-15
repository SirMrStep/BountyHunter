package me.steep.bountyhunter.objects;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.Util;
import me.steep.bountyhunter.handlers.ItemHandler;
import me.steep.bountyhunter.handlers.Log;
import me.steep.bountyhunter.handlers.SQLite;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("all")
public class Bounty {

    private final UUID creator;
    private final UUID wanted;
    private final List<ItemStack> rewards;

    /**
     * A Bounty that has not yet been created/activated and is not yet store in the database
     *
     * @param creator The UUID of the player who put up the Bounty
     * @param wanted The UUID of the person wanted in this Bounty
     * @param rewards The rewards of this Bounty
     */
    public Bounty(UUID creator, UUID wanted, List<ItemStack> rewards) {

        this.creator = creator;
        this.wanted = wanted;
        this.rewards = rewards;

    }
    /*
    public Bounty(int id, UUID creator, UUID wanted, List<ItemStack> rewards) {

        this.id = id;
        this.creator = creator;
        this.wanted = wanted;
        this.rewards = rewards;

    }

    public Bounty(UUID creator, UUID wanted) {

        this.creator = creator;
        this.wanted = wanted;

    }

    public Bounty(UUID creator, UUID wanted, ItemStack rewards) {

        this.creator = creator;
        this.wanted = wanted;

        List<ItemStack> reward = new ArrayList<>();
        reward.add(rewards);

        this.rewards = reward;

    }

    public Bounty(int id, UUID creator, UUID wanted, ItemStack rewards) {

        this.id = id;
        this.creator = creator;
        this.wanted = wanted;

        List<ItemStack> reward = new ArrayList<>();
        reward.add(rewards);

        this.rewards = reward;

    }
    */

    public Bounty create() {

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;

                try {

                    con = SQLite.getConnection();
                    con.createStatement().executeUpdate("insert into bounty (creator, wanted, rewards) values (" + creator.toString() + ", " + wanted.toString() + ", " + Util.format_ItemListToString(getRewards()) + ")");

                } catch (SQLException e) {

                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnection(con);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

        return this;

    }


    /* Replaced by BountyHandler's removeBounty() method
    public void remove() {

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;

                try {

                    con = SQLite.getConnection();
                    con.createStatement().
                            executeUpdate("delete from bounty where creator='" + creator.toString() + "' and wanted='" + wanted.toString() + "'");

                } catch (SQLException e) {

                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnection(con);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

    }
     */

    @NotNull
    public List<ItemStack> getRewards() {
        return this.rewards != null ? this.rewards : new ArrayList<ItemStack>();
    }

    @NotNull
    public Player getCreator() {
        return Bukkit.getPlayer(this.creator);
    }

    @NotNull
    public UUID getCreatorUUID() {
        return this.creator;
    }

    @NotNull
    public Player getWanted() {
        return Bukkit.getPlayer(this.wanted);
    }

    @NotNull
    public UUID getWantedUUID() {
        return this.wanted;
    }

}
