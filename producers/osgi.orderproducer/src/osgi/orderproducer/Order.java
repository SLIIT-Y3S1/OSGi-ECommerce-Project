package osgi.orderproducer;

import java.util.Map;

public class Order {
    private String customerId;
    private Map<String, Integer> items;
    private double totalAmount;
    private String status;
    private long timestamp;

    public Order(String customerId, Map<String, Integer> items, double totalAmount) {
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = "NEW";
        this.timestamp = System.currentTimeMillis();
    }

    public String getCustomerId() { return customerId; }
    public Map<String, Integer> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}