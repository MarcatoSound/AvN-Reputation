package net.playavalon.avnrep.data.reputation;

import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnrep.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static net.playavalon.avnrep.AvNRep.plugin;

public class RepSource {

    // Critical details
    private final String trigger;
    private Double value;

    // Display details
    private String displayName;
    private List<String> description;
    private ItemStack displayIcon;

    public RepSource(ConfigurationSection data) {
        trigger = data.getName();
        value = data.getDouble("Reputation", 1);

        displayName = Utils.colorize(data.getString("DisplayName", trigger));

        description = new ArrayList<>();
        List<String> uncolouredDesc = data.getStringList("DescriptionLore");
        for (String line : uncolouredDesc) {
            description.add(Utils.colorize(line));
        }

        Material mat;
        if (plugin.avni != null) {

            AvalonItem aItem = plugin.avni.itemManager.getItem(data.getString("IconItem"));
            if (aItem == null) {
                mat = Material.matchMaterial(data.getString("IconItem", value >= 0 ? "LIME_STAINED_GLASS_PANE" : "RED_STAINED_GLASS_PANE"));
                if (mat == null) mat = Material.LIME_STAINED_GLASS_PANE;
                displayIcon = new ItemStack(mat);
            } else {
                displayIcon = new ItemStack(aItem.item.getType());
                ItemMeta meta = displayIcon.getItemMeta();
                meta.setCustomModelData(aItem.getCustomModelData());

                displayIcon.setItemMeta(meta);
            }

        } else {

            mat = Material.matchMaterial(data.getString("IconItem", value >= 0 ? "LIME_STAINED_GLASS_PANE" : "RED_STAINED_GLASS_PANE"));
            if (mat == null) mat = Material.LIME_STAINED_GLASS_PANE;
            displayIcon = new ItemStack(mat);

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

    public ItemStack getDisplayIcon() {
        return displayIcon;
    }
}
