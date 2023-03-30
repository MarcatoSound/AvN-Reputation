package net.playavalon.avnrep.data.shops;

import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnrep.Utils;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public class ShopItem {

    private String namespace;
    private ItemStack item;
    private int cost;
    private int minRepLevel;

    private AvalonItem aItem;
    private int quality;

    private ReputationShop shop;


    public ShopItem(ConfigurationSection data, ReputationShop shop) {
        this.namespace = data.getName();
        this.shop = shop;

        if (plugin.avni != null) {
            AvalonItem aItem = plugin.avni.itemManager.getItem(data.getString("AvalonItem"));
            if (aItem != null) {
                this.aItem = aItem;
                quality = data.getInt("Quality", 0);
                item = aItem.getQualityItem(quality);
            } else {
                item = new ItemStack(Material.valueOf(data.getString("Material", "STICK")));
            }
        }
        else item = new ItemStack(Material.valueOf(data.getString("Material", "STICK")));

        item.setAmount(data.getInt("Quantity"));

        cost = data.getInt("Cost", 5);

        minRepLevel = data.getInt("RepLevel", 0);
    }


    public ItemStack getItem() {
        return item;
    }

    public int getCost() {
        return cost;
    }

    public String getNamespace() {
        return namespace;
    }

    public int getQuality() {
        return quality;
    }

    public int getMinRepLevel() {
        return minRepLevel;
    }


    public void purchase(Player player) {

        if (canPlayerPurchase(player)) {
            Utils.giveOrDrop(player, item);
            ItemStack itemCost = shop.getCurrency().clone();
            itemCost.setAmount(cost);
            player.getInventory().removeItem(itemCost);
        }

    }

    public boolean canPlayerPurchase(Player player) {

        Faction faction = shop.getFaction();
        AvalonPlayer aPlayer = plugin.getAvalonPlayer(player);

        if (faction != null && aPlayer.getReputation(faction.getName()).getRepLevel() < minRepLevel) {
            player.sendMessage(Utils.colorize(debugPrefix + "&cYou must be at least reputation level &b" + minRepLevel + " &cto purchase this!"));
            return false;
        } else if (faction == null && aPlayer.getHighestRepLevel() < minRepLevel) {
            player.sendMessage(Utils.colorize(debugPrefix + "&cYou must have a reputation level of at least &b" + minRepLevel + " &cto purchase this!"));
            return false;
        }

        if (!player.getInventory().containsAtLeast(shop.getCurrency(), cost)) {
            player.sendMessage(Utils.colorize(debugPrefix + "&cYou do not have enough &b" + shop.getCurrencyName() + " &cto purchase this!"));
            return false;
        }

        return true;
    }
}
