package packt.java9.by.example.mybusiness.inventory;

import packt.java9.by.example.mybusiness.product.ProductIsOutOfStock;

public class InventoryItem {

    private long amountOnStock = 0;

    public long amount() {
        return amountOnStock;
    }

    public InventoryItem(long n) {
        amountOnStock = n;
    }

    void store(long n) {
        amountOnStock += n;
    }

    void remove(long n) {
        amountOnStock -= n;
    }

    public long getAmountOnStock() {
        return amountOnStock;
    }
}
