package nl.Wesley.Main.Shop;

import static org.bukkit.ChatColor.RED;
import java.util.ArrayList;

import nl.Wesley.Main.KitManager.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Wesley on 10/11/2016.
 */
public class ShopManager implements Listener{

    private static ArrayList<Entity> spawnList = new ArrayList<>();

    public static void shopRemove() {
        for (Entity entity : spawnList) {
            entity.remove();
        }
    }
    public static void shopClear() {
        spawnList.clear();
    }

    public static void shopSpawn() {
        World world = Bukkit.getWorlds().get(0);
        Location loc = new Location(world, 59, 65, 250, 0, 0);
        Villager villager = (Villager) world.spawnEntity(loc, EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.BLACKSMITH);
        villager.setAI(false);
        villager.setAge(1);
        villager.setAgeLock(true);
        villager.setCustomName(RED + "Kit Selector");
        villager.setCustomNameVisible(true);
        villager.setSilent(true);
        villager.setRemoveWhenFarAway(false);
        villager.setBreed(false);
        villager.setCanPickupItems(false);
        villager.setGliding(false);
        villager.setGlowing(false);
        villager.setGravity(true);
        villager.setCollidable(false);
        villager.setInvulnerable(true);
        spawnList.add(villager);
    }

    @EventHandler
    public void ShopEntityClickEvent(PlayerInteractEntityEvent e) {
        Entity clicked = e.getRightClicked();
        Player player = e.getPlayer();
        if (e.getRightClicked().getName().equalsIgnoreCase(RED + "Kit Selector")){
            e.setCancelled(true);
            if (clicked.getCustomName().equals(RED + "Kit Selector")) {
                KitManager.KitSelectInventory(player);
            }
        }
    }

    @EventHandler
    public void HitShopEvent(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER) {
            if (e.getEntity().getName().equalsIgnoreCase(RED + "Kit Selector")) {
                e.getEntity().setInvulnerable(true);
            }
        }
    }
}

