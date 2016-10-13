package nl.Wesley.Main.Profiles;

import org.bukkit.entity.Player;
import java.util.UUID;

/**
 * Created by Wesley on 10/11/2016.
 */
public class Profile {
    private UUID uuid;
    private String name;

    private int Playerkills;
    private int Level;

    private boolean loaded;

    public Profile(Player p) {
        this.setUuid(p.getUniqueId());
        this.setName(p.getName());
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerkills() {
        return Playerkills;
    }

    public void setPlayerkills(int playerkills) {
        Playerkills = playerkills;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
