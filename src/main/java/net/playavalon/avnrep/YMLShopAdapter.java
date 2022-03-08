package net.playavalon.avnrep;

import net.playavalon.avnrep.data.shops.ReputationShop;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static net.playavalon.avnrep.AvNRep.plugin;

public class YMLShopAdapter {

    public YMLShopAdapter() {
        if (!plugin.ymlShops.exists()) {
            plugin.saveResource("shops/example_shops.yml", false);
        }

        File[] files = plugin.ymlShops.listFiles();
        if (files == null) return;
        fileWalk(files);
    }

    public void fileWalk(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                fileWalk(file.listFiles());
                return;
            }

            if (!FilenameUtils.getExtension(file.getName()).equals("yml")) continue;
            FileConfiguration data = new YamlConfiguration();
            try {
                data.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
                continue;
            }
            Set<String> namespaces = data.getKeys(false);
            for (String namespace : namespaces) {
                adaptItem(data, namespace);
            }
        }
    }

    public void adaptItem(FileConfiguration data, String namespace) {
        ConfigurationSection shopData = data.getConfigurationSection(namespace);
        if (shopData == null) return;

        new ReputationShop(shopData);
    }
}
