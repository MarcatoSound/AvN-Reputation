package net.playavalon.avnrep;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.playavalon.avnrep.AvNRep.plugin;

public class AvalonListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.playerManager.put(event.getPlayer());
    }

}
