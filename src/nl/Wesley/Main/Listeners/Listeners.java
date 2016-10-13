package nl.Wesley.Main.Listeners;

import nl.Wesley.Main.LevelKit;
import nl.Wesley.Main.Profiles.Profile;
import nl.Wesley.Main.Profiles.Profile;
import nl.Wesley.Main.Profiles.ProfileLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Wesley on 10/11/2016.
 */
public class Listeners implements Listener{

    private LevelKit PLUGIN;

    /**
     * PlayerJointEvent
     * @param
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (event.getPlayer().hasPlayedBefore() || !event.getPlayer().hasPlayedBefore()) {

            final Profile profile = new Profile(p);
            PLUGIN.getProfiles().put(p.getUniqueId(), profile);
            new ProfileLoader(profile, PLUGIN).runTaskAsynchronously(PLUGIN);
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Profile profile = new Profile(e.getPlayer());
        new ProfileLoader(profile, PLUGIN).runTaskAsynchronously(PLUGIN);
    }
}
