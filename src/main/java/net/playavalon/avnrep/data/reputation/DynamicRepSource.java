package net.playavalon.avnrep.data.reputation;

import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class DynamicRepSource {

    private final String namespace;
    private final HashMap<String, RepSource> sources;
    private RepSource currentSource;

    public DynamicRepSource(ConfigurationSection data) {
        this.namespace = data.getName();

        this.sources = new HashMap<>();
        for (String key : data.getKeys(false)) {
            ConfigurationSection repSourceConfig = data.getConfigurationSection(key);
            if (repSourceConfig == null) continue;
            sources.put(repSourceConfig.getName(), new RepSource(repSourceConfig));
        }

    }

    public void init() {

    }

    public RepSource next() {
        List<RepSource> repSources = new ArrayList<>(sources.values());

        Random rand = new Random();

        currentSource = repSources.get(rand.nextInt(repSources.size()-1));
        return currentSource;
    }

    public RepSource getCurrentSource() {
        return currentSource;
    }

    public String getNamespace() {
        return namespace;
    }
}
