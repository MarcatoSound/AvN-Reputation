package net.playavalon.avnrep;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.playavalon.avncombatspigot.api.PlayerChangeLevelEvent;
import net.playavalon.avnitems.crafting.events.PlayerFinishCraftEvent;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.gathering.events.PlayerGatherItemEvent;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.PlayerReputation;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.HashMap;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationListener implements Listener {

    // Primary class for tracking reputation triggers and their events.
    // TODO Implement registration of additional/new triggers via API

    // !! WORKING !!
    // KILL_[MOB]
    // KILL_MONSTER
    // KILL_ANIMAL
    @EventHandler
    public void onKill(EntityDeathEvent e) {
        // TODO Temporarily disabled while testing trigger registration.
        /*
        LivingEntity ent = e.getEntity();
        if (ent.getKiller() == null) return;

        Player player = ent.getKiller();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasKillTrigger()) continue;
            sources = rep.getRepSources();

            EntityType type = ent.getType();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("KILL_" + type.name().toUpperCase())) continue;
                // The player killed a specific mob, and this reputation includes the trigger for said mob.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (Utils.getEntityGroup(ent).equals("monster") && sources.containsKey("KILL_MONSTER")) {
                // The player killed a hostile mob of some kind, and this reputation includes the KILL_MONSTER trigger.
                rep.addRepValue(sources.get("KILL_MONSTER"));
                continue;
            }

            if (Utils.getEntityGroup(ent).equals("animal") && sources.containsKey("KILL_ANIMAL")) {
                // The player killed an animal of some kind, and this reputation includes the KILL_ANIMAL trigger.
                rep.addRepValue(sources.get("KILL_ANIMAL"));
            }

        }*/
    }

    // !! WORKING !!
    // BREAK_[BLOCK]
    // BREAK_BLOCK
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasBreakTrigger()) continue;
            sources = rep.getRepSources();

            Material mat = e.getBlock().getType();

            // Check if this reputation's sources have a trigger relating to
            //   this event.
            for (String key : sources.keySet()) {
                if (!key.equals("BREAK_" + mat.name().toUpperCase())) continue;
                // The player broke a specific block, and this reputation
                //   includes the trigger for said block.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (sources.containsKey("BREAK_BLOCK")) {
                // The player broke a block, and this reputation includes the
                //   trigger BREAK_BLOCK.
                rep.addRepValue(sources.get("BREAK_BLOCK"));
            }
        }*/
    }

    // !! WORKING !!
    // PLACE_[BLOCK]
    // PLACE_BLOCK
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasPlaceTrigger()) continue;
            sources = rep.getRepSources();

            Material mat = e.getBlock().getType();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("PLACE_" + mat.name().toUpperCase())) continue;
                // The player placed a specific block, and this reputation includes the trigger for said block.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (sources.containsKey("PLACE_BLOCK")) {
                // The player placed a block, and this reputation includes the trigger PLACE_BLOCK.
                rep.addRepValue(sources.get("PLACE_BLOCK"));
            }

        }*/

    }

    // !! WORKING !!
    // CRAFT_[ITEM]
    // CRAFT_ITEM
    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        /*// TODO For some reason this trigger produces an empty println() in console...
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player)e.getWhoClicked();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasCraftTrigger()) continue;
            sources = rep.getRepSources();

            Material mat = e.getRecipe().getResult().getType();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("CRAFT_" + mat.name().toUpperCase())) continue;
                // The player crafted a specific item, and this reputation includes the trigger for said item.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (sources.containsKey("CRAFT_ITEM")) {
                // The player crafted an item, and this reputation includes the trigger for CRAFT_ITEM.
                rep.addRepValue(sources.get("CRAFT_ITEM"));
            }

        }*/
    }

    // !! WORKING !!
    // DEATH
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        /*Player player = e.getEntity();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasDeathTrigger()) continue;
            sources = rep.getRepSources();

            if (!sources.containsKey("DEATH")) continue;
            // The player ****ing DIED.

            rep.addRepValue(sources.get("DEATH"));

        }*/
    }

    // !! WORKING !!
    // LEVELUP
    @EventHandler
    public void onPlayerLevel(PlayerLevelChangeEvent e) {
        /*if (e.getNewLevel() <= e.getOldLevel()) return;
        Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasDeathTrigger()) continue;
            sources = rep.getRepSources();

            if (!sources.containsKey("LEVELUP")) continue;
            // The player has leveled up, and this reputation includes the trigger for LEVELUP.

            rep.addRepValue(sources.get("LEVELUP"));

        }*/
    }


    // TODO Implement catching to not bother loading this event handler if MythicMobs isn't present; probably a sub-class initialized during parent initialization?
    // !! WORKING !!
    // MYTHIC_[MOB]
    // MYTHIC_MOB
    @EventHandler
    public void onMythicKill(MythicMobDeathEvent e) {
        /*if (!(e.getKiller() instanceof Player)) return;
        MythicMob mob = e.getMobType();

        Player player = (Player)e.getKiller();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasMythicTrigger()) continue;
            sources = rep.getRepSources();

            String mobName = mob.getInternalName();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("MYTHIC_" + mobName.toUpperCase())) continue;
                // The player killed a specific Mythic Mob, and this reputation includes the trigger for said Mythic Mob.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (sources.containsKey("MYTHIC_MOB")) {
                // The player killed a Mythic Mob, and this reputation includes the trigger for MYTHIC_MOB.
                rep.addRepValue(sources.get("MYTHIC_MOB"));
            }

        }*/
    }



    // TODO Implement catching to not bother loading this event handler if AvalonCombat isn't present; probably a sub-class initialized during parent initialization?
    // !! WORKING !!
    // CLASS_LEVELUP_[LEVEL]
    // CLASS_LEVELUP
    @EventHandler
    public void onClassLevel(PlayerChangeLevelEvent e) {
        /*if (e.getNewLevel() <= e.getOldLevel()) return;
        Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasAvnLevelTrigger()) continue;
            sources = rep.getRepSources();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("CLASS_LEVELUP_" + e.getNewLevel())) continue;
                // The player's class leveled up to a specific level, and this reputation includes the trigger for said level.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (sources.containsKey("CLASS_LEVELUP")) {
                // The player's class leveled up, and this reputation includes the trigger for CLASS_LEVELUP.
                rep.addRepValue(sources.get("CLASS_LEVELUP"));
            }

        }*/
    }

    // TODO Implement catching to not bother loading this event handler if AvalonItems isn't present; probably a sub-class initialized during parent initialization?
    // AVN_CRAFT_[ITEM]
    // AVN_CRAFT
    @EventHandler
    public void onAvnCraft(PlayerFinishCraftEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasAvnCraftTrigger()) continue;
            sources = rep.getRepSources();

            AvalonItem aItem = e.getResult();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("AVN_CRAFT_" + aItem.namespace.toUpperCase())) continue;
                // The player crafted a specific item with AvNi, and this reputation
                //   includes the trigger for said item.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (!sources.containsKey("AVN_CRAFT")) {
                // The player crafted an item with AvNi, and this reputation includes the
                //   trigger for AVN_CRAFT.
                rep.addRepValue(sources.get("AVN_CRAFT"));
            }
        }*/
    }
    // AVN_CRAFT_HQ_[ITEM]
    // AVN_CRAFT_HQ
    @EventHandler
    public void onAvnCraftHQ(PlayerFinishCraftEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        if (e.getQuality() < 1) return;

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasAvnCraftTrigger()) continue;
            sources = rep.getRepSources();

            AvalonItem aItem = e.getResult();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("AVN_CRAFT_HQ_" + aItem.namespace.toUpperCase())) continue;
                // The player crafted a specific item with AvNi in high-quality, and this reputation includes the trigger for said item.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (!sources.containsKey("AVN_CRAFT_HQ")) {
                // The player crafted an item with AvNi in high-quality, and this reputation includes the trigger for AVN_CRAFT_HQ.
                rep.addRepValue(sources.get("AVN_CRAFT_HQ"));
            }

        }*/
    }

    // GATHER_[ITEM]
    // GATHER
    @EventHandler
    public void onAvnGather(PlayerGatherItemEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasAvnGatherTrigger()) continue;
            sources = rep.getRepSources();

            AvalonItem aItem = e.getResult();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("GATHER_" + aItem.namespace.toUpperCase())) continue;
                // The player gathered a specific item with AvNi, and this reputation
                //   includes the trigger for said item.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (!sources.containsKey("GATHER")) {
                // The player gathered an item with AvNi, and this reputation includes
                //   the trigger for GATHER.
                rep.addRepValue(sources.get("GATHER"));
            }

        }*/
    }
    // GATHER_HQ_[ITEM]
    // GATHER_HQ
    @EventHandler
    public void onAvnGatherHQ(PlayerGatherItemEvent e) {
        /*Player player = e.getPlayer();
        AvalonPlayer ap = plugin.getAvalonPlayer(player);

        if (!e.isHq()) return;

        HashMap<String, Double> sources;
        ROOT_LOOP: for (PlayerReputation rep : ap.getAllReputations()) {

            if (!rep.getRep().hasAvnGatherTrigger()) continue;
            sources = rep.getRepSources();

            AvalonItem aItem = e.getResult();

            // Check if this reputation's sources have a trigger relating to this event.
            for (String key : sources.keySet()) {
                if (!key.equals("GATHER_HQ_" + aItem.namespace.toUpperCase())) continue;
                // The player gathered a specific item with AvNi in high-quality, and this reputation includes the trigger for said item.

                rep.addRepValue(sources.get(key));
                continue ROOT_LOOP;
            }

            if (!sources.containsKey("GATHER_HQ")) {
                // The player gathered an item with AvNi in high-quality, and this reputation includes the trigger for GATHER_HQ.
                rep.addRepValue(sources.get("GATHER_HQ"));
            }

        }*/
    }

}
