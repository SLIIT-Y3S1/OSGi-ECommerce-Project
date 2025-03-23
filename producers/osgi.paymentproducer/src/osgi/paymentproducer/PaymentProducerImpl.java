package osgi.paymentproducer;

public class PaymentProducerImpl implements PaymentProducer {
    
    @Override
    public boolean processPayment(String orderId, double amount, String cardType) {
        // Log to console
        System.out.println("Processing payment for order " + orderId + 
                          " - Amount: " + amount + 
                          " - Card Type: " + cardType);
        
        boolean paymentSuccessful = validateCardPayment(amount, cardType);
        
        // Log the result
        System.out.println("Payment result for order " + orderId + ": " + 
                          (paymentSuccessful ? "SUCCESSFUL" : "FAILED"));
        
        return paymentSuccessful;
    }

    private boolean validateCardPayment(double amount, String cardType) {
        // Check for valid amount
        if (amount <= 0) {
            System.out.println("Payment failed: Invalid amount");
            return false;
        }
        
        // Check for supported card types
        if (cardType == null || 
            !(cardType.equalsIgnoreCase("VISA") || 
              cardType.equalsIgnoreCase("MASTERCARD"))) {
            System.out.println("Payment failed: Unsupported card type. We only accept VISA or MASTERCARD");
            return false;
        }
        
        // Card processing
        if (cardType.equalsIgnoreCase("VISA")) {
            System.out.println("VISA card payment processed");
            return true;
        } else if (cardType.equalsIgnoreCase("MASTERCARD")) {
            System.out.println("MASTERCARD payment processed");
            return true;
        }
        
        return false;
       
    }
}