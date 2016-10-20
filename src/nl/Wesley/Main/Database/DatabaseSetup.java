package nl.Wesley.Main.Database;

import nl.Wesley.Main.LevelKit;
import nl.Wesley.Main.Listeners.ScoreboardListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Created by Wesley on 10/11/2016.
 */
public class DatabaseSetup {

    private static int playerServerJoined;
    private static int playerCustomLevel;
    public static Connection connection;

    /** openConnection*/
    public synchronized static void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minecraft", "root", "toor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** closeConnection*/
    public synchronized static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** disableConnection*/
    public synchronized static void disableConnection() {
        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** playerDataContainsPlayer*/
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

    /** addNewPlayer*/
    public synchronized static void addNewPlayer(Player player) {
        openConnection();
        try {
            System.out.println(ChatColor.AQUA + "CREATING NEW TABLE FOR " + player.getName());
            PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_data` values(?,?,0,0,0);");
            newPlayer.setString(1, player.getPlayer().getName());
            newPlayer.setString(2, player.getPlayer().getUniqueId().toString());
            newPlayer.execute();
            newPlayer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /** playerServerJoined*/
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
            } else {
                addNewPlayer(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public synchronized static Integer getIntPlayerServerJoined(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_serverjoined FROM `player_data` WHERE player_name=?;");
                sql.setString(1, player.getName());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                   playerServerJoined = sql.getResultSet().getInt(1);
                }
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return playerServerJoined;
    }

    /** playerCustomLevel */
    public synchronized static Integer getIntPlayerCustomLevel(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_name=?;");
                sql.setString(1, player.getName());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    playerCustomLevel = sql.getResultSet().getInt(1);
                }
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return playerCustomLevel;
    }

    public synchronized static void addIntPlayerCustomLevel(Player player, int integer) {
        openConnection();
        ScoreboardListener.scoreboardUpdate(player);
        try {
            int player_custom_level = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_name=?;");
                sql.setString(1, player.getPlayer().getName());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_level = result.getInt("player_custom_level");
                PreparedStatement player_custom_levelUpdate = connection.prepareStatement("UPDATE `player_data` SET player_custom_level=? WHERE player_name=?;");

                player_custom_levelUpdate.setInt(1, player_custom_level + integer);
                player_custom_levelUpdate.setString(2, player.getPlayer().getName());
                player_custom_levelUpdate.executeUpdate();

                player_custom_levelUpdate.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public synchronized static void remIntPlayerCustomLevel(Player player, int integer) {
        openConnection();
        ScoreboardListener.scoreboardUpdate(player);
        try {
            int player_custom_level = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_name=?;");
                sql.setString(1, player.getPlayer().getName());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_level = result.getInt("player_custom_level");
                PreparedStatement player_custom_levelUpdate = connection.prepareStatement("UPDATE `player_data` SET player_custom_level=? WHERE player_name=?;");

                player_custom_levelUpdate.setInt(1, player_custom_level - integer);
                player_custom_levelUpdate.setString(2, player.getPlayer().getName());
                player_custom_levelUpdate.executeUpdate();

                player_custom_levelUpdate.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /** playerKills */


}
