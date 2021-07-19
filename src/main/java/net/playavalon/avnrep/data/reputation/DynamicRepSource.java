package net.playavalon.avnrep.data.reputation;

import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class DynamicRepSource {

    private String namespace;
    private HashMap<String, Double> sources;
    private Map.Entry<String, Double> currentSource;

    public DynamicRepSource(String namespace, List<String> sources) {
        this.namespace = namespace;

        this.sources = new HashMap<>();
        for (String source : sources) {
            String[] pair = source.split(":");
            String trigger = pair[0].toUpperCase();
            this.sources.put(trigger, Double.parseDouble(pair[1]));
        }

    }

    public void init() {

    }

    public Map.Entry<String, Double> next() {
        Set<Map.Entry<String, Double>> pairs = sources.entrySet();

        Random rand = new Random();

        List<Map.Entry<String, Double>> pairsList = new ArrayList<>(pairs);

        currentSource = pairsList.get(rand.nextInt(pairs.size()-1));
        return currentSource;
    }

    public Map.Entry<String, Double> getCurrentSource() {
        return currentSource;
    }

    public String getNamespace() {
        return namespace;
    }
}
