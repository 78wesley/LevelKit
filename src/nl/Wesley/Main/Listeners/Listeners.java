package nl.Wesley.Main.Listeners;

//import nl.Wesley.Main.LevelKit;

import nl.Wesley.Main.Database.DatabaseSetup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Wesley on 10/11/2016.
 */
public class Listeners implements Listener {

    /**
     * PlayerJointEvent
     * @param
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().hasPlayedBefore()){
            DatabaseSetup.addIntPlayerServerJoined(player);
        } else  if (!player.hasPlayedBefore()) {
            DatabaseSetup.addNewPlayer(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DatabaseSetup.closeConnection();
    }
}
