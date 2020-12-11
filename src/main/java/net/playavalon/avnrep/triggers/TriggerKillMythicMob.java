package net.playavalon.avnrep.triggers;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerKillMythicMob extends ReputationTrigger {

    public TriggerKillMythicMob() {
        super("KILL_MYTHIC_[MOB]");
    }

    @EventHandler
    public void onKillMythic(MythicMobDeathEvent e) {
        if (!(e.getKiller() instanceof Player)) return;
        Player player = (Player)e.getKiller();

        MythicMob mob = e.getMobType();

        updateRep(player, mob.getInternalName());
    }
}
