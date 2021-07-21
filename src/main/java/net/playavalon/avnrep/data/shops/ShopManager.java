package net.playavalon.avnrep.data.shops;

import java.util.HashMap;

public class ShopManager {

    private final HashMap<String, ReputationShop> shops;

    public ShopManager() {
        shops = new HashMap<>();
    }

    public void put(ReputationShop shop) {
        shops.put(shop.getNamespace(), shop);
    }

    public ReputationShop get(String namespace) {
        return shops.get(namespace);
    }

    public boolean contains(String namespace) { return shops.containsKey(namespace); }

}
