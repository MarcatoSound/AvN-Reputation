package net.playavalon.avnrep;

import net.md_5.bungee.api.ChatColor;
import net.playavalon.avncombatspigot.AvalonCombat;
import net.playavalon.avnitems.AvalonItems;
import net.playavalon.avnitems.database.AvalonItem;
import net.playavalon.avnitems.utility.Util;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.playavalon.avnrep.AvNRep.debugPrefix;
import static net.playavalon.avnrep.AvNRep.plugin;

public final class Utils {

    public static String colorize(String s) {

        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String fullColor(String s) {

        StringBuilder sb = new StringBuilder();

        String[] strs = s.split("(?=(\\{#[a-fA-F0-9]*}))");

        Pattern pat = Pattern.compile("(\\{(#[a-fA-F0-9]*)})(.*)");
        Matcher matcher;

        for (String str : strs) {
            matcher = pat.matcher(str);
            if (matcher.find()) {
                sb.append(ChatColor.of(matcher.group(2)));
                sb.append(matcher.group(3));
            } else {
                sb.append(str);
            }
        }

        return colorize(sb.toString());
    }

    public static boolean hasPermission(CommandSender sender, String node) {

        if (sender.hasPermission("*") || sender.hasPermission(node))
            return true;
        sender.sendMessage(ChatColor.RED + "You do not have permission to do that.");
        return false;
    }

    public static boolean hasPermissionSilent(CommandSender sender, String node) {

        return sender.hasPermission("*") || sender.hasPermission(node);
    }

    public static double round (double value, int places) {

        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;

    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;

    }

    public static boolean getRandomBoolean(double chance) {
        return Math.random() < chance;
    }

    public static Timestamp getFutureTime(int minutes) {
        return new Timestamp(System.currentTimeMillis()+(minutes*60*1000));
    }

    public static void giveOrDrop(Player player, ItemStack item) {

        if (item == null || item.getType() == Material.AIR) return;
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Util.colorize(debugPrefix + "&cYour inventory is full! Item dropped to the ground..."));
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }

    }

    public static double calcLevelCost(Faction rep, double level) {
        double minRep = rep.getMinRep(); //plugin.config.getDouble("");
        double maxRep = rep.getMaxRep();
        double diff = maxRep-minRep;

        double maxLevel = rep.getMaxLevel();
        double perLevel = diff/maxLevel;

        double factor1 = 0.35;
        double curve = rep.getCurve();

        return minRep+((level*perLevel) * (factor1 - (level/maxLevel) * curve));
    }

    public static String getEntityGroup(LivingEntity ent) {
        switch (ent.getType()) {
            case WITHER:
            case ENDER_DRAGON:
            case VEX:
            case HUSK:
            case BLAZE:
            case GHAST:
            case GIANT:
            case GUARDIAN:
            case ELDER_GUARDIAN:
            case SLIME:
            case STRAY:
            case WITCH:
            case EVOKER:
            case SPIDER:
            case ZOGLIN:
            case ZOMBIE:
            case ENDERMAN:
            case SHULKER:
            case CREEPER:
            case DROWNED:
            case PHANTOM:
            case ENDERMITE:
            case ILLUSIONER:
            case CAVE_SPIDER:
            case SKELETON:
            case MAGMA_CUBE:
            case SILVERFISH:
            case VINDICATOR:
            case ZOMBIE_VILLAGER:
            case PIGLIN:
            case PIGLIN_BRUTE:
            case ZOMBIFIED_PIGLIN:
            case PILLAGER:
            case WITHER_SKELETON:
            case RAVAGER:
                return "monster";
            case BAT:
            case BEE:
            case CAT:
            case COD:
            case COW:
            case FOX:
            case PIG:
            case MULE:
            case WOLF:
            case HORSE:
            case LLAMA:
            case PANDA:
            case SHEEP:
            case SQUID:
            case DONKEY:
            case HOGLIN:
            case OCELOT:
            case PARROT:
            case RABBIT:
            case SALMON:
            case TURTLE:
            case CHICKEN:
            case DOLPHIN:
            case POLAR_BEAR:
            case PUFFERFISH:
            case MUSHROOM_COW:
            case TRADER_LLAMA:
                return "animal";
            default:
                return "unknown";
        }
    }

    public static String getNamespace(ItemStack item) {
        if (plugin.avni == null) return "";

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return "";
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key;
        key = new NamespacedKey(plugin.avni, "namespace");


        if (!data.has(key, PersistentDataType.STRING)) return "";
        String namespace = data.get(key, PersistentDataType.STRING);

        return namespace;

    }

    public static int getQuality(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return -1;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(AvalonItems.plugin, "quality");

        if (!data.has(key, PersistentDataType.INTEGER)) return 0;

        return data.get(key, PersistentDataType.INTEGER);

    }

    public static int getMobLevel(Entity ent) {
        PersistentDataContainer data = ent.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(AvalonCombat.plugin, "AvnLevel");

        if (!data.has(key, PersistentDataType.INTEGER)) return 0;

        return data.get(key, PersistentDataType.INTEGER);
    }

    public static ItemStack getItemFromNamespace(String itemName) {
        ItemStack item;
        Material currencyMat = Material.matchMaterial(itemName);

        if (currencyMat != null) {

            item = new ItemStack(currencyMat);

        } else {

            // If AvNi is present...
            if (plugin.avni != null) {

                AvalonItem aItem = plugin.avni.itemManager.getItem(itemName);
                if (aItem != null) {
                    item = aItem.getQualityItem(0);
                }
                else {
                    item = new ItemStack(Material.STICK);
                }

            } else {
                item = new ItemStack(Material.STICK);
            }

        }

        return item;
    }

    @Nullable
    public static String getItemDisplayName(String itemName) {
        String name;
        Material currencyMat = Material.matchMaterial(itemName);

        if (currencyMat != null) {

            name = currencyMat.toString();

        } else {

            // If AvNi is present...
            if (plugin.avni != null) {

                AvalonItem aItem = plugin.avni.itemManager.getItem(itemName);
                if (aItem != null) {
                    name = aItem.fullName;
                }
                else {
                    name = null;
                }

            } else {
                name = Material.STICK.toString();
            }

        }

        return name;
    }


}
