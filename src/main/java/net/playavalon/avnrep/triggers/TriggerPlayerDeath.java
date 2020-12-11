package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TriggerPlayerDeath extends ReputationTrigger {

    public TriggerPlayerDeath() {
        super("PLAYER_DEATH");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        updateRep(player);
    }
}
