package nl.Wesley.Main.Profiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.Wesley.Main.LevelKit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Wesley on 10/11/2016.
 */
public class ProfileLoader extends BukkitRunnable {

    private Profile profile;
    private LevelKit plugin;


    private static final String INSERT = "INSERT INTO data VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?";
    private static final String SELECT = "SELECT player_kills,levels FROM data WHERE uuid=?";

    public ProfileLoader(Profile profile, Plugin plugin) {
        return;
    }

    @Override
    public void run() {
        Connection connection = null;

        //TODO: load stats
        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, ((Player) profile).getUniqueId().toString());
            preparedStatement.setString(2, ((Player) profile).getUniqueId().toString());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, 0);
            preparedStatement.setString(7, ((Player) profile).getName());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, ((Player) profile).getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                // https://youtu.be/8VvJI04YZDA?t=10m31s
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
