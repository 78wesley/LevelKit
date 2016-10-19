package nl.Wesley.Main.Listeners;


import nl.Wesley.Main.Database.DatabaseSetup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


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
        DatabaseSetup.addIntPlayerServerJoined(player);
        DatabaseSetup.addIntPlayerCustomLevel(player);
        player.sendMessage(ChatColor.RED + "JE BENT AL "+ DatabaseSetup.getIntPlayerServerJoined(player) + " KEER INGELOGHT");
        ScoreboardListener.scoreboardSetup(player);
        if (!player.hasPlayedBefore()) {
            DatabaseSetup.addNewPlayer(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DatabaseSetup.closeConnection();
    }
}
