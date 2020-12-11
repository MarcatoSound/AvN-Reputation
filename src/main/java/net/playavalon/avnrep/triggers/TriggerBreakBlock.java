package net.playavalon.avnrep.triggers;

import net.playavalon.avnrep.api.ReputationTrigger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class TriggerBreakBlock extends ReputationTrigger {

    public TriggerBreakBlock() {
        super("BREAK_[BLOCK]");
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Player player = e.getPlayer();

        Material mat = e.getBlock().getType();

        updateRep(player, mat.name());

    }
}
