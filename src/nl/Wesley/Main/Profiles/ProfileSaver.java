package nl.Wesley.Main.Profiles;

import java.sql.Connection;
import java.sql.SQLException;
import nl.Wesley.Main.LevelKit;
import org.bukkit.scheduler.BukkitRunnable;
/**
 * Created by Wesley on 10/11/2016.
 * this is for a test for commit and pushnnnn
 */
public class ProfileSaver extends BukkitRunnable {

    @SuppressWarnings("unused")
    private Profile profile;
    private LevelKit plugin;

    @Override
    public void run() {
        Connection connection = null;

        //TODO: load stats
        try {
            connection = plugin.getHikari().getConnection();
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
