package osgi.paymentproducer;

public interface PaymentProducer {
	boolean processPayment(String orderId, double amount, String paymentMethod);
}
