package net.playavalon.avnrep.data.reputation;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static net.playavalon.avnrep.AvNRep.plugin;

public class FactionManager {

    private HashMap<String, Faction> reputations;

    public FactionManager() {
        reputations = new HashMap<>();

        ConfigurationSection configSec = plugin.config.getConfigurationSection("ReputationFactions");
        if (configSec == null) return;
        Set<String> reps = configSec.getKeys(false);

        for (String rep : reps) {
            Faction repObj = new Faction(configSec.getConfigurationSection(rep));
            reputations.put(repObj.getName(), repObj);
        }
    }

    public void put(Faction rep) {
        reputations.putIfAbsent(rep.getName(), rep);
    }
    public void remove(String name) {
        reputations.remove(name);
    }
    public Faction get(String name) {
        return reputations.get(name);
    }
    public Collection<Faction> getValues() {
        return reputations.values();
    }

}
