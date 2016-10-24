package nl.Wesley.Main.Database;

import nl.Wesley.Main.Listeners.ScoreboardListener;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Created by Wesley on 10/11/2016.
 */
public class DatabaseSetup {

    private static int playerCustomLevel;
    private static int playerCustomXP;
    private static int playerKills;
    private static int playerDeaths;

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
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player_uuid=?;");
            sql.setString(1, player.getUniqueId().toString());
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
            System.out.println("CREATING NEW TABLE FOR " + player.getName());
            PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_data` values(?,?,0,0,0,0);");
            newPlayer.setString(1, player.getPlayer().getUniqueId().toString());
            newPlayer.setString(2, player.getPlayer().getName());
            newPlayer.execute();
            newPlayer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    /** playerCustomLevel */
    public synchronized static Integer getPlayerCustomLevel(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    playerCustomLevel = sql.getResultSet().getInt(1);
                }
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
        return playerCustomLevel;
    }

    public synchronized static void setPlayerCustomLevel(Player player, int integer) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                integer = result.getInt("player_custom_level");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_level=? WHERE player_uuid=?;");

                update.setInt(1, integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void addPlayerCustomLevel(Player player, int integer) {
        openConnection();
        try {
            int player_custom_level = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_level = result.getInt("player_custom_level");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_level=? WHERE player_uuid=?;");

                update.setInt(1, player_custom_level + integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void remPlayerCustomLevel(Player player, int integer) {
        openConnection();
        try {
            int player_custom_level = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_level FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_level = result.getInt("player_custom_level");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_level=? WHERE player_uuid=?;");

                update.setInt(1, player_custom_level - integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    /** playerCustomXP */
    public synchronized static Integer getPlayerCustomXP(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_xp FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    playerCustomXP = sql.getResultSet().getInt(1);
                }
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
        return playerCustomXP;
    }

    public synchronized static void setPlayerCustomXP(Player player, int integer) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_xp FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                integer = result.getInt("player_custom_xp");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_xp=? WHERE player_uuid=?;");

                update.setInt(1, integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void addPlayerCustomXP(Player player, int integer) {
        openConnection();
        try {
            int player_custom_xp = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_xp FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_xp = result.getInt("player_custom_xp");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_xp=? WHERE player_uuid=?;");

                update.setInt(1, player_custom_xp + integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void remPlayerCustomXP(Player player, int integer) {
        openConnection();
        try {
            int player_custom_xp = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_custom_xp FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_custom_xp = result.getInt("player_custom_xp");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_custom_xp=? WHERE player_uuid=?;");

                update.setInt(1, player_custom_xp - integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    /** playerKills */
    public synchronized static Integer getPlayerKills(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_kills FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    playerKills = sql.getResultSet().getInt(1);
                }
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
        return playerKills;
    }

    public synchronized static void setPlayerKills(Player player, int integer) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_kills FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                integer = result.getInt("player_kills");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_kills=? WHERE player_uuid=?;");

                update.setInt(1, integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void addPlayerKills(Player player, int integer) {
        openConnection();
        try {
            int player_kills = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_kills FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_kills = result.getInt("player_kills");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_kills=? WHERE player_uuid=?;");

                update.setInt(1, player_kills + integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    public synchronized static void remPlayerKills(Player player, int integer) {
        openConnection();
        try {
            int player_kills = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_kills FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_kills = result.getInt("player_kills");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_kills=? WHERE player_uuid=?;");

                update.setInt(1, player_kills - integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    /** playerDeaths */
    public synchronized static Integer getPlayerDeaths(Player player) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_deaths FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                while (result.next()) {
                    playerDeaths = sql.getResultSet().getInt(1);
                }
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
        return playerDeaths;
    }

    public synchronized static void setPlayerDeaths(Player player, int integer) {
        openConnection();
        try {
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_deaths FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                integer = result.getInt("player_deaths");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_deaths=? WHERE player_uuid=?;");

                update.setInt(1, integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
            ScoreboardListener.scoreboardUpdate(player);
        }
    }

    public synchronized static void addPlayerDeaths(Player player, int integer) {
        openConnection();
        try {
            int player_deaths = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_deaths FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_deaths = result.getInt("player_deaths");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_deaths=? WHERE player_uuid=?;");

                update.setInt(1, player_deaths + integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
            ScoreboardListener.scoreboardUpdate(player);
        }
    }

    public synchronized static void remPlayerDeaths(Player player, int integer) {
        openConnection();
        try {
            int player_deaths = 0;
            if (playerDataContainsPlayer(player.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_deaths FROM `player_data` WHERE player_uuid=?;");
                sql.setString(1, player.getUniqueId().toString());
                ResultSet result = sql.executeQuery();
                result.next();
                player_deaths = result.getInt("player_deaths");
                PreparedStatement update = connection.prepareStatement("UPDATE `player_data` SET player_deaths=? WHERE player_uuid=?;");

                update.setInt(1, player_deaths - integer);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();

                update.close();
                sql.close();
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScoreboardListener.scoreboardUpdate(player);
            closeConnection();
        }
    }

    /**
     * #TODO Checking if player has custom XP and set it in to a LEVEL
     */
    public static void XPToLevel(Player player) {
        if (playerDataContainsPlayer(player)) {
            if (getPlayerCustomXP(player) == 1000) {
                setPlayerCustomLevel(player, 1);
            } else if (getPlayerCustomXP(player) == 2000) {
                setPlayerCustomLevel(player, 2);
            } else if (getPlayerCustomXP(player) == 3000) {
                setPlayerCustomLevel(player, 3);
            } else if (getPlayerCustomXP(player) == 4000) {
                setPlayerCustomLevel(player, 4);
            } else if (getPlayerCustomXP(player) == 5000) {
                setPlayerCustomLevel(player, 5);
            } else if (getPlayerCustomXP(player) == 6000) {
                setPlayerCustomLevel(player, 6);
            } else if (getPlayerCustomXP(player) == 7000) {
                setPlayerCustomLevel(player, 7);
            } else if (getPlayerCustomXP(player) == 8000) {
                setPlayerCustomLevel(player, 8);
            } else if (getPlayerCustomXP(player) == 9000) {
                setPlayerCustomLevel(player, 9);
            } else if (getPlayerCustomXP(player) == 10000) {
                setPlayerCustomLevel(player, 10);
            } else if (getPlayerCustomXP(player) == 11000) {
                setPlayerCustomLevel(player, 11);
            } else if (getPlayerCustomXP(player) == 12000) {
                setPlayerCustomLevel(player, 12);
            } else if (getPlayerCustomXP(player) == 13000) {
                setPlayerCustomLevel(player, 13);
            } else if (getPlayerCustomXP(player) == 14000) {
                setPlayerCustomLevel(player, 14);
            } else if (getPlayerCustomXP(player) == 15000) {
                setPlayerCustomLevel(player, 15);
            } else if (getPlayerCustomXP(player) == 16000) {
                setPlayerCustomLevel(player, 16);
            } else if (getPlayerCustomXP(player) == 17000) {
                setPlayerCustomLevel(player, 17);
            } else if (getPlayerCustomXP(player) == 18000) {
                setPlayerCustomLevel(player, 18);
            } else if (getPlayerCustomXP(player) == 19000) {
                setPlayerCustomLevel(player, 19);
            } else if (getPlayerCustomXP(player) == 20000) {
                setPlayerCustomLevel(player, 20);
            } else if (getPlayerCustomXP(player) == 21000) {
                setPlayerCustomLevel(player, 21);
            } else if (getPlayerCustomXP(player) == 22000) {
                setPlayerCustomLevel(player, 22);
            }
        }
    }
}
