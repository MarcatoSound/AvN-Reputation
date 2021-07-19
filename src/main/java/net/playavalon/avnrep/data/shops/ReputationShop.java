package net.playavalon.avnrep.data.shops;

import com.google.common.collect.Lists;
import net.playavalon.avngui.GUI.Buttons.Button;
import net.playavalon.avngui.GUI.Window;
import net.playavalon.avngui.GUI.WindowGroup;
import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.Reputation;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationShop {

    private final String namespace;
    private final String displayName;
    private final ArrayList<ShopItem> items;

    private final Faction faction;

    public ReputationShop(ConfigurationSection data) {

        this.namespace = data.getName();
        this.displayName = data.getString("DisplayName");
        items = new ArrayList<>();

        faction = plugin.repManager.get(data.getString("Faction"));
        if (faction == null) System.out.println(debugPrefix + "WARNING :: Shop '" + namespace + "' does not have a faction!!");

        ConfigurationSection shopItems = data.getConfigurationSection("Items");
        if (shopItems == null) {
            System.out.println(debugPrefix + "ERROR :: Shop '" + namespace + "' contains no items! Skipping...");
            return;
        }

        for (String path : shopItems.getKeys(false)) {
            ConfigurationSection itemData = shopItems.getConfigurationSection(path);
            if (itemData == null) {
                System.out.println(debugPrefix + "ERROR :: Invalid item in shop '" + namespace + "': '" + path + "'");
                continue;
            }

            addItem(new ShopItem(itemData, this));

        }

        initGUI();

        plugin.shopManager.put(this);

    }

    public void initGUI() {
        WindowGroup group = new WindowGroup("shop_" + namespace);
        Window gui;
        Button button;

        List<List<ShopItem>> pages = Lists.partition(items, 27);
        int page = 0;
        for (List<ShopItem> items : pages) {

            gui = new Window("shop_" + namespace + "_" + page, 36, displayName + " " + page, group);

            int slot = 0;
            for (ShopItem item : items) {

                button = new Button("shop_" + namespace + "_" + item.getNamespace(), item.getItem());

                button.addLore(Utils.colorize("&8----------------------"));
                button.addLore(Utils.colorize("&7Reputation Level: " + item.getMinRepLevel()));
                button.addLore(Utils.colorize("&7Cost: " + item.getCost()));
                button.addLore(Utils.colorize(""));

                button.addAction("purchase", event -> {
                    Player player = (Player)event.getWhoClicked();
                    item.purchase(player);
                });

                gui.addButton(slot, button);

                slot++;

            }

            page++;

        }

    }


    public String getNamespace() {
        return namespace;
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }

    public ShopItem getItem(int index) {
        return items.get(index);
    }

    public Faction getFaction() {
        return faction;
    }
}
