package pt.gmsgarcia.befree;

import org.bukkit.plugin.java.JavaPlugin;
import pt.gmsgarcia.befree.commands.BeFreeCommand;
import pt.gmsgarcia.befree.player.PlayerListener;
import pt.gmsgarcia.befree.config.BeFreeConfig;

public final class BeFree extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("befree").setExecutor(new BeFreeCommand());
        BeFreeConfig.getInstance().load();
        getLogger().info("Be Free: Plugin enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BeFree getInstance() {
        return getPlugin(BeFree.class);
    }
}
