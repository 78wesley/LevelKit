package nl.Wesley.Main.Listeners;

import static org.bukkit.ChatColor.*;
import nl.Wesley.Main.Database.DatabaseSetup;
import nl.Wesley.Main.LevelKit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Wesley on 10/17/2016.
 */
public class ScoreboardListener {

    public static void scoreboardSetup(final Player player) {
        Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("LevelKit", "dummy");
        obj.setDisplayName(GOLD + "LevelKit");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score scoreName = obj.getScore(AQUA + "Name : " + GREEN + player.getName());
        scoreName.setScore(3);

        Score scoreLevel = obj.getScore(AQUA + "Level : " + GREEN + DatabaseSetup.getPlayerCustomLevel(player));
        scoreLevel.setScore(2);

        Score scoreKills = obj.getScore(AQUA + "Kills : " + GREEN + DatabaseSetup.getPlayerKills(player));
        scoreKills.setScore(1);

        Score scoreDeaths = obj.getScore(AQUA + "Deaths : " + GREEN + DatabaseSetup.getPlayerDeaths(player));
        scoreDeaths.setScore(0);

        player.setScoreboard(scoreboard);
    }

    public static void scoreboardUpdate(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                scoreboardSetup(player);
            }
        }.runTaskTimer(LevelKit.getPlugin(LevelKit.class),0, 20);
    }
}
