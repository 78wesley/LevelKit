package nl.Wesley.Main.KitManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Wesley on 10/11/2016.
 */
public class Kits {

    static void Starter(Player player) {
        ItemStack Helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack Shield = new ItemStack(Material.SHIELD);
        ItemStack Bow = new ItemStack(Material.BOW);
        Bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemStack Sword = new ItemStack(Material.STONE_SWORD);
        ItemStack Arrow = new ItemStack(Material.ARROW);

        player.getInventory().setHelmet(Helmet);
        player.getInventory().setChestplate(Chestplate);
        player.getInventory().setLeggings(Leggings);
        player.getInventory().setBoots(Boots);
        player.getInventory().setItemInOffHand(Shield); // SHIELD IN OFFHAND
        player.getInventory().setItem(0, Bow);
        player.getInventory().setItem(1, Sword);
        player.getInventory().setItem(9, Arrow);
    }
}
