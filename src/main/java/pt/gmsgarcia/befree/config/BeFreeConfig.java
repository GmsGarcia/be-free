package pt.gmsgarcia.befree.config;

import org.bukkit.configuration.file.YamlConfiguration;
import pt.gmsgarcia.befree.BeFree;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BeFreeConfig {
    private static final BeFreeConfig instance = new BeFreeConfig();

    private File file;
    private YamlConfiguration config;

    Map<String, Object> elytra = new HashMap<>();
    Map<String, Object> booster = new HashMap<>();
    Map<String, Object> effects = new HashMap<>();

    private BeFreeConfig() {
    }

    public void load() {
        file = new File(BeFree.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) {
            BeFree.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        elytra.put("item-name", String.valueOf(config.getString("elytra.item-name")));
        elytra.put("unbreakable", Boolean.valueOf(config.getString("elytra.unbreakable")));

        booster.put("enabled", Boolean.valueOf(config.getString("booster.enabled")));
        booster.put("item-name", String.valueOf(config.getString("booster.item-name")));
        booster.put("hotbar-slot", Integer.valueOf(config.getString("booster.hotbar-slot")));
        booster.put("cooldown", Integer.valueOf(config.getString("booster.cooldown")));

        effects.put("resistance", Boolean.valueOf(config.getString("effects.resistance")));
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public String getElytraItemName() {
        return (String) elytra.get("item-name");
    }

    public void setElytraItemName(String item_name) {
        elytra.put("item-name", item_name);
    }

    public Boolean isBoosterEnabled() {
        return (Boolean) booster.get("enabled");
    }

    public void setBoosterEnabled(Boolean enabled) {
        booster.put("enabled", enabled);
    }

    public String getBoosterItemName() {
        return (String) booster.get("item-name");
    }

    public void setBoosterItemName(String item_name) {
        booster.put("item-name", item_name);
    }

    public Integer getBoosterHotbarSlot() {
        return (Integer) booster.get("hotbar-slot");
    }

    public void setBoosterHotbarSlot(Integer hotbar_slot) {
        booster.put("hotbar-slot", hotbar_slot);
    }

    public Integer getBoosterCooldown() {
        return (Integer) booster.get("cooldown");
    }

    public void setBoosterCooldown(Integer cooldown) {
        booster.put("cooldown", cooldown);
    }

    public Boolean giveBoosterResistance() {
        return (Boolean) effects.get("resistance");
    }

    public void setGiveBoosterResistance(Boolean give_resistance) {
       effects.put("resistance", give_resistance);
    }

    public static BeFreeConfig getInstance() {
        return instance;
    }
}
