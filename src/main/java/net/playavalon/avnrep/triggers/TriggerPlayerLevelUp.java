package net.playavalon.avnrep.triggers;

import net.playavalon.avncombatspigot.api.PlayerChangeLevelEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerPlayerLevelUp extends ReputationTrigger {

    public TriggerPlayerLevelUp() {
        super("PLAYER_LEVELUP");
    }

    @EventHandler
    public void onPlayerLevelUp(PlayerChangeLevelEvent e) {
        if (e.getNewLevel() <= e.getOldLevel()) return;
        Player player = e.getPlayer();

        updateRep(player);
    }
}
