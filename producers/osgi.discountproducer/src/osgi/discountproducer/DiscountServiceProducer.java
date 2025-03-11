package osgi.discountproducer;

public interface DiscountServiceProducer {
	double applyDiscount(String customerId, double orderTotal);
}
