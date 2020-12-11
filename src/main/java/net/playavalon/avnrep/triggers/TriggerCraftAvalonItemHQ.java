package net.playavalon.avnrep.triggers;

import net.playavalon.avnitems.crafting.events.PlayerFinishCraftEvent;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerCraftAvalonItemHQ extends ReputationTrigger {

    public TriggerCraftAvalonItemHQ() {
        super("AVN_CRAFT_HQ_[ITEM]");
    }

    @EventHandler
    public void onCraftItemHQ(PlayerFinishCraftEvent e) {
        if (e.getQuality() < 1) return;
        Player player = e.getPlayer();

        AvalonItem aItem = e.getResult();

        String type = "AVN_CRAFT_HQ_" + aItem.type.name();

        updateRep(player, aItem.namespace.toUpperCase(), new String[] {type});
    }
}
