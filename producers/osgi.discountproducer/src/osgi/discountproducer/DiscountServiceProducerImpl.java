package osgi.discountproducer;

import java.util.HashMap;
import java.util.Map;

public class DiscountServiceProducerImpl implements DiscountServiceProducer{
	private static final Map<String, Double> customer_discount = new HashMap();

    static {
    	customer_discount.put("VIP123", 0.15); // 15% discount for VIP customers
    	customer_discount.put("NEWUSER", 0.10); // 10% discount for new users
    }

    @Override
    public double applyDiscount(String customerId, double orderTotal) {
        double discount = customer_discount.getOrDefault(customerId, 0.05); // Default 5%
        double discountedPrice = orderTotal * (1 - discount);

        // Print header for the table
        System.out.println("+------------------+---------------------+-----------------------+");
        System.out.println("| Customer ID      | Original Total      | Discount Applied      |");
        System.out.println("+------------------+---------------------+-----------------------+");

        // Print the discount details as a table row
        System.out.printf("| %-16s | %-19.2f | %-21.2f |%n", customerId, orderTotal, discount * 100);
        System.out.println("+------------------+---------------------+-----------------------+");

        // Print the discounted price
        System.out.printf("Discounted Price for Customer %s: %.2f%n", customerId, discountedPrice);
        return discountedPrice;
    }

	
}
