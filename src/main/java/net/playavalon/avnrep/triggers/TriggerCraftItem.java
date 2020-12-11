package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class TriggerCraftItem extends ReputationTrigger {

    public TriggerCraftItem() {
        super("CRAFT_[ITEM]");
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player)e.getWhoClicked();

        Material mat = e.getRecipe().getResult().getType();

        updateRep(player, mat.name());

    }
}
