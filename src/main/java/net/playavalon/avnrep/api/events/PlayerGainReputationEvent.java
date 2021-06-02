package net.playavalon.avnrep.api.events;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.Reputation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerGainReputationEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;
    private Player player;
    private AvalonPlayer ap;
    private Reputation reputation;
    private double amount;
    private String triggerName;

    public PlayerGainReputationEvent(AvalonPlayer ap, Reputation pRep, double amount) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.reputation = pRep;
        this.amount = amount;
        this.triggerName = "NONE";
    }
    public PlayerGainReputationEvent(AvalonPlayer ap, Reputation pRep, double amount, String triggerName) {
        this.player = ap.getPlayer();
        this.ap = ap;
        this.reputation = pRep;
        this.amount = amount;
        this.triggerName = triggerName;
    }

    @NotNull
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
     * @see Reputation
     * @return The PlayerReputation object the triggered this event.
     */
    public Reputation getReputation() {
        return reputation;
    }

    /**
     *
     * @return The amount of reputation being gained.
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
