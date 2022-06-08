package me.steep.bountyhunter.handlers;

import me.steep.bountyhunter.Util;
import org.bukkit.Bukkit;

import java.sql.SQLException;

/**
 * Move this into SteepAPI
 * Also make it use GeneralAPI there
 */
public class Log {

    public static void failed_Connection_To_SQL_Database() {
        Bukkit.getConsoleSender().sendMessage(Util.color("&c[Bounty] Failed to connect to database."));
    }

    public static void failed_Connection_To_SQL_DataBase(SQLException ex) {
        Bukkit.getConsoleSender().sendMessage(Util.color("&c[Bounty] Failed to connect to database:"));
        ex.printStackTrace();
    }

}
