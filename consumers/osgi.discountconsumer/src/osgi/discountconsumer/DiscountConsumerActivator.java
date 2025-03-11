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
                 double discountedTotal = discountService.applyDiscount("VIP123", 500.00);
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
