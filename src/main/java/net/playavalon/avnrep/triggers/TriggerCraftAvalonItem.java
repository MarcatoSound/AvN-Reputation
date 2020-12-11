package net.playavalon.avnrep.triggers;

import net.playavalon.avnitems.crafting.events.PlayerFinishCraftEvent;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TriggerCraftAvalonItem extends ReputationTrigger {

    public TriggerCraftAvalonItem() {
        super("AVN_CRAFT_[ITEM]");
    }

    @EventHandler
    public void onCraftItem(PlayerFinishCraftEvent e) {
        Player player = e.getPlayer();

        AvalonItem aItem = e.getResult();

        // Extra trigger name for checking the item's category.
        String type = "AVN_CRAFT_" + aItem.type.name();

        updateRep(player, aItem.namespace.toUpperCase(), new String[] {type});
    }
}
