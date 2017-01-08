package packt.java9.by.example.mybusiness.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import packt.java9.by.example.mybusiness.product.Order;
import packt.java9.by.example.mybusiness.product.OrderItem;
import packt.java9.by.example.mybusiness.product.ProductDoesNotExists;
import packt.java9.by.example.mybusiness.product.ProductIsOutOfStock;

import java.util.concurrent.Flow;

public class InventoryKeeper implements Flow.Subscriber<Order> {
    private static final Logger log = LoggerFactory.getLogger(InventoryKeeper.class);
    private final Inventory inventory;

    public InventoryKeeper(@Autowired Inventory inventory) {
        this.inventory = inventory;
    }

    private Flow.Subscription subscription = null;
    private static final long INFINITE = Long.MAX_VALUE;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log.info("onSubscribe was called");
        subscription.request(INFINITE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Order order) {
        log.info("onNext was called for {}", order);
        for (OrderItem item : order.getItems()) {
            try {
                inventory.remove(item.getProduct(), item.getAmount());
            } catch (ProductDoesNotExists | ProductIsOutOfStock exception) {
                log.error("Product does not exists or not enough", exception);
                subscription.cancel();
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("onError was called for {}", throwable);
    }

    @Override
    public void onComplete() {
        log.info("onComplete was called");
    }
}
