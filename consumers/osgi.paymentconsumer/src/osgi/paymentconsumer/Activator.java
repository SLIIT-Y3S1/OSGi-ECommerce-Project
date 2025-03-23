package osgi.paymentconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.paymentproducer.PaymentProducer;


public class Activator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Payment Consumer bundle starting...");
        
        // Look up the PaymentProducer service
        ServiceReference<?> reference = context.getServiceReference(PaymentProducer.class.getName());
        
        if (reference != null) {
            // Get the service
            PaymentProducer paymentProducer = (PaymentProducer) context.getService(reference);
            
            if (paymentProducer != null) {
                // Process a single order
                String orderId = "ORD-12345";
                double amount = 99.99;
                String cardType = "VISA";
                
                System.out.println("Processing order: " + orderId);
                boolean result = paymentProducer.processPayment(orderId, amount, cardType);
                
                System.out.println("Payment result: " + (result ? "SUCCESS" : "FAILED"));
                
                // Release the service
                context.ungetService(reference);
            }
        } else {
            System.out.println("PaymentProducer service not available.");
        }
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Payment Consumer bundle stopped.");
    }
    
}

