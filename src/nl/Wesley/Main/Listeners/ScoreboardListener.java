package nl.Wesley.Main.Listeners;

import nl.Wesley.Main.Database.DatabaseSetup;
import nl.Wesley.Main.LevelKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        obj.setDisplayName(ChatColor.GOLD + "LevelKit");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score1 = obj.getScore(ChatColor.AQUA + "Level : " + DatabaseSetup.getIntPlayerCustomLevel(player));
        score1.setScore(0);

        Score score2 = obj.getScore(ChatColor.AQUA + "Name : " + player.getName());
        score2.setScore(1);
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
