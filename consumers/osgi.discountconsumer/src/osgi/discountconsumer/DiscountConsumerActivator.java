package osgi.discountconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.discountproducer.DiscountServiceProducer;
import java.util.Scanner;


public class DiscountConsumerActivator implements BundleActivator {
	  private ServiceReference<?> discountServiceReference;
        @Override
        public void start(BundleContext context) throws Exception {
        	 System.out.println("Discount Consumer Started");

             discountServiceReference = context.getServiceReference(DiscountServiceProducer.class.getName());
             if (discountServiceReference != null) {
            	 DiscountServiceProducer discountService = (DiscountServiceProducer) context.getService(discountServiceReference);
            	// Create Scanner to get input from the console
                 Scanner scanner = new Scanner(System.in);

                 System.out.print("Enter Customer ID: ");
                 String customerId = scanner.nextLine();

                 System.out.print("Enter Order Total: ");
                 double orderTotal = scanner.nextDouble();
            	 
            	 
                 double discountedTotal = discountService.applyDiscount(customerId, orderTotal);
                 System.out.println("Original Total: $" + orderTotal);
                 System.out.println("Discounted Total: $" + discountedTotal);

                 
         
             } else {
                 System.out.println("DiscountService not found!");
             }
        }

        @Override
        public void stop(BundleContext context) throws Exception {
            System.out.println("Discount Consumer Stopped");
        }
    }
