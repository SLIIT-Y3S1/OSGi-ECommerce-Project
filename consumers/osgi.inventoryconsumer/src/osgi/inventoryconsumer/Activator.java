package osgi.inventoryconsumer;

//import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.inventoryproducer.InventoryProducer;

public class Activator implements BundleActivator {

    private ServiceReference<InventoryProducer> serviceReference;
    private InventoryProducer service;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Console Inventory Consumer Started");

        serviceReference = bundleContext.getServiceReference(InventoryProducer.class);
        if (serviceReference != null) {
            service = bundleContext.getService(serviceReference);
            if (service != null) {
                runConsole();
            } else {
                System.out.println("Inventory Service Not Available.");
            }
        } else {
            System.out.println("Inventory Service Not Found.");
        }
    }

    private void runConsole() {
    	
        service.main();
        
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Console Inventory Consumer Stopped");
        if (serviceReference != null) {
            bundleContext.ungetService(serviceReference);
        }
    }
}
