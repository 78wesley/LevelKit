package nl.Wesley.Main.Listeners;


import nl.Wesley.Main.Database.DatabaseSetup;
import static org.bukkit.ChatColor.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            ScoreboardListener.scoreboardSetup(player);
        } else if (!player.hasPlayedBefore()) {
            DatabaseSetup.addNewPlayer(player);
            DatabaseSetup.addPlayerCustomLevel(player, 100);
            ScoreboardListener.scoreboardSetup(player);
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e) {
        DatabaseSetup.closeConnection();
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();
        if (player.getKiller() == killer) {
            e.setDeathMessage(BOLD + "" + WHITE + player.getName() + GRAY + " Was Slain by " + BOLD + "" + WHITE + killer.getName());
            DatabaseSetup.addPlayerDeaths(player, 1);
            DatabaseSetup.addPlayerKills(killer, 1);
        }
    }
}
