package nl.Wesley.Main;

import static org.bukkit.ChatColor.AQUA;

import java.sql.*;
import java.util.logging.Logger;
import nl.Wesley.Main.KitManager.KitManager;
import nl.Wesley.Main.Listeners.Listeners;
import nl.Wesley.Main.Shop.ShopManager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Wesley on 10/11/2016.
 */
public class LevelKit extends JavaPlugin implements Listener{

    public Plugin plugin = this;
    static Logger log = Logger.getLogger("Minecraft");
    String version = " v" + this.getDescription().getVersion();
    ConsoleCommandSender ConsoleSender = Bukkit.getServer().getConsoleSender();
    private static Connection connection;

    @Override
    public void onEnable() {
        ConsoleSender.sendMessage(AQUA + "LevelKit"+ version + " Has Been Enabled");
        RegisterCommandAndEvents();
        ShopManager.ShopSpawn();
    }

    @Override
    public void onDisable() {
        ConsoleSender.sendMessage(AQUA + "LevelKit"+ version + " Has Been Disabled");
        ShopManager.ShopRemove();
        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void RegisterCommandAndEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new KitManager(), this);
        pm.registerEvents(new ShopManager(), this);
        pm.registerEvents(new Listeners(), this);
        pm.registerEvents(this, this);
    }

    public synchronized static void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minecraft", "root", "toor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static boolean playerDataContainsPlayer(Player p) {
        try {
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player_name=?;");
            sql.setString(1, p.getName());
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
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        openConnection();
        try {
            int player_serverjoined = 0;

            if (playerDataContainsPlayer(event.getPlayer())) {
                PreparedStatement sql = connection.prepareStatement("SELECT player_serverjoined FROM `player_data` WHERE player_name=?;");
                sql.setString(1, event.getPlayer().getName());
                ResultSet result = sql.executeQuery();
                result.next();
                player_serverjoined = result.getInt("player_serverjoined");
                PreparedStatement player_serverjoinedUpdate = connection.prepareStatement("UPDATE `player_data` SET player_serverjoined=? WHERE player_name=?;");

                player_serverjoinedUpdate.setInt(1, player_serverjoined + 1);
                player_serverjoinedUpdate.setString(2, event.getPlayer().getName());
                player_serverjoinedUpdate.executeUpdate();

                player_serverjoinedUpdate.close();
                sql.close();
                result.close();
            } else {
                Bukkit.getServer().broadcastMessage("ELSE 123");
                PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `player_serverjoined` values(?,?,0,0,0);");
                newPlayer.setString(1, event.getPlayer().getName());
                newPlayer.setString(2, event.getPlayer().getUniqueId().toString());
                newPlayer.execute();
                newPlayer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
