package osgi.orderproducer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class OrderProducerImp implements OrderProducer {
    // Map to store orders
    private final HashMap<String, Order> orderMap = new HashMap<>();
    
    // Logger callback
    private Consumer<String> logCallback;
    
    public OrderProducerImp() {
        log("Initializing OrderProducerImp");
        
        // Sample orders (can be removed if unnecessary)
        Map<String, Integer> items1 = new HashMap<>();
        items1.put("P001", 2);
        items1.put("P002", 1);
        orderMap.put("ORD001", new Order("CUST001", items1, 1500.0));
        
        Map<String, Integer> items2 = new HashMap<>();
        items2.put("P003", 3);
        orderMap.put("ORD002", new Order("CUST002", items2, 900.0));
        
        log("Sample orders created. Total orders: " + orderMap.size());
    }
    
    // Set a log callback function that the consumer can provide
    public void setLogCallback(Consumer<String> logCallback) {
        this.logCallback = logCallback;
        log("Logging redirected to UI");
    }
    
    // Helper method to log messages
    private void log(String message) {
        // Log to system out only
        System.out.println("[OrderProducer] " + message);
    }
    
    @Override
    public String createOrder(String customerId, String[] itemIds, int[] quantities) {
        log("Creating new order for customer: " + customerId);
        
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8);
        Map<String, Integer> items = new HashMap<>();
        
        for (int i = 0; i < itemIds.length; i++) {
            items.put(itemIds[i], quantities[i]);
            log("Added item: " + itemIds[i] + ", quantity: " + quantities[i]);
        }
        
        double total = 100.0; // Placeholder for actual price calculation
        orderMap.put(orderId, new Order(customerId, items, total));
        
        log("Order created successfully. Order ID: " + orderId + ", Total: $" + total);
        return orderId;
    }
    
    @Override
    public Map<String, Order> getOrders() {
        log("Getting all orders. Total count: " + orderMap.size());
        return new HashMap<>(orderMap);
    }
    
    @Override
    public boolean updateOrderStatus(String orderId, String status) {
        log("Updating order status. Order ID: " + orderId + ", New Status: " + status);
        
        if (orderMap.containsKey(orderId)) {
            Order order = orderMap.get(orderId);
            String oldStatus = order.getStatus();
            order.setStatus(status);
            
            log("Order status updated successfully. Order ID: " + orderId + 
                ", Old Status: " + oldStatus + ", New Status: " + status);
            return true;
        }
        
        log("Failed to update order status. Order not found: " + orderId);
        return false;
    }
    
    @Override
    public boolean cancelOrder(String orderId) {
        log("Canceling order: " + orderId);
        boolean result = updateOrderStatus(orderId, "CANCELED");
        
        if (result) {
            log("Order canceled successfully: " + orderId);
        } else {
            log("Failed to cancel order. Order not found: " + orderId);
        }
        
        return result;
    }
    
    @Override
    public Order getOrder(String orderId) {
        Order order = orderMap.get(orderId);
        
        if (order != null) {
            log("Order retrieved. Order ID: " + orderId + 
                ", Customer: " + order.getCustomerId() + 
                ", Status: " + order.getStatus());
        } else {
            log("Order not found: " + orderId);
        }
        
        return order;
    }
}