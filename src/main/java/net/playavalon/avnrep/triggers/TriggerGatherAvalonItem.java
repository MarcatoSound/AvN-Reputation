package net.playavalon.avnrep.triggers;

import net.playavalon.avnitems.crafting.events.PlayerFinishCraftEvent;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.gathering.events.PlayerGatherItemEvent;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerGatherAvalonItem extends ReputationTrigger {

    public TriggerGatherAvalonItem() {
        super("AVN_GATHER_[ITEM]");
    }

    @EventHandler
    public void onGatherItem(PlayerGatherItemEvent e) {
        Player player = e.getPlayer();

        AvalonItem aItem = e.getResult();

        updateRep(player, aItem.namespace.toUpperCase());
    }
}
