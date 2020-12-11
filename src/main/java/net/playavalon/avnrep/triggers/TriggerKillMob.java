package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class TriggerKillMob extends ReputationTrigger {

    public TriggerKillMob() {
        super("KILL_[MOB]");
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent e) {
        LivingEntity ent = e.getEntity();
        if (ent.getKiller() == null) return;
        Player player = ent.getKiller();

        EntityType entType = ent.getType();

        String mobType = "KILL_" + Utils.getEntityGroup(ent).toUpperCase();

        updateRep(player, entType.name(), new String[] {mobType});

    }

}
