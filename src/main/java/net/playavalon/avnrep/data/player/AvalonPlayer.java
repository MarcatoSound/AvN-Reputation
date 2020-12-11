package net.playavalon.avnrep.data.player;

import net.playavalon.avnrep.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public class AvalonPlayer {

    private Player player;
    private PlayerReputationManager playerReputationManager;

    public AvalonPlayer(Player player) {
        this.player = player;
        this.playerReputationManager = new PlayerReputationManager(this);

        if (!plugin.dbEnabled) {
            // Since there is no database, create a blank player data file if it doesn't exist
            String file = player.getUniqueId()+".yml";
            File playerFile = new File(plugin.playerData, file);
            try {
                if (!playerFile.exists()) playerFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public PlayerReputationManager getPlayerReputationManager() {
        return this.playerReputationManager;
    }

    public PlayerReputation getReputation(String namespace) {
        return playerReputationManager.getReputation(namespace);
    }
    public Collection<PlayerReputation> getAllReputations() {
        return playerReputationManager.getReputations();
    }

    public Player getPlayer() {
        return player;
    }

    // Used to get the colour formatted reputation information for MC Chat
    public String printReputation() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.fullColor("{#f5476d}==== " + player.getName() + "'s Reputation ===="));
        for (PlayerReputation rep : playerReputationManager.getReputations()) {
            sb.append("\n&b" + WordUtils.capitalize(rep.getRep().getName()) + ":");
            sb.append("\n&a- Level: " + rep.getRepLevel());
            sb.append("\n&a- Reputation: " + rep.getRepValue());
        }

        return Utils.colorize(sb.toString());
    }

    //////
    // PLAYER DATA HANDLERS
    //////

    public void loadPlayerData() {
        if (plugin.dbEnabled) {
            // Database support is enabled; use SQL to save player data.
            try {
                plugin.database.loadPlayerData(this);
            } catch (SQLException e) {
                System.out.println(debugPrefix + "Could not load player data!");
                e.printStackTrace();
            }

        } else {
            // No database; use YML to load player data.
            String file = player.getUniqueId()+".yml";
            File playerFile = new File(plugin.playerData, file);

            YamlConfiguration playerData = new YamlConfiguration();
            try {
                playerData.load(playerFile);

                ConfigurationSection reputations = playerData.getConfigurationSection("reputations");
                if (reputations == null) reputations = playerData.createSection("reputations");
                // Loop through reputations in the config and load them into memory.
                for (String name : reputations.getKeys(false)) {
                    name = name.toLowerCase();
                    PlayerReputation rep = playerReputationManager.getReputation(name);
                    if (rep == null) continue;
                    rep.setRepLevel(reputations.getInt(name + ".level"));
                    rep.setRepValue(reputations.getDouble(name + ".exp"));
                }

            } catch (InvalidConfigurationException | IOException ex) {
                ex.printStackTrace();
            }

        }
    }
    public void savePlayerData() {
        if (plugin.dbEnabled) {
            // Database support is enabled; use SQL to save player data.
            try {
                plugin.database.savePlayerData(this);
            } catch (SQLException e) {
                System.out.println(debugPrefix + "Could not save player data!");
                e.printStackTrace();
            }

        } else {
            // No database; use YML to save player data.
            YamlConfiguration playerData = new YamlConfiguration();

            try {
                File playerFile = new File(plugin.playerData, player.getUniqueId()+".yml");
                playerData.load(playerFile);

                // Loop through reputations on the player and save them to YML.
                for (PlayerReputation rep : playerReputationManager.getReputations()) {
                    playerData.set("reputations."+rep.getRep().getName()+".level", rep.getRepLevel());
                    playerData.set("reputations."+rep.getRep().getName()+".exp", rep.getRepValue());
                }

                playerData.save(playerFile);
            } catch (InvalidConfigurationException | IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
