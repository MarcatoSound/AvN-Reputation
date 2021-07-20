package net.playavalon.avnrep.data.reputation;

import net.playavalon.avnrep.Utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class RepSource {

    // Critical details
    private final String trigger;
    private Double value;

    // Display details
    private String displayName;
    private List<String> description;

    public RepSource(ConfigurationSection data) {
        trigger = data.getName();
        value = data.getDouble("Reputation", 1);

        displayName = Utils.colorize(data.getString("DisplayName", trigger));

        description = new ArrayList<>();
        List<String> uncolouredDesc = data.getStringList("DescriptionLore");
        for (String line : uncolouredDesc) {
            description.add(Utils.colorize(line));
        }
    }

    public String getTrigger() {
        return trigger;
    }

    public double getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getDescription() {
        return description;
    }

}
