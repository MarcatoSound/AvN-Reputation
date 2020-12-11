package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class TriggerPlaceBlock extends ReputationTrigger {

    public TriggerPlaceBlock() {
        super("PLACE_[BLOCK]");
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        Material mat = e.getBlock().getType();

        updateRep(player, mat.name());

    }
}
