package nl.Wesley.Main.Listeners;

//import nl.Wesley.Main.LevelKit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Wesley on 10/11/2016.
 */
public class Listeners implements Listener{

    //private LevelKit plugin;

    /**
     * PlayerJointEvent
     * @param
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (event.getPlayer().hasPlayedBefore() || !event.getPlayer().hasPlayedBefore()) {
            p.sendMessage(p.getName() + ", Has Joined the server !");
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }
}
