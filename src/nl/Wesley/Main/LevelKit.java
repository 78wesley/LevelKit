package nl.Wesley.Main;

import static org.bukkit.ChatColor.AQUA;

import java.util.logging.Logger;

import nl.Wesley.Main.Database.DatabaseSetup;
import nl.Wesley.Main.KitManager.KitManager;
import nl.Wesley.Main.Listeners.Listeners;
import nl.Wesley.Main.Shop.ShopManager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Wesley on 10/11/2016.
 */
public class LevelKit extends JavaPlugin {

    static Logger log = Logger.getLogger("Minecraft");
    String version = " v" + this.getDescription().getVersion();
    ConsoleCommandSender ConsoleSender = Bukkit.getServer().getConsoleSender();

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
        DatabaseSetup.disableConnection();
    }
    private void RegisterCommandAndEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new KitManager(), this);
        pm.registerEvents(new ShopManager(), this);
        pm.registerEvents(new Listeners(), this);
    }

}
