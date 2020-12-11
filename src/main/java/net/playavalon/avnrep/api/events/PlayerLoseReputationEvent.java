package net.playavalon.avnrep.api.events;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.PlayerReputation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoseReputationEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;
    private Player player;
    private AvalonPlayer ap;
    private PlayerReputation playerReputation;
    private double amount;
    private String triggerName;

    public PlayerLoseReputationEvent(AvalonPlayer ap, PlayerReputation pRep, double amount) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.playerReputation = pRep;
        this.amount = amount;
        this.triggerName = "NONE";
    }
    public PlayerLoseReputationEvent(AvalonPlayer ap, PlayerReputation pRep, double amount, String triggerName) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.playerReputation = pRep;
        this.amount = amount;
        this.triggerName = triggerName;
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
     * @return The amount of reputation being lost.
     */
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return The name of the trigger that caused this change, if applicable.
     */
    public String getTriggerName() {
        return triggerName;
    }
}
