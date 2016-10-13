package nl.Wesley.Main.KitManager;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
/**
 * Created by Wesley on 10/11/2016.
 */
public class KitManager implements Listener {
    // spawn een villager voor kit selection of via een sign
    // later word de menu KIT UPGRADE

    public static void KitSelectInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, RED + "Kit Selector");

        ItemStack StarterKit = new ItemStack(Material.STAINED_CLAY, 0);
        ItemMeta StarterKitmeta = StarterKit.getItemMeta();
        ArrayList<String> StarterKitList = new ArrayList<String>();
        StarterKitList.add("This is the fist kit when you started");
        StarterKitmeta.setDisplayName(BLUE + "Kit Starter");
        StarterKitmeta.setLore(StarterKitList);
        StarterKit.setItemMeta(StarterKitmeta);

        inv.setItem(0, StarterKit);
        p.openInventory(inv);
    }
    // weet niet of diet werkt zo 123 uit mijn hoofd

    @EventHandler
    public void KitSelectClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if (inv.getName().equalsIgnoreCase(RED + "Kit Selector")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BLUE + "Kit Starter") ) {
                Kits.Startter(p);
                p.closeInventory();
            }
            else if (e.getCurrentItem() == null) {
                return;
            }
        }
        if (e.getSlotType() == SlotType.OUTSIDE){
            return;
        }
    }

}
