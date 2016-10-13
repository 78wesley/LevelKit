package nl.Wesley.Main;

import static org.bukkit.ChatColor.AQUA;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariDataSource;
import nl.Wesley.Main.Database.DatabaseSetup;
import nl.Wesley.Main.KitManager.KitManager;
import nl.Wesley.Main.Listeners.Listeners;
import nl.Wesley.Main.Profiles.Profile;
import nl.Wesley.Main.Shop.ShopManager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Wesley on 10/11/2016.
 */
public class LevelKit extends JavaPlugin {

    static Logger log = Logger.getLogger("Minecraft");
    String version = " v" + this.getDescription().getVersion();
    ConsoleCommandSender ConsoleSender = Bukkit.getServer().getConsoleSender();
    private HikariDataSource hikari;
    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void onEnable() {
        DatabaseSetup.setupHikari();
        ConsoleSender.sendMessage(AQUA + "LevelKit"+ version + " Has Been Enabled");
        RegisterCommandAndEvents();
        ShopManager.ShopSpawn();
    }

    @Override
    public void onDisable() {
        DatabaseSetup.disableHikari();
        ConsoleSender.sendMessage(AQUA + "LevelKit"+ version + " Has Been Disabled");
        ShopManager.ShopRemove();
    }

    private void RegisterCommandAndEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new KitManager(), this);
        pm.registerEvents(new ShopManager(), this);
        pm.registerEvents(new Listeners(), this);
    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    public Map<UUID, Profile> getProfiles() {
        return profiles;
    }

    public Profile getProfile(Player p) {
        return profiles.get(p.getUniqueId());
    }

    public Profile getRemovedProfile(Player p) {
        return profiles.remove(p.getUniqueId());
    }

    public String getUuid(Player p) {
        return p.getUniqueId().toString();
    }
}
