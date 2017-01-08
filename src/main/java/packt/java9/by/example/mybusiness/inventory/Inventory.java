package packt.java9.by.example.mybusiness.inventory;

import org.springframework.stereotype.Component;
import packt.java9.by.example.mybusiness.product.Product;
import packt.java9.by.example.mybusiness.product.ProductDoesNotExists;
import packt.java9.by.example.mybusiness.product.ProductIsOutOfStock;

import java.util.HashMap;
import java.util.Map;

@Component
public class Inventory {
    private final Map<Product, InventoryItem> inventory = new HashMap<>();

    public InventoryItem getItem(Product product) {
        return inventory.get(product);
    }

    public void store(Product product, long amount) {
        if (inventory.containsKey(product)) {
            inventory.get(product).store(amount);
        } else {
            inventory.put(product, new InventoryItem(amount));
        }
    }

    public void remove(Product product, long amount) throws ProductDoesNotExists, ProductIsOutOfStock {
        if (inventory.containsKey(product)) {
            if (inventory.get(product).amount() >= amount) {
                inventory.get(product).remove(amount);
            } else {
                throw new ProductIsOutOfStock(product);
            }
        } else {
            throw new ProductDoesNotExists(product);
        }
    }
}
