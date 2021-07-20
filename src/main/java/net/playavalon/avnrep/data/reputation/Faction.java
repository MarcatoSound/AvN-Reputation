package net.playavalon.avnrep.data.reputation;

import net.playavalon.avnitems.AvalonItems;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.utility.ItemUtils;
import net.playavalon.avnrep.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.util.*;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
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

    private HashMap<String, RepSource> repSources;
    private ArrayList<DynamicRepSource> dynamicSources;
    private HashMap<Integer, List<String>> levelCommands;

    private BukkitRunnable dynamicSourceCycle;
    private long currentDate;

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
            System.out.println("Material: " + mat);

            // If AvNi is present...
            if (plugin.avni != null) {
                if (mat != null) {
                    currency = new ItemStack(mat);
                    currencyName = mat.toString();
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
                    currencyName = mat.toString();
                }
                else {
                    currency = new ItemStack(Material.EMERALD);
                    currencyName = Material.EMERALD.toString();
                }
            }
            currencyRate = Math.max(config.getInt("Currency.CurrencyRate", 25), 1);
        }

        repSources = new HashMap<>();
        ConfigurationSection repSourceConfigs = config.getConfigurationSection("Sources");
        if (repSourceConfigs != null) {
            for (String key : repSourceConfigs.getKeys(false)) {
                ConfigurationSection repSourceConfig = repSourceConfigs.getConfigurationSection(key);
                if (repSourceConfig == null) continue;
                repSources.put(repSourceConfig.getName(), new RepSource(repSourceConfig));
            }
        }

        dynamicSources = new ArrayList<>();
        ConfigurationSection dynRepConfig = config.getConfigurationSection("DynamicSources");
        if (dynRepConfig != null) {
            for (String key : dynRepConfig.getKeys(false)) {
                ConfigurationSection dynSourceConfig = dynRepConfig.getConfigurationSection(key);
                if (dynSourceConfig == null) continue;
                dynamicSources.add(new DynamicRepSource(dynSourceConfig));
            }
        }

        currentDate = (System.currentTimeMillis() / 60000) / 1440;
        dynamicSourceCycle = new BukkitRunnable() {
            @Override
            public void run() {
                long newDate = (System.currentTimeMillis() / 60000) / 1440;
                if (currentDate == newDate) return;
                currentDate = newDate;

                System.out.println(debugPrefix + "Good morning! Updating dynamic reputation sources for factions...");

                randomizeDynamicSources();
            }
        };
        dynamicSourceCycle.runTaskTimerAsynchronously(plugin, 0, 1200);

        randomizeDynamicSources();

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


    public void randomizeDynamicSources() {
        System.out.println(debugPrefix + "Updating dynamic rep sources for faction '" + namespace + "': ");
        for (DynamicRepSource dynamicSource : dynamicSources) {
            // Remove the old reputation source
            if (dynamicSource.getCurrentSource() != null)
                repSources.remove(dynamicSource.getCurrentSource().getTrigger());

            // Update and retrieve a new reputation source
            RepSource source = dynamicSource.next();

            System.out.println(debugPrefix + "- " + dynamicSource.getNamespace() + ": " + source.getTrigger());

            // Register the new source with this faction
            repSources.put(source.getTrigger(), source);
        }
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
    public HashMap<String, RepSource> getRepSources() {
        return repSources;
    }
    public double getSourceValue(String source) {
        if (!hasSource(source)) return 0; // We return 0 since it's possible for sources to have negative values.

        RepSource repSource = repSources.get(source.toUpperCase());

        if (repSource == null) return 0;

        return repSource.getValue();
    }

    public boolean hasSource(String source) {
        return repSources.containsKey(source.toUpperCase());
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
        for (Map.Entry<String, RepSource> pair : repSources.entrySet()) {
            sb.append("\n---- " + pair.getKey() + ": " + pair.getValue().getValue());
        }

        return sb.toString();
    }
}
