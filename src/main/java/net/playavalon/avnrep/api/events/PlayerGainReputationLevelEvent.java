package net.playavalon.avnrep.api.events;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.PlayerReputation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGainReputationLevelEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;
    private Player player;
    private AvalonPlayer ap;
    private PlayerReputation playerReputation;
    private double oldRep;
    private double newRep;
    private int oldLevel;
    private int newLevel;
    private PlayerGainReputationEvent gainEvent;

    public PlayerGainReputationLevelEvent(AvalonPlayer ap, PlayerReputation pRep, double oldRep, double newRep, int oldLevel, int newLevel) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.playerReputation = pRep;
        this.oldRep = oldRep;
        this.newRep = newRep;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
        this.gainEvent = null;
    }
    public PlayerGainReputationLevelEvent(AvalonPlayer ap, PlayerReputation pRep, double oldRep, double newRep, int oldLevel, int newLevel, PlayerGainReputationEvent gainEvent) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.playerReputation = pRep;
        this.oldRep = oldRep;
        this.newRep = newRep;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
        this.gainEvent = gainEvent;
    }

    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /**
     *
     * @return The player associated with this event.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @see AvalonPlayer
     * @return The AvalonPlayer associated with this event.
     */
    public AvalonPlayer getAvalonPlayer() {
        return ap;
    }

    /**
     * @see PlayerReputation
     * @return The PlayerReputation object the triggered this event.
     */
    public PlayerReputation getPlayerReputation() {
        return playerReputation;
    }


    /**
     *
     * @return The level this player was before this event occurred.
     */
    public int getOldLevel() {
        return oldLevel;
    }


    /**
     *
     * @return The level this player will become after this event.
     */
    public int getNewLevel() {
        return newLevel;
    }
    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }

    /**
     *
     * @return The reputation this player had before this event occurred.
     */
    public double getOldRep() {
        return oldRep;
    }

    /**
     *
     * @return The reputation this player will have after this event.
     */
    public double getNewRep() {
        return newRep;
    }
    public void setNewRep(double newRep) {
        this.newRep = newRep;
    }
}
