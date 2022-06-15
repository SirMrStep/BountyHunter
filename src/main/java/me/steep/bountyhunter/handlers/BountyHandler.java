package me.steep.bountyhunter.handlers;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.Util;
import me.steep.bountyhunter.objects.Bounty;
import me.steep.bountyhunter.objects.Callback;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
public class BountyHandler {

    /**
     * @param creator The UUID of the Player who put up this Bounty.
     * @param wanted The UUID of the wanted Player.
     * @param rewards The rewards for killing the wanted Player.
     * @return The Bounty created.
     */
    public static Bounty createBounty(UUID creator, UUID wanted, List<ItemStack> rewards) {

        return new Bounty(creator, wanted, rewards).create();

    }

    /**
     * @param creator The UUID of the Player who put up this Bounty.
     * @param wanted The UUID of the wanted Player.
     * @param callback
     */
    public static void getBounty(UUID creator, UUID wanted, Callback<Bounty, SQLException> callback) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;
                ResultSet result = null;

                try {

                    con = SQLite.getConnection();
                    result = con.createStatement().
                            executeQuery("select * from bounty where creator='" + creator.toString() + "' and wanted='" + wanted.toString() + "'");

                    UUID c = UUID.fromString(result.getString("creator"));
                    UUID w = UUID.fromString(result.getString("wanted"));
                    List<ItemStack> r = Util.format_StringToItemList(result.getString("rewards"));

                    callback.call(new Bounty(c, w, r), null);

                } catch (SQLException e) {

                    callback.call(null, e);
                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnectionAndResult(con, result);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

    }

    public static void reward(Bounty bounty, Player player) {

        // test if this works later V
        //int air = (int) Arrays.stream(player.getInventory().getContents()).filter(item -> item.getType() == Material.AIR).count();

        int air = 0;

        // Check how many empty slots the inventory has
        for(int index = 0; index < player.getInventory().getSize(); index++) {

            ItemStack item = player.getInventory().getItem(index);

            if(item == null || item.getType() == Material.AIR) {

                air++;

            }

        }

        // put the rewards in a '/bounty claim' menu if the inventory doesn't have enough empty slots for the reward
        if(air < bounty.getRewards().size()) {

            // store unclaimed rewards in database for later '/bounty claim' menu
            new BukkitRunnable() {
                @Override
                public void run() {

                    Connection con = null;

                    try {

                        Connection conn = SQLite.getConnection();
                        con.createStatement().executeUpdate("insert into rewards (owner, rewards) values ('" + bounty.getCreator().getUniqueId().toString() + "', '" + Util.format_ItemListToString(bounty.getRewards()) + "')");

                    } catch (SQLException e) {

                        Log.failed_Connection_To_SQL_DataBase(e);

                    } finally {

                        SQLite.closeConnection(con);

                    }

                }
            }.runTaskAsynchronously(BountyHunter.getInst());

            return;
        }

        bounty.getRewards().forEach(item -> player.getInventory().addItem(item));

        removeBounty(bounty.getCreatorUUID(), bounty.getWantedUUID());

    }

    public static void removeBounty(UUID creator, UUID wanted) {

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

    public static void loadUnclaimedRewards(Player p, Callback<List<ItemStack>, SQLException> callback) {

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;
                ResultSet result = null;

                try {

                    con = SQLite.getConnection();
                    result = con.createStatement().
                            executeQuery("select * from rewards where owner='" + p.getUniqueId().toString() + "'");

                    if(result.getString("rewards").equals("none")) {
                        callback.call(null, null);
                        return;
                    }

                    callback.call(Util.format_StringToItemList(result.getString("rewards")), null);

                } catch (SQLException e) {

                    callback.call(null, e);
                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnectionAndResult(con, result);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

    }

}
