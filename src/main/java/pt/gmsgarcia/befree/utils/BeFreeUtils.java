package pt.gmsgarcia.befree.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import pt.gmsgarcia.befree.BeFree;
import pt.gmsgarcia.befree.Keys;
import pt.gmsgarcia.befree.config.BeFreeConfig;

public final class BeFreeUtils {
    public static void preparePlayer(Player player) {
        BeFreeConfig config = BeFreeConfig.getInstance();

        // remove booster
        int current_booster_slot = player.getInventory().first(getCustomFireworkRocket());
        if (current_booster_slot != -1) {
            player.getInventory().setItem(current_booster_slot, ItemStack.of(Material.AIR));
        }

        // remove elytra and resistance effect
        player.getInventory().setChestplate(ItemStack.of(Material.AIR));
        player.removePotionEffect(PotionEffectType.RESISTANCE);

        // give unbreakable elytra and firework rockets
        player.getInventory().setChestplate(getUnbreakableElytra());

        if (config.isBoosterEnabled()) {
            player.getInventory().setItem(config.getBoosterHotbarSlot(), getCustomFireworkRocket());
        }

        // give resistance effect
        if (config.giveBoosterResistance()) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTask(BeFree.getInstance(), () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 100));
            });
        }
    }

    public static ItemStack getUnbreakableElytra() {
        BeFreeConfig config = BeFreeConfig.getInstance();
        ItemStack item = new ItemStack(Material.ELYTRA);

        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + config.getElytraItemName());
        meta.getPersistentDataContainer().set(Keys.UNBREAKABLE_ELYTRA, PersistentDataType.BOOLEAN, true);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomFireworkRocket() {
        BeFreeConfig config = BeFreeConfig.getInstance();
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + config.getBoosterItemName());
        meta.getPersistentDataContainer().set(Keys.CUSTOM_FIREWORK_ROCKET, PersistentDataType.BOOLEAN, true);

        item.setItemMeta(meta);
        return item;
    }
}
