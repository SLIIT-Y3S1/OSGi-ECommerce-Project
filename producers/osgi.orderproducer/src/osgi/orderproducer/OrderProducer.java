package osgi.orderproducer;

import java.util.Map;

public interface OrderProducer {
    String createOrder(String customerId, String[] itemIds, int[] quantities);
    Map<String, Order> getOrders();
    boolean updateOrderStatus(String orderId, String status);
    boolean cancelOrder(String orderId);
    Order getOrder(String orderId);
}