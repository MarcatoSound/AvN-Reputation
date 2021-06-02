package net.playavalon.avnrep.data.player;

import net.playavalon.avnrep.data.reputation.Faction;

import java.util.Collection;
import java.util.HashMap;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationManager {

    private HashMap<String, Reputation> reputations;

    public ReputationManager(AvalonPlayer ap) {
        reputations = new HashMap<>();

        for (Faction rep : plugin.repManager.getValues()) {
            reputations.putIfAbsent(rep.getName(), new Reputation(rep, ap));
        }

    }

    public Reputation getReputation(String namespace) {
        return reputations.get(namespace);
    }
    public Collection<Reputation> getReputations() {
        return reputations.values();
    }

}
