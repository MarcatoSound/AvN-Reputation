package net.playavalon.avnrep.data.reputation;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationManager {

    private HashMap<String, Reputation> reputations;

    public ReputationManager() {
        reputations = new HashMap<>();

        ConfigurationSection configSec = plugin.config.getConfigurationSection("ReputationFactions");
        if (configSec == null) return;
        Set<String> reps = configSec.getKeys(false);

        for (String rep : reps) {
            Reputation repObj = new Reputation(configSec.getConfigurationSection(rep));
            reputations.put(repObj.getName(), repObj);
        }
    }

    public void put(Reputation rep) {
        reputations.putIfAbsent(rep.getName(), rep);
    }
    public void remove(String name) {
        reputations.remove(name);
    }
    public Reputation get(String name) {
        return reputations.get(name);
    }
    public Collection<Reputation> getValues() {
        return reputations.values();
    }

}
