package pt.gmsgarcia.befree.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pt.gmsgarcia.befree.config.BeFreeConfig;
import pt.gmsgarcia.befree.utils.BeFreeUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeFreeCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        /*if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
           return true;
        }*/

        if (args.length > 0) {
            if (!args[0].equalsIgnoreCase("set") && !args[0].equalsIgnoreCase("give")) {
                sender.sendMessage("Invalid command.");
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {
                boolean item_updated = false;

                if (args.length <= 2) {
                    sender.sendMessage("Invalid argument length.");
                    return true;
                }

                switch (args[1]) {
                    case "elytra-item-name": {
                        BeFreeConfig.getInstance().setElytraItemName(args[2]);
                        sender.sendMessage("Elytra item name set to " + args[2]);
                        item_updated = true;
                        break;
                    }
                    case "booster-enabled": {
                        BeFreeConfig.getInstance().setBoosterEnabled(Boolean.parseBoolean(args[2]));
                        sender.sendMessage("Booster set to " + (Boolean.parseBoolean(args[2]) ? "enabled" : "disabled"));
                        item_updated = true;
                        break;
                    }
                    case "booster-item-name": {
                        BeFreeConfig.getInstance().setBoosterItemName(args[2]);
                        sender.sendMessage("Booster item name set to " + args[2]);
                        item_updated = true;
                        break;
                    }
                    case "booster-hotbar-slot": {
                        try {
                            BeFreeConfig.getInstance().setBoosterHotbarSlot(Integer.parseInt(args[2]));
                            sender.sendMessage("Booster hotbar slot set to " + args[2]);
                            item_updated = true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage("Invalid value.");
                        }
                        break;
                    }
                    case "booster-cooldown": {
                        try {
                            BeFreeConfig.getInstance().setBoosterCooldown(Integer.parseInt(args[2]));
                            sender.sendMessage("Booster cooldown set to " + args[2] + " seconds.");
                            item_updated = true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage("Invalid value.");
                        }
                        break;
                    }
                    case "effects-resistance": {
                        BeFreeConfig.getInstance().setGiveBoosterResistance(Boolean.parseBoolean(args[2]));
                        sender.sendMessage("Resistance effect set to " + (Boolean.parseBoolean(args[2]) ? "enabled" : "disabled"));
                        item_updated = true;
                        break;
                    }
                    default: {
                        sender.sendMessage("Invalid argument.");
                        break;
                    }
                }

                // update players inventory
                if (item_updated) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        BeFreeUtils.preparePlayer(player);
                    }
                }
            } else if (args[0].equalsIgnoreCase("give")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("You must be a player to use this command.");
                    return true;
                }

                BeFreeUtils.preparePlayer((Player) sender);
                sender.sendMessage("The items were given to you.");
            } else {
                // it should not land here!!!
            }

            return true;
        } else {
            sender.sendMessage("Be Free Plugin:\nVersion: 0.1.0");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            List<String> commands = List.of("set");
            return commands.stream().filter(option -> option.contains(args[0])).collect(Collectors.toList());
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            List<String> options = List.of("elytra-item-name", "booster-enabled", "booster-item-name", "booster-hotbar-slot", "booster-cooldown", "effects-resistance");
            return options.stream().filter(option -> option.contains(args[1])).collect(Collectors.toList());
        }
        return List.of();
    }
}
