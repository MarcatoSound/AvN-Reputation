package net.playavalon.avnrep.data.player;

import net.playavalon.avncombatspigot.utility.Util;
import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.data.reputation.Reputation;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;

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
        repValue = Math.max(Util.round(val, 2), 0);
        tryLevelUp();
        ap.savePlayerData();
    }

    public void addRepValue(double val) {
        setRepValue(repValue + val);
    }

    public void removeRepValue(double val) {
        setRepValue(repValue - val);
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
    private void tryLevelUp() {
        // Level up processing
        double nextLevelCost = Utils.calcLevelCost(this.rep, repLevel+1);
        if (repValue >= nextLevelCost) {

            while (repValue >= nextLevelCost) {
                repValue -= nextLevelCost;
                repLevel++;
                nextLevelCost = Utils.calcLevelCost(this.rep, repLevel+1);
            }
            if (repValue < 0) repValue = 0;

            Player player = ap.getPlayer();
            player.playSound(player.getLocation(), "entity.player.levelup", 1.0F, 1.0F);
            player.sendMessage(Utils.colorize(debugPrefix + "&aYour " + WordUtils.capitalize(rep.getName()) + " reputation level went up!"));
        }
    }

}
