package net.playavalon.avnrep;

import net.playavalon.avnrep.api.ReputationAPI;
import net.playavalon.avnrep.triggers.*;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.AvalonPlayerManager;
import net.playavalon.avnrep.data.OnlineSQLDatabase;
import net.playavalon.avnrep.data.player.Reputation;
import net.playavalon.avnrep.data.reputation.Faction;
import net.playavalon.avnrep.data.reputation.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AvNRep extends JavaPlugin {

    public static AvNRep plugin;
    public static String debugPrefix;
    public FileConfiguration config;
    public OnlineSQLDatabase database;

    // Config constants
    public boolean dbEnabled = false;

    // Player data constants
    public FactionManager repManager;
    public AvalonPlayerManager playerManager;

    // File and folder locations
    public File playerData;

    @Override
    public void onEnable() {

        // Initial plugin data setup
        plugin = this;
        debugPrefix = Utils.fullColor("{#f5476d}[AvN Rep] ");

        saveDefaultConfig();
        config = getConfig();

        if (config.getBoolean("Database.Enabled", false)) {
            database = new OnlineSQLDatabase();
        }

        repManager = new FactionManager();
        playerManager = new AvalonPlayerManager();
        playerData = new File(getDataFolder(), "playerdata");
        if (!playerData.exists()) {
            playerData.mkdir();
        }

        Bukkit.getPluginManager().registerEvents(new AvalonListener(), this);

        // Register reputation triggers
        ReputationAPI api = new ReputationAPI();
        api.registerTrigger(new TriggerKillMob());
        api.registerTrigger(new TriggerBreakBlock());
        api.registerTrigger(new TriggerPlaceBlock());
        api.registerTrigger(new TriggerCraftItem());
        api.registerTrigger(new TriggerPlayerDeath());
        api.registerTrigger(new TriggerPlayerLevelUp());
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            api.registerTrigger(new TriggerKillMythicMob());
        }
        if (Bukkit.getPluginManager().getPlugin("AvNCombat") != null) {
            api.registerTrigger(new TriggerClassLevelUp());
        }
        if (Bukkit.getPluginManager().getPlugin("AvNItems") != null) {
            api.registerTrigger(new TriggerCraftAvalonItem());
            api.registerTrigger(new TriggerCraftAvalonItemHQ());
            api.registerTrigger(new TriggerGatherAvalonItem());
            api.registerTrigger(new TriggerGatherAvalonItemHQ());
        }


        // Functionality for hot loading and reloading (via say PlugMan)
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                // Code to reestablish player objects for AvN Reputation
                playerManager.put(player);
            }
        }



    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player;
        Player target;
        AvalonPlayer ap;

        switch (command.getName().toLowerCase()) {
            case "avnr":
            case "reputation":
                if (args.length == 0) {
                    if (sender instanceof Player) {
                        ap = getAvalonPlayer((Player)sender);
                        sender.sendMessage(ap.printReputation());
                        return false;
                    } else {
                        sender.sendMessage(Utils.fullColor("{#f5476d}Avalon Reputation - Version 1.0 Beta"));
                        return false;
                    }
                }

                Reputation playerRep;
                int level;
                double value;

                switch (args[0].toLowerCase()) {
                    case "version":
                        sender.sendMessage(Utils.fullColor("{#f5476d}Avalon Reputation - Version 1.0 Beta"));
                        return false;
                    case "checklevel":
                        if (args.length != 3) return false;
                        Faction rep = repManager.get(args[1]);
                        if (rep == null) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cCould not find reputation by the name " + args[0]));
                            return false;
                        }
                        try {
                            level = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cPlease enter a valid level number."));
                            return false;
                        }
                        sender.sendMessage(debugPrefix + Utils.colorize("&eRep level " + level + " cost: " + Utils.calcLevelCost(rep, level)));
                        break;
                    case "setrep":
                        if (args.length != 4) return false;
                        target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cCould not find player by the name " + args[0]));
                            return false;
                        }

                        ap = getAvalonPlayer(target);
                        playerRep = ap.getReputation(args[2].toLowerCase());
                        try {
                            value = Double.parseDouble(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cPlease enter a valid number. Valid numbers can have decimals."));
                            return false;
                        }
                        playerRep.setRepValue(value);

                        break;
                    case "addrep":
                        if (args.length != 4) return false;
                        target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cCould not find player by the name " + args[0]));
                            return false;
                        }

                        ap = getAvalonPlayer(target);
                        playerRep = ap.getReputation(args[2].toLowerCase());
                        try {
                            value = Double.parseDouble(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cPlease enter a valid number. Valid numbers can have decimals."));
                            return false;
                        }
                        playerRep.addRepValue(value);

                        break;
                    case "setlevel":
                        if (args.length < 4) return false;
                        target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cCould not find player by the name " + args[0]));
                            return false;
                        }

                        ap = getAvalonPlayer(target);
                        playerRep = ap.getReputation(args[2].toLowerCase());
                        try {
                            level = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cPlease enter a valid level number."));
                            return false;
                        }
                        playerRep.setRepLevel(level);

                    default:
                        target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(debugPrefix + Utils.colorize("&cCould not find player by the name " + args[0]));
                            return false;
                        }
                        ap = getAvalonPlayer(target);
                        sender.sendMessage(ap.printReputation());
                        break;
                }

                break;

        }

        return false;

    }


    public AvalonPlayer getAvalonPlayer(Player player) {
        return playerManager.getByPlayer(player);
    }

}
