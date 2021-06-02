package net.playavalon.avnrep.data;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class AvalonPlayerManager {
    
    private HashMap<UUID, AvalonPlayer> players;

    public AvalonPlayerManager() {
        players = new HashMap<>();
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    [ WRAPPERS ]
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void put(Player player) {
        AvalonPlayer ap = new AvalonPlayer(player);
        ap.loadPlayerData();
        players.putIfAbsent(player.getUniqueId(), ap);
    }
    public void remove(Player player) {
        players.remove(player.getUniqueId());
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    [ GETTERS ]
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public AvalonPlayer getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }
    public AvalonPlayer getByName(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) return null;
        return players.get(player.getUniqueId());
    }
    public AvalonPlayer getByUuid(UUID uuid) {
        return players.get(uuid);
    }
    
}
