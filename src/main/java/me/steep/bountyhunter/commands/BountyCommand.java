package me.steep.bountyhunter.commands;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.Util;
import me.steep.bountyhunter.handlers.Log;
import me.steep.bountyhunter.handlers.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("all")
public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player p)) {
            BountyHunter.send(Util.color("&cOnly players can use this command."));
            return true;
        }

        if()

        new BukkitRunnable() {
            @Override
            public void run() {

                Connection con = null;

                try {

                    con = SQLite.getConnection();

                } catch (SQLException e) {

                    Log.failed_Connection_To_SQL_DataBase(e);

                } finally {

                    SQLite.closeConnection(con);
                }


            }
        }.runTaskAsynchronously(BountyHunter.getInst());

        return true;
    }

    private void sendHelp(Player p) {

        p.sendMessage(color("&6/bounty list"));
        p.sendMessage(color("&6/bounty add <player>"));
        p.sendMessage(color("&6/bounty remove <player>"));

        if(p.hasPermission("bounty.admin")) {

            p.sendMessage(color("&6/bounty admin remove <creator> <wanted>"));

        }

    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
