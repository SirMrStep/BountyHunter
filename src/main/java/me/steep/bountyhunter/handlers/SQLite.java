package me.steep.bountyhunter.handlers;

import me.steep.bountyhunter.BountyHunter;
import me.steep.bountyhunter.Util;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("all")
public class SQLite {

    public static void loadDefaultDatabase() {

        Connection con = null;

        try {

            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + BountyHunter.getInst().getDataFolder() + "/database.db");
            con.createStatement().executeUpdate("create table if not exists bounty (creator text, wanted text, rewards text)");

        } catch (SQLException e) {

            Log.failed_Connection_To_SQL_DataBase(e);

        } finally {

            closeConnection(con);

        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + BountyHunter.getInst().getDataFolder() + "/database.db");
    }

    public static void closeConnection(Connection con) {

        if(con != null) {

            try {

                con.close();

            } catch (SQLException e) {

                failed_To_Close_SQL_Connection(e);

            }

        }

    }

    public static void closeResult(ResultSet result) {

        if(result != null) {

            try {

                result.close();

            } catch (SQLException e) {

                failed_To_Close_SQL_Connection(e);

            }

        }

    }

    public static void closeConnectionAndResult(Connection con, ResultSet result) {

        if(con != null) {

            try {

                con.close();

            } catch (SQLException e) {

                failed_To_Close_SQL_Connection(e);

            }

        }

        if(result != null) {

            try {

                result.close();

            } catch (SQLException e) {

                failed_To_Close_SQL_Connection(e);

            }

        }

    }

    private static void failed_To_Close_SQL_Connection() {
        Bukkit.getConsoleSender().sendMessage(Util.color("&c[Bounty] Failed to close connection to database."));
    }

    private static void failed_To_Close_SQL_Connection(SQLException ex) {
        Bukkit.getConsoleSender().sendMessage(Util.color("&c[Bounty] Failed to close connection to database:"));
        ex.printStackTrace();
    }

}
