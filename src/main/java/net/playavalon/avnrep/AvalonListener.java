package net.playavalon.avnrep;

import net.playavalon.avnrep.api.events.PlayerGainReputationLevelEvent;
import net.playavalon.avnrep.data.player.Reputation;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.playavalon.avnrep.AvNRep.plugin;

public class AvalonListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.playerManager.put(event.getPlayer());
    }

    @EventHandler
    public void onReputationLevel(PlayerGainReputationLevelEvent event) {
        Reputation pRep = event.getReputation();
        Faction faction = pRep.getFaction();
        int level = event.getNewLevel();
        if (!faction.getLevelCommands().containsKey(level)) return;

        pRep.runLevelCommands(level);
    }
}
