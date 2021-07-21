package net.playavalon.avnrep.data.reputation;

import net.playavalon.avngui.GUI.Buttons.Button;
import net.playavalon.avngui.GUI.Window;
import net.playavalon.avngui.GUI.WindowGroup;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static net.playavalon.avnrep.AvNRep.plugin;

public class FactionManager {

    private HashMap<String, Faction> reputations;

    public FactionManager() {
        reputations = new HashMap<>();

        ConfigurationSection configSec = plugin.config.getConfigurationSection("ReputationFactions");
        if (configSec == null) return;
        Set<String> reps = configSec.getKeys(false);

        for (String rep : reps) {
            Faction repObj = new Faction(configSec.getConfigurationSection(rep));
            reputations.put(repObj.getName(), repObj);
        }

        initGui();
    }

    private void initGui() {
        Window gui = new Window("factionlist", 27, "&1Reputation Factions");
        Button button;

        int i = 0;
        for (Faction faction : reputations.values()) {

            button = new Button("factionlist_" + faction.getName(), faction.getDisplayIcon());
            button.setDisplayName(faction.getDisplayName());

            button.addAction("click", event -> {
                Player player = (Player)event.getWhoClicked();
                plugin.avnAPI.openGUI(player, "factioninfo_" + faction.getName());
            });

            gui.addButton(i, button);

            i++;

        }
    }

    public void put(Faction rep) {
        reputations.putIfAbsent(rep.getName(), rep);
    }
    public void remove(String name) {
        reputations.remove(name);
    }
    public Faction get(String name) {
        return reputations.get(name);
    }
    public Collection<Faction> getValues() {
        return reputations.values();
    }

}
