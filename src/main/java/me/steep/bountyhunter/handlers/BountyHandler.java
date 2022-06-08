package me.steep.bountyhunter.handlers;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.objects.Bounty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BountyHandler {

    public static void createBounty(UUID creator, UUID wanted)

    public static Bounty getBounty(UUID creator, UUID wanted, boolean created) {

        return new Bounty(created ? 1 : -1, creator, wanted);

    }

    public static void reward(Bounty bounty, Player player) {

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
            // put the rewards in a /bounty claim menu here
            return;
        }

        bounty.getRewards().forEach(item -> player.getInventory().addItem(item));

        bounty.remove();

    }

    public static void removeBounty(String s) {



    }

    public static void removeBounty(UUID creator, UUID wanted) {

        new BukkitRunnable() {

            @Override
            public void run() {

                Connection con = null;
                ResultSet result = null;

                try {

                    con = SQLite.getConnection();

                    DataBase.executeUpdate("delete from `urmom` where `creator`='" + creator.toString() + "' and `wanted`='" + wanted.toString() + "'");

                } catch (SQLException e) {

                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnectionAndResult(con, result);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

    }

}
