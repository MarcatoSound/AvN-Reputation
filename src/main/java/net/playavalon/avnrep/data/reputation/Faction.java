package net.playavalon.avnrep.data.reputation;

import net.playavalon.avnitems.AvalonItems;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.utility.ItemUtils;
import net.playavalon.avnrep.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.playavalon.avnrep.AvNRep.plugin;

public class Faction {

    private String namespace;
    private String displayName;

    private double minRep;
    private double maxRep;
    private int maxLevel;
    private double curve;

    private boolean currencyEnabled;
    private ItemStack currency;
    private String currencyName;
    private int currencyRate;

    private HashMap<String, Double> sources;
    private HashMap<Integer, List<String>> levelCommands;

    public Faction(ConfigurationSection config) {
        if (config == null) return;
        namespace = config.getName().toLowerCase();
        displayName = config.getString("DisplayName", namespace);
        minRep = config.getDouble("FirstLevelCost", 25);
        maxRep = config.getDouble("LastLevelCost", 5000);
        maxLevel = config.getInt("MaxLevel", 20);
        curve = config.getDouble("RepCurve", -0.5);

        // Faction currency handling
        currencyEnabled = config.getBoolean("Currency.Enabled", false);
        if (currencyEnabled) {
            String itemName = config.getString("Currency.Item", "EMERALD");
            assert itemName != null;
            Material mat = Material.matchMaterial(itemName);
            if (plugin.avni != null) {
                if (mat != null) {
                    currency = new ItemStack(mat);
                } else {
                    AvalonItem aItem = plugin.avni.itemManager.getItem(itemName);
                    if (aItem != null) {
                        currency = aItem.item;
                        currencyName = aItem.fullName;
                    }
                    else {
                        currency = new ItemStack(Material.EMERALD);
                        currencyName = Material.EMERALD.toString();
                    }
                }
            } else {
                if (mat != null) {
                    currency = new ItemStack(mat);
                    currencyName = mat.name();
                }
                else {
                    currency = new ItemStack(Material.EMERALD);
                    currencyName = Material.EMERALD.toString();
                }
            }
            currencyRate = Math.max(config.getInt("Currency.CurrencyRate", 25), 1);
        }

        sources = new HashMap<>();
        List<String> sourceList = config.getStringList("Sources");
        for (String source : sourceList) {
            String[] pair = source.split(":");
            String trigger = pair[0].toUpperCase();
            sources.put(trigger, Double.parseDouble(pair[1]));
        }

        levelCommands = new HashMap<>();
        ConfigurationSection levels = config.getConfigurationSection("Commands");
        if (levels != null) {
            List<String> commands;
            for (String path : levels.getKeys(false)) {
                commands = levels.getStringList(path);
                int level = Integer.parseInt(path);
                levelCommands.put(level, commands);
            }
        }

        System.out.println(this);

    }

    public String getName() {
        return namespace;
    }
    public String getDisplayName() {
        return Utils.colorize(displayName);
    }
    public double getMinRep() {
        return minRep;
    }
    public double getMaxRep() {
        return maxRep;
    }
    public int getMaxLevel() {
        return maxLevel;
    }
    public double getCurve() {
        return curve;
    }
    public boolean hasCurrency() {
        return currencyEnabled;
    }
    public ItemStack getCurrency() {
        return currency;
    }
    public String getCurrencyName() {
        return currencyName;
    }
    public int getCurrencyRate() {
        return currencyRate;
    }
    public HashMap<String, Double> getSources() {
        return sources;
    }
    public double getSourceValue(String source) {
        if (!hasSource(source)) return 0; // We return 0 since it's possible for sources to have negative values.
        return sources.get(source.toUpperCase());
    }

    public boolean hasSource(String source) {
        return sources.containsKey(source.toUpperCase());
    }

    public HashMap<Integer, List<String>> getLevelCommands() {
        return levelCommands;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(namespace.toUpperCase());
        sb.append("\n-- Max Level: " + maxLevel);
        sb.append("\n-- Rep Curve: " + curve);
        sb.append("\n-- Min Rep: " + minRep);
        sb.append("\n-- Max Rep: " + maxRep);
        if (currencyEnabled) {
            sb.append("\n-- Currency: " + currencyName);
            sb.append("\n-- Currency Rate: " + currencyRate);
        }
        sb.append("\n-- Rep Sources: ");
        for (Map.Entry<String, Double> pair : sources.entrySet()) {
            sb.append("\n---- " + pair.getKey() + ": " + pair.getValue());
        }

        return sb.toString();
    }
}
