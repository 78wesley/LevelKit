package nl.Wesley.Main.Database;

import nl.Wesley.Main.LevelKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.sql.*;

/**
 * Created by Wesley on 10/11/2016.
 */
public class DatabaseSetup  extends LevelKit {

    public static Connection connection;

    public synchronized static void openConnection() {
        try {
            System.out.println(ChatColor.AQUA + "CONNECTED WITH THE MYSQL DATABASE");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minecraft", "root", "toor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static void closeConnection() {
        try {
            System.out.println(ChatColor.AQUA + "DISCONNECTED WITH THE MYSQL DATABASE");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static void disableConnection() {
        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public synchronized static boolean playerDataContainsPlayer(Player player) {
        try {
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player_name=?;");
            sql.setString(1, player.getName());
            ResultSet resultSet = sql.executeQuery();
            boolean containsPlayer = resultSet.next();

            sql.close();
            resultSet.close();

            return containsPlayer;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized static void addIntPlayerServerJoined(Player player) {
        openConnection();
        try {
            int player_serverjoined = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_serverjoined FROM `player_data` WHERE player_name=?;");
                sql.setString(1, player.getPlayer().getName());
                ResultSet result = sql.executeQuery();
                result.next();
                player_serverjoined = result.getInt("player_serverjoined");
                PreparedStatement player_serverjoinedUpdate = connection.prepareStatement("UPDATE `player_data` SET player_serverjoined=? WHERE player_name=?;");

                player_serverjoinedUpdate.setInt(1, player_serverjoined + 1);
                player_serverjoinedUpdate.setString(2, player.getPlayer().getName());
                player_serverjoinedUpdate.executeUpdate();

                player_serverjoinedUpdate.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public synchronized static void addNewPlayer(Player player) {
       openConnection();
        try {
            System.out.println("CREATING NEW PLAYER FOR " + player.getName());
            PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_data` values(?,?,0,0,0);");
            newPlayer.setString(1, player.getPlayer().getName());
            newPlayer.setString(2, player.getPlayer().getUniqueId().toString());
            newPlayer.execute();
            newPlayer.close();
            if (newPlayer == null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
