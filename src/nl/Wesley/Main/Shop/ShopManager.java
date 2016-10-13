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

import java.util.ArrayList;

/**
 * Created by Wesley on 10/11/2016.
 */
public class ShopManager implements Listener{

    private static ArrayList<Entity> SpawnList = new ArrayList<Entity>();

    public static void ShopRemove() {
        for (Entity entity : SpawnList) {
            entity.remove();
        }
    }

    public static void ShopSpawn() {
        World world = Bukkit.getWorlds().get(0);
        Location loc = new Location(world, 59, 65, 250);
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
        if (villager.isInvulnerable()) {
            return;
        }
        SpawnList.add(villager);
    }

    @EventHandler
    public void ShopEntityClickEvent(PlayerInteractEntityEvent e) {
        Entity clicked = e.getRightClicked();
        Player p = e.getPlayer();
        if (e.getRightClicked().getName().equalsIgnoreCase(RED + "Kit Selector")){
            e.setCancelled(true);
            if (clicked.getCustomName().equals(RED + "Kit Selector")) {
                KitManager.KitSelectInventory(p);
            }
        }
    }

    @EventHandler
    public void HitShopEvent(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER) {
            if (e.getEntity().getName().equalsIgnoreCase(RED + "Kit Selector")) {
                e.getEntity().setInvulnerable(true);
                e.getEntity().getLastDamageCause().setCancelled(true);
            }
        }
    }
}

