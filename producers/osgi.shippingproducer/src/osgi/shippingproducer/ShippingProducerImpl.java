package osgi.shippingproducer;

import java.util.HashMap;
import java.util.Scanner;

public class ShippingProducerImpl implements ShippingProducer {

    Scanner scanner = new Scanner(System.in);

    //add class to store details 
    public class ShippingDetails {
		
        String OrderId;
        String shippingStatus;
        String shippingDate;
        String shippingAddress;
        String shippingCost;
    

        //constructor for the class
        public ShippingDetails( String orderId, String shippingStatus, String shippingDate,
                    String shippingAddress, String shippingCost) {
                super();
 
                this.OrderId = orderId;
                this.shippingStatus = shippingStatus;
                this.shippingDate = shippingDate;
                this.shippingAddress = shippingAddress;
                this.shippingCost = shippingCost;
            }
    }    
    //hashmap to store data instead of Db
    HashMap<String, ShippingDetails> shippingDetails = new HashMap<String, ShippingDetails>();

    //dummy data for the hashmap
    public ShippingProducerImpl() {
        shippingDetails.put("S001", new ShippingDetails("TS001", "OnProcess", "2021-09-01", "Colombo", "1000"));
        shippingDetails.put("S002", new ShippingDetails("TS002", "Delivered", "2021-09-01", "Kandy", "1200"));
        shippingDetails.put("S003", new ShippingDetails("TS003", "onCustoms", "2021-09-01", "Galle", "1500"));
        shippingDetails.put("S004", new ShippingDetails("TS004", "Completed", "2021-09-01", "Matara", "2000"));
        shippingDetails.put("S005", new ShippingDetails("TS005", "Returned", "2021-09-01", "Jaffna", "2500"));
    }

        @Override
        public void main() {
                System.out.println("Welcome to Shipping Management System");
                System.out.println("Please select the operation you want to perform:");
                System.out.println("add - Add Shipping, view - View Shipping, update - Update Shipping, delete - Delete Shipping, search - Search Shipping");
                String operation = scanner.nextLine();

                //validating the operation
                switch (operation) {
                        case "add":
                                System.out.println("Enter the Order Id:");
                                String orderId = scanner.nextLine();
                                System.out.println("Enter the Shipping Status:");
                                String shippingStatus = scanner.nextLine();
                                System.out.println("Enter the Shipping Date:");
                                String shippingDate = scanner.nextLine();
                                System.out.println("Enter the Shipping Address:");
                                String shippingAddress = scanner.nextLine();
                                System.out.println("Enter the Shipping Cost:");
                                String shippingCost = scanner.nextLine();
                                addShipping(orderId, shippingStatus, shippingDate, shippingAddress, shippingCost);
                                break;
                        case "view":
                                viewShipping();
                                break;
                        case "update":
                                System.out.println("Enter the Shipping Id:");
                                String ShippingId = scanner.nextLine();
                                System.out.println("Enter the Shipping Status:");
                                String shippingStatus1 = scanner.nextLine();
                                updateShipping(ShippingId, shippingStatus1);
                                break;
                        case "delete":
                                System.out.println("Enter the Shipping Id:");
                                String ShippingId1 = scanner.nextLine();
                                deleteShipping(ShippingId1);
                                break;
                        default:
                                System.out.println("Invalid Operation. Enter Valid-operation:");
                                System.out.println("add,view,update,delete,search");
                                operation = scanner.nextLine();
                                break;
                }
        }

        //add shipping details
        public void addShipping (String orderId, String shippingStatus, String shippingDate, String shippingAddress, String shippingCost) {
                //adding the shipping details to the hashmap
                shippingDetails.put(orderId, new ShippingDetails(orderId, shippingStatus, shippingDate, shippingAddress, shippingCost));
                System.out.println("Shipping Details added successfully");
        }

        //view shipping details all
        public void viewShipping (){
                //looping through the hashmap to view all the shipping details
                for (String key : shippingDetails.keySet()) {
                        System.out.println("Shipping ID: " + key);
                        System.out.println("Order ID: " + shippingDetails.get(key).OrderId);
                        System.out.println("Shipping Status: " + shippingDetails.get(key).shippingStatus);
                        System.out.println("Shipping Date: " + shippingDetails.get(key).shippingDate);
                        System.out.println("Shipping Address: " + shippingDetails.get(key).shippingAddress);
                        System.out.println("Shipping Cost: " + shippingDetails.get(key).shippingCost);
                }
        }  
        
        //update shipping details
        public void updateShipping (String ShippingId, String shippingStatus) {
                //checking if the shipping details exist
                if (shippingDetails.containsKey(ShippingId)) {
                        //updating the shipping details retriev other details with the shipping id
                        shippingDetails.put(ShippingId, new ShippingDetails(shippingDetails.get(ShippingId).OrderId,shippingStatus, shippingDetails.get(ShippingId).shippingDate, shippingDetails.get(ShippingId).shippingAddress, shippingDetails.get(ShippingId).shippingCost));
                        System.out.println("Shipping Details updated successfully");
                        System.out.println("Shipping ID: " + ShippingId);
                        System.out.println("Order ID: " + shippingDetails.get(ShippingId).OrderId);
                        System.out.println("Shipping Status: " + shippingDetails.get(ShippingId).shippingStatus);
                } else {
                        System.out.println("Shipping Details not found");
                }
        }

        //delete shipping details
        public void deleteShipping (String ShippingId) {
                //checking if the shipping details exist
                if (shippingDetails.containsKey(ShippingId)) {
                        //deleting the shipping details
                        shippingDetails.remove(ShippingId);
                        System.out.println("Shipping Details deleted successfully");
                } else {
                        System.out.println("Shipping Details not found");
                }
        }
}

