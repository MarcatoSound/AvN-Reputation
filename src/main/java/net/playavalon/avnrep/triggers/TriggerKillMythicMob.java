package net.playavalon.avnrep.triggers;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static net.playavalon.avncombatspigot.AvalonCombat.plugin;

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

            PersistentDataContainer data = ent.getPersistentDataContainer();
            if (data.has(new NamespacedKey(plugin, "FromSpawner"), PersistentDataType.INTEGER)) return;

            Player player = (Player) ent;

            updateRep(player, mob.getInternalName());
        }
    }
}
