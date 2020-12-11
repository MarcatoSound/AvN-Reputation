package net.playavalon.avnrep.triggers;

import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.gathering.events.PlayerGatherItemEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerGatherAvalonItemHQ extends ReputationTrigger {

    public TriggerGatherAvalonItemHQ() {
        super("AVN_GATHER_HQ_[ITEM]");
    }

    @EventHandler
    public void onGatherItemHQ(PlayerGatherItemEvent e) {
        if (e.getQuality() < 1) return;
        Player player = e.getPlayer();

        AvalonItem aItem = e.getResult();

        updateRep(player, aItem.namespace.toUpperCase());
    }
}
