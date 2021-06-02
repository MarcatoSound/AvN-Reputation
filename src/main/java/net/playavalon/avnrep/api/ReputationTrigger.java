package net.playavalon.avnrep.api;

import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.Reputation;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public abstract class ReputationTrigger implements Listener {

    private String triggerName;
    private String triggerNameClean;

    public ReputationTrigger(String name) {
        this.triggerName = name;
        this.triggerNameClean = this.triggerName.replaceAll("[\\[\\]]", "");
    }

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	[ CLASS METHODS ]
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public String getTriggerName() {
        return this.triggerName;
    }
    public String getTriggerNameClean() {
        return triggerNameClean;
    }
    public String getTriggerNameSpecific(String specific) {
        return this.triggerName.replaceAll("\\[.*]", specific);
    }

    // Methods to search for a particular source/trigger on the reputation object
    /**
     * @param rep The PlayerReputation we are checking.
     * @param query The source/trigger label in the reputation config. (I.e. KILL_MONSTER)
     * @return True if the query was found. False if not.
     */
    private boolean updateRep(@NotNull Reputation rep, @NotNull String query) {

        if (plugin.config.getBoolean("Debug", false)) System.out.println(debugPrefix + "Checking " + rep.getFaction().getName() + " for " + query + "...");
        HashMap<String, Double> sources = rep.getRepSources();
        if (sources.containsKey(query)) {
            if (plugin.config.getBoolean("Debug", false)) System.out.println(debugPrefix + "Found!");
            rep.addRepValue(sources.get(query), query);
            return true;
        }
        return false;
    }

    /**
     * @param player The player involved with this trigger.
     * @param queries A list of triggers to search for. (I.e. KILL_MONSTER, KILL_ANIMAL, etc.)
     */
    protected void updateRep(@NotNull Player player, @NotNull String... queries) {
        AvalonPlayer ap = plugin.getAvalonPlayer(player);
        if (ap == null) return;

        for (Reputation rep : ap.getAllReputations()) {
            for (String query : queries) {
                if (updateRep(rep, query)) break;
            }
        }
    }

    /**
     * @param player The player involved with this trigger.
     * @param specific A specific identifier to use at the end of the trigger name (I.e. KILL_CREEPER, where CREEPER is the specific.)
     * @param extra A list of triggers to search for. (I.e. KILL_MONSTER, KILL_ANIMAL, etc.)
     */
    protected void updateRep(@NotNull Player player, @NotNull String specific, @NotNull String[] extra) {
        // TODO Implement functionality for extras to contain the [] wildcard.
        AvalonPlayer ap = plugin.getAvalonPlayer(player);
        if (ap == null) return;

        ArrayList<String> queries = new ArrayList<>();

        queries.add(this.getTriggerNameSpecific(specific));
        queries.addAll(Arrays.asList(extra));
        queries.add(this.getTriggerNameClean());

        for (Reputation rep : ap.getAllReputations()) {
            for (String query : queries) {
                if (updateRep(rep, query)) break;
            }
        }
    }

    /**
     * @param player The player involved with this trigger.
     * @param specific A specific identifier to use at the end of the trigger name (I.e. KILL_CREEPER, where CREEPER is the specific.)
     * @param extra One extra trigger to search for. (I.e. KILL_MONSTER, KILL_ANIMAL, etc.)
     */
    protected void updateRep(@NotNull Player player, @NotNull String specific, @NotNull String extra) {
        // TODO Implement functionality for extras to contain the [] wildcard.
        AvalonPlayer ap = plugin.getAvalonPlayer(player);
        if (ap == null) return;

        ArrayList<String> queries = new ArrayList<>();

        queries.add(this.getTriggerNameSpecific(specific));
        queries.addAll(Collections.singletonList(extra));
        queries.add(this.getTriggerNameClean());

        for (Reputation rep : ap.getAllReputations()) {
            for (String query : queries) {
                if (updateRep(rep, query)) break;
            }
        }
    }
    /**
     * @param player The player involved with this trigger.
     * @param specific A specific identifier to use at the end of the trigger name (I.e. KILL_CREEPER, where CREEPER is the specific.)
     */
    protected void updateRep(@NotNull Player player, @NotNull String specific) {
        AvalonPlayer ap = plugin.getAvalonPlayer(player);
        if (ap == null) return;

        ArrayList<String> queries = new ArrayList<>();

        queries.add(this.getTriggerNameSpecific(specific));
        queries.add(this.getTriggerNameClean());

        for (Reputation rep : ap.getAllReputations()) {
            for (String query : queries) {
                if (updateRep(rep, query)) break;
            }
        }
    }

}
