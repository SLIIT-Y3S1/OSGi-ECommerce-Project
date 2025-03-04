package osgi.testconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import osgi_testproducer.TestServiceProducer;

public class Activator implements BundleActivator {
	ServiceReference reference;

	public void start(BundleContext context) throws Exception {
			System.out.println("Test Consumer Started");
		
		ServiceReference<?> ref = context.getServiceReference(TestServiceProducer.class.getName());
		if (ref != null) {
			TestServiceProducer serivce = (TestServiceProducer) context.getService(ref);
			serivce.testMethod();
            
        }
	}

	public void stop(BundleContext bundleContext) throws Exception {
		
	}

}
