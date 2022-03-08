package net.playavalon.avnrep.triggers;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerKillMythicMob extends ReputationTrigger {

    public TriggerKillMythicMob() {
        super("KILL_MYTHIC_[MOB]");
    }

    @EventHandler
    public void onKillMythic(MythicMobDeathEvent e) {
        MythicMob mob = e.getMobType();
        if (e.getMob() == null) return;
        if (e.getMob().getThreatTable() == null) return;
        for (AbstractEntity aEnt : e.getMob().getThreatTable().getAllThreatTargets()) {
            Entity ent = aEnt.getBukkitEntity();
            if (!(ent instanceof Player)) continue;

            Player player = (Player) ent;

            updateRep(player, mob.getInternalName());
        }
    }
}
