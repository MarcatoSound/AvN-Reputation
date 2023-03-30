package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static net.playavalon.avncombatspigot.AvalonCombat.plugin;

public class TriggerKillMob extends ReputationTrigger {

    public TriggerKillMob() {
        super("KILL_[MOB]");
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent e) {
        LivingEntity ent = e.getEntity();
        if (ent.getKiller() == null) return;
        Player player = ent.getKiller();

        PersistentDataContainer data = ent.getPersistentDataContainer();
        if (data.has(new NamespacedKey(plugin, "FromSpawner"), PersistentDataType.INTEGER)) return;

        EntityType entType = ent.getType();

        String mobType = "KILL_" + Utils.getEntityGroup(ent).toUpperCase();

        int mobLevel = Math.max(1, Utils.getMobLevel(ent));
        double bonusRepMod = (double)mobLevel * 0.1;

        updateRep(player, entType.name(), new String[] {mobType}, bonusRepMod + 1);

    }

}
