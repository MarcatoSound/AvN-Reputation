package net.playavalon.avnrep.data.player;

import net.playavalon.avncombatspigot.utility.Util;
import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.api.events.PlayerGainReputationEvent;
import net.playavalon.avnrep.api.events.PlayerGainReputationLevelEvent;
import net.playavalon.avnrep.api.events.PlayerLoseReputationEvent;
import net.playavalon.avnrep.data.reputation.Reputation;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.List;

import static net.playavalon.avnrep.AvNRep.debugPrefix;

public class PlayerReputation {

    private Reputation rep;
    private AvalonPlayer ap;
    private int repLevel;
    private double repValue;

    public PlayerReputation(Reputation rep, AvalonPlayer ap) {
        this.rep = rep;
        this.ap = ap;

        // Default rep values
        this.repLevel = 0;
        this.repValue = 0;
    }

    public Reputation getRep() {
        return rep;
    }
    public HashMap<String, Double> getRepSources() {
        return rep.getSources();
    }

    // Reputation value manipulation methods
    public double getRepValue() {
        return repValue;
    }

    public void setRepValue(double val) {
        Event event;
        if (val > 0) {
            event = new PlayerGainReputationEvent(ap, this, val);
            PlayerGainReputationEvent gainEvent = (PlayerGainReputationEvent)event;
            Bukkit.getPluginManager().callEvent(gainEvent);
            if (gainEvent.isCancelled()) return;

            repValue = Math.max(Util.round(((PlayerGainReputationEvent)event).getAmount(), 2), 0);
            tryLevelUp(gainEvent);

        } else if (val < 0) {
            event = new PlayerLoseReputationEvent(ap, this, Math.abs(val));
            PlayerLoseReputationEvent lossEvent = (PlayerLoseReputationEvent)event;
            Bukkit.getPluginManager().callEvent(lossEvent);
            if (lossEvent.isCancelled()) return;

            repValue = Math.max(Util.round(-((PlayerLoseReputationEvent)event).getAmount(), 2), 0);

        }
        ap.savePlayerData();
    }
    public void addRepValue(double val) {
        setRepValue(repValue + val);
    }
    public void removeRepValue(double val) {
        setRepValue(repValue - val);
    }

    public void setRepValue(double val, String trigger) {
        Event event;
        if (val > 0) {

            event = new PlayerGainReputationEvent(ap, this, val, trigger);
            PlayerGainReputationEvent gainEvent = (PlayerGainReputationEvent)event;
            Bukkit.getPluginManager().callEvent(gainEvent);
            if (gainEvent.isCancelled()) return;

            repValue = Math.max(Util.round(((PlayerGainReputationEvent)event).getAmount(), 2), 0);
            tryLevelUp(gainEvent);

        } else if (val < 0) {

            event = new PlayerLoseReputationEvent(ap, this, Math.abs(val), trigger);
            PlayerLoseReputationEvent lossEvent = (PlayerLoseReputationEvent)event;
            Bukkit.getPluginManager().callEvent(lossEvent);
            if (lossEvent.isCancelled()) return;

            repValue = Math.max(Util.round(-((PlayerLoseReputationEvent)event).getAmount(), 2), 0);

        }
        ap.savePlayerData();
    }
    public void addRepValue(double val, String trigger) {
        setRepValue(repValue + val, trigger);
    }
    public void removeRepValue(double val, String trigger) {
        setRepValue(repValue - val, trigger);
    }

    // Reputation level manipulation methods
    public int getRepLevel() {
        return repLevel;
    }

    public void setRepLevel(int repLevel) {
        if (repLevel > rep.getMaxLevel()) {
            this.repLevel = rep.getMaxLevel();
            return;
        }
        this.repLevel = repLevel;
    }
    public void addRepLevel(int level) {
        setRepLevel(repLevel + level);
    }
    public void removeRepLevel(int level) {
        setRepLevel(repLevel - level);
    }

    // Reputation utility methods
    private void tryLevelUp(PlayerGainReputationEvent gainEvent) {
        // Level up processing
        double oldRep = repValue;
        double newRep = repValue;
        double nextLevelCost = Utils.calcLevelCost(this.rep, repLevel+1);
        int oldLevel = repLevel;
        int newLevel = repLevel;
        if (repValue >= nextLevelCost) {

            while (newRep >= nextLevelCost) {
                newRep -= nextLevelCost;
                newLevel++;
                nextLevelCost = Utils.calcLevelCost(this.rep, repLevel+1);
            }

            PlayerGainReputationLevelEvent event = new PlayerGainReputationLevelEvent(ap, this, oldRep, newRep, oldLevel, newLevel, gainEvent);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) return;

            repValue = Util.round(event.getNewRep(), 2);
            repLevel = event.getNewLevel();
            if (repValue < 0) repValue = 0;

            Player player = ap.getPlayer();
            player.playSound(player.getLocation(), "entity.player.levelup", 1.0F, 1.0F);
            player.sendMessage(Utils.colorize(debugPrefix + "&aYour " + WordUtils.capitalize(rep.getName()) + " reputation level went up!"));

        }

    }

    public void runLevelCommands(int level) {
        HashMap<Integer, List<String>> levelCommands = rep.getLevelCommands();
        if (!levelCommands.containsKey(level)) return;
        List<String> commands = levelCommands.get(level);
        Player player = ap.getPlayer();
        for (String command : commands) {
            // Handling of placeholders
            if (command.contains("<player>")) command = command.replaceAll("<player>", player.getDisplayName());
            if (command.contains("<level>")) command = command.replaceAll("<level>", String.valueOf(level));
            if (command.contains("<faction>")) command = command.replaceAll("<faction>", rep.getName());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

}
