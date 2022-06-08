package me.steep.bountyhunter.objects;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.handlers.Log;
import me.steep.bountyhunter.handlers.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("all")
public class Bounty {

    private int id = -1;
    private final UUID creator;
    private final UUID wanted;
    private Set<ItemStack> rewards = null;

    public Bounty(UUID creator, UUID wanted, Set<ItemStack> rewards) {

        this.creator = creator;
        this.wanted = wanted;
        this.rewards = rewards;

    }

    public Bounty(int id, UUID creator, UUID wanted, Set<ItemStack> rewards) {

        this.id = id;
        this.creator = creator;
        this.wanted = wanted;
        this.rewards = rewards;

    }

    public Bounty(UUID creator, UUID wanted) {

        this.creator = creator;
        this.wanted = wanted;

    }

    public Bounty(int id, UUID creator, UUID wanted) {

        this.id = id;
        this.creator = creator;
        this.wanted = wanted;

    }

    public Bounty(UUID creator, UUID wanted, ItemStack rewards) {

        this.creator = creator;
        this.wanted = wanted;

        Set<ItemStack> reward = new HashSet<>();
        reward.add(rewards);

        this.rewards = reward;

    }

    public Bounty(int id, UUID creator, UUID wanted, ItemStack rewards) {

        this.id = id;
        this.creator = creator;
        this.wanted = wanted;

        Set<ItemStack> reward = new HashSet<>();
        reward.add(rewards);

        this.rewards = reward;

    }

    public void create() {

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;

                try {

                    con = SQLite.getConnection();
                    con.createStatement().executeUpdate("insert into bounty (creator, wanted) values (" + creator.toString() + ", " + wanted.toString() + ", " + format(rewards) + ")");

                } catch (SQLException e) {

                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnection(con);

                }

            }
        }.runTaskAsynchronously(BountyHunter.getInst());

    }

    public void remove() {

        if(this.id == -1) return;



    }

    @NotNull
    public Set<ItemStack> getRewards() {
        return this.rewards != null ? this.rewards : new HashSet<>();
    }

    @NotNull
    public Player getCreator() {
        return Bukkit.getPlayer(this.creator);
    }

    @NotNull
    public Player getWanted() {
        return Bukkit.getPlayer(this.wanted);
    }

    private String format(Set<ItemStack> r) {

        StringBuilder formatted = new StringBuilder("");

        r.forEach(item -> formatted.append(item.getType() + "(" + item.getAmount() + ")[" + "nbt goes here" + "],"));

        return formatted.toString();

    }

    private Set<ItemStack> unformat(String formatted) {

        Set<ItemStack> unformatted = new HashSet<>();

        String[] items = formatted.split(",");

        for(int index = 0; index < formatted.split(",").length; index++) {

            String entry = items[index];

            Material material = Material.getMaterial(entry.split("\\[")[0].toUpperCase());
            int amount = Integer.parseInt(entry.substring(entry.indexOf("("), entry.indexOf("(") + 1));

            ItemStack item = new ItemStack(material, amount);

            unformatted.add(new ItemStack(material, amount));

        }

        return unformatted;

    }

}
