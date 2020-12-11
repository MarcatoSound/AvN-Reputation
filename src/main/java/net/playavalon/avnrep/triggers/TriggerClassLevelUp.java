package net.playavalon.avnrep.triggers;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.playavalon.avncombatspigot.api.PlayerChangeLevelEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerClassLevelUp extends ReputationTrigger {

    public TriggerClassLevelUp() {
        super("CLASS_LEVELUP_[ANY]");
    }

    @EventHandler
    public void onClassLevelUp(PlayerChangeLevelEvent e) {
        if (e.getNewLevel() <= e.getOldLevel()) return;
        Player player = e.getPlayer();

        updateRep(player, String.valueOf(e.getNewLevel()), "CLASS_LEVELUP");
    }

}
