package net.playavalon.avnrep.data.reputation;

import net.playavalon.avnrep.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reputation {

    private String namespace;

    private double minRep;
    private double maxRep;
    private int maxLevel;
    private double curve;
    private HashMap<String, Double> sources;

    public Reputation(ConfigurationSection config) {
        if (config == null) return;
        namespace = config.getName().toLowerCase();
        minRep = config.getDouble("FirstLevelCost", 25);
        maxRep = config.getDouble("LastLevelCost", 5000);
        maxLevel = config.getInt("MaxLevel", 20);
        curve = config.getDouble("RepCurve", -0.5);

        sources = new HashMap<>();
        List<String> sourceList = config.getStringList("Sources");
        for (String source : sourceList) {
            String[] pair = source.split(":");
            String trigger = pair[0].toUpperCase();
            sources.put(trigger, Double.parseDouble(pair[1]));
        }

        System.out.println(this);

    }

    public String getName() {
        return namespace;
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
    public HashMap<String, Double> getSources() {
        return sources;
    }
    public double getSourceValue(String source) {
        if (!hasSource(source)) return 0;
        return sources.get(source.toUpperCase());
    }

    public boolean hasSource(String source) {
        return sources.containsKey(source.toUpperCase());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(namespace.toUpperCase());
        sb.append("\n-- Max Level: " + maxLevel);
        sb.append("\n-- Rep Curve: " + curve);
        sb.append("\n-- Min Rep: " + minRep);
        sb.append("\n-- Lvl 5 Rep: " + Utils.calcLevelCost(this, 5));
        sb.append("\n-- Lvl 10 Rep: " + Utils.calcLevelCost(this, 10));
        sb.append("\n-- Max Rep: " + maxRep);
        sb.append("\n-- Rep Sources: ");
        for (Map.Entry<String, Double> pair : sources.entrySet()) {
            sb.append("\n---- " + pair.getKey() + ": " + pair.getValue());
        }

        return sb.toString();
    }
}
