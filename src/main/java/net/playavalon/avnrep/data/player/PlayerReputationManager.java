package net.playavalon.avnrep.data.player;

import net.playavalon.avnrep.data.reputation.Reputation;

import java.util.Collection;
import java.util.HashMap;

import static net.playavalon.avnrep.AvNRep.plugin;

public class PlayerReputationManager {

    private HashMap<String, PlayerReputation> reputations;

    public PlayerReputationManager(AvalonPlayer ap) {
        reputations = new HashMap<>();

        for (Reputation rep : plugin.repManager.getValues()) {
            reputations.putIfAbsent(rep.getName(), new PlayerReputation(rep, ap));
        }

    }

    public PlayerReputation getReputation(String namespace) {
        return reputations.get(namespace);
    }
    public Collection<PlayerReputation> getReputations() {
        return reputations.values();
    }

}
