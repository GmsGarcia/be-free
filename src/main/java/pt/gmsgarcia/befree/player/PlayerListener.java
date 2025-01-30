package pt.gmsgarcia.befree.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import pt.gmsgarcia.befree.BeFree;
import pt.gmsgarcia.befree.config.BeFreeConfig;
import pt.gmsgarcia.befree.utils.BeFreeUtils;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BeFreeUtils.preparePlayer(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        BeFreeUtils.preparePlayer(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        BeFreeConfig config = BeFreeConfig.getInstance();

        if (event.getWhoClicked() instanceof Player) {
            ItemStack item = event.getCurrentItem();
            InventoryType.SlotType slot_type = event.getSlotType();
            int slot = event.getSlot();

            // did the player click on the elytra?
            // kinda redundant check if the item is a elytra tho :/
            if (slot_type.equals(InventoryType.SlotType.ARMOR) && item.equals(BeFreeUtils.getUnbreakableElytra())) {
                event.setCancelled(true);
                return;
            }

            if (config.isBoosterEnabled()) {
                // did the player click on the firework rockets?
                if (slot_type.equals(InventoryType.SlotType.QUICKBAR) && slot == config.getBoosterHotbarSlot()) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        // did the player tried to drop the elytra?
        if (event.getItemDrop().getItemStack().equals(BeFreeUtils.getUnbreakableElytra())) {
            event.setCancelled(true);
            return;
        }

        // did the player tried to drop the firework rockets?
        if (event.getItemDrop().getItemStack().equals(BeFreeUtils.getUnbreakableElytra())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        BeFreeConfig config = BeFreeConfig.getInstance();

        if (event.getItem() == null) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (config.isBoosterEnabled()) {
            //if (item.isSimilar(new ItemStack(Material.FIREWORK_ROCKET))) {
            if (player.getInventory().getHeldItemSlot() == config.getBoosterHotbarSlot()) {
                Action action = event.getAction();

                // prevent the firework to be used on blocks
                if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    return;
                }

                if (player.hasCooldown(item)) {
                    event.setCancelled(true);
                    return;
                }

                if (player.isGliding()) {
                    if (action.equals(Action.RIGHT_CLICK_AIR)) {
                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.runTask(BeFree.getInstance(), () -> {
                            player.getInventory().setItem(config.getBoosterHotbarSlot(), BeFreeUtils.getCustomFireworkRocket());
                            player.setCooldown(Material.FIREWORK_ROCKET, 20 * config.getBoosterCooldown());
                        });
                    }
                }
            }
        }
    }
}