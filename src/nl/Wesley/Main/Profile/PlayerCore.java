package nl.Wesley.Main.Profile;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Wesley on 10/16/2016.
 */
public class PlayerCore  {

    private String name;
    private UUID uuid;

    private int serverJoined;

    public Player getPlayer() {
        return Bukkit.getPlayer(name);
    }

    public UUID getUUID() {
        return Bukkit.getPlayer(name).getUniqueId();
    }

    public int getServerJoined() {
        return serverJoined;
    }

}
