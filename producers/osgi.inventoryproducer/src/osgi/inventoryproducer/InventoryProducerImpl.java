package osgi.inventoryproducer;

import java.util.HashMap;
import java.util.Scanner;

public class InventoryProducerImpl implements InventoryProducer  {

	@Override
	public void main() { //main method to run the program
		//creating a scanner object to get the user input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Inventory Management System");
		System.out.println("Please select the operation you want to perform:");
		System.out.println("add - Add Inventory, view - View Inventory, update - Update Inventory, delete - Delete Inventory, search - Search Inventory");
		String operation = scanner.nextLine();

		//validating the operation
		while (!operation.equals("add") && !operation.equals("view") && !operation.equals("update") && !operation.equals("delete") && !operation.equals("search")) {
			System.out.println("Invalid Operation. Enter Valid-operation:");
			System.out.println("add,view,update,delete,search");
			operation = scanner.nextLine();
		}

		//performing the operation based on the user input
		//looping through the operations until user enter exit/-1
		while (!operation.equals("exit") && !operation.equals("-1")) {
			System.out.println("Need to perform another operation? Enter the operation or enter exit/-1 to exit:");	
			operation = scanner.nextLine();

			//selecting the relavant operation
			switch (operation) {
				case "add": //calling the method to add inventory
					System.out.println("Enter the Item Id:");
					String id = scanner.nextLine();
					System.out.println("Enter the Item Name:");
					String name = scanner.nextLine();
					System.out.println("Enter the Item Description:");
					String description = scanner.nextLine();
					System.out.println("Enter the Item Quantity:");
					int quantity = scanner.nextInt();
					scanner.nextLine();
					addInventory(id, name, description, quantity);
					break;
	
				case "view": //calling the method to view inventory
					viewInventroy();
					break;
	
				case "update": //calling the method to update inventory
					System.out.println("Enter the Item Id:");
					String id1 = scanner.nextLine();
					System.out.println("Enter the Item Name:");
					String name1 = scanner.nextLine();
					System.out.println("Enter the Item Description:");
					String description1 = scanner.nextLine();
					System.out.println("Enter the Item Quantity:");
					int quantity1 = scanner.nextInt();
					scanner.nextLine();
					updateInventory(id1, name1, description1, quantity1);
					break;
	
				case "delete": //calling the method to delete inventory
					System.out.println("Enter the Item Id:");
					String id2 = scanner.nextLine();
					deleteInventory(id2);
					break;
	
				case "search": //calling the method to search inventory
					System.out.println("Enter the Item Id:");
					String id3 = scanner.nextLine();
					searchInventory(id3);
					break;
	
			}
		
		}
		
	}
	//class to store the inventory items
	class InventoryItem {
	    
	    String name;
	    String description;
	    int quantity;
	    
	    //constructor
	    public InventoryItem(String name, String description, int quantity) {
			this.name = name;
			this.description = description;
			this.quantity = quantity;
	    }
	}
	//map to store the inventories in the run time since we dont use a DB
	//the string in the hashmap stores the item Id:
	HashMap <String,InventoryItem> inventoryHashMap = new HashMap<>();
	
	// Initializing the inventory items (dummy data)
    public InventoryProducerImpl() {
        inventoryHashMap.put("P001", new InventoryItem("HeadPhone", "JBL dual driver harmon cardon tuned headphones", 10));
        inventoryHashMap.put("P002", new InventoryItem("Laptop", "Dell Inspiron 15 5000 series", 5));
        inventoryHashMap.put("P003", new InventoryItem("Mobile", "Samsung Galaxy S10", 15));
        inventoryHashMap.put("P004", new InventoryItem("Tablet", "Apple Ipad Pro", 20));
        inventoryHashMap.put("P005", new InventoryItem("Smart Watch", "Apple Watch Series 5", 25));
    }
	
	//@Override //adding inventory 
	public void addInventory(String id, String name, String description, int quantity){
		if (inventoryHashMap.containsKey(id)) { //check if the item exists
			System.out.println("Item already exists with the given id.Please use a different id");
			return;
		}
		else{ //add the item to the inventory
			inventoryHashMap.put(id, new InventoryItem(name, description, quantity));
			System.out.println("Item Added Successfully...");
			System.out.println("Item Id :"+ id + "  Name:" + name + "  Qty :" + quantity );
		}
	}

	//@Override //view all inventroy
	public void viewInventroy() {
		System.out.println("Inventory Items :");
		for (String key : inventoryHashMap.keySet()) { //iterate through the hashmap
			InventoryItem item = inventoryHashMap.get(key);
			System.out.println("Item Id :"+ key + ",  Name:" + item.name + ",  Details :" + item.description + ",  Qty :" + item.quantity);
		}
		
	}

	//@Override //update inventory
	public void updateInventory(String id, String name, String description, int quantity) {
		if (!inventoryHashMap.containsKey(id)) { //check if the item exists
			System.out.println("Item does not exist with the given id.Please use a existing id:");
			return;
		}
		else{
			inventoryHashMap.put(id, new InventoryItem(name, description, quantity));
			System.out.println("Item updated Successfully...");
			System.out.println("Item Id :"+ id + ",  Name:" + name + ",  Details :" + description + ",  Qty :" + quantity);
		}
	}

	//@Override //delete inventory
	public void deleteInventory(String id) {
		if (!inventoryHashMap.containsKey(id)) { //check if the item exists
			System.out.println("Delete Failed, Item Not Found..!");
			return;
		}
		else{
			inventoryHashMap.remove(id); //delete the item
			System.out.println("Item deleted Successfully...");
			System.out.println("Item Id :"+ id );
		}
	}

	//@Override //search inventory
	public void searchInventory(String id) {
		if (!inventoryHashMap.containsKey(id)) { //check if the item exists
			System.out.println("Item Not Found..!");
			return;
		}
		else{
			InventoryItem item = inventoryHashMap.get(id);
			System.out.println("Item Id :"+ id + ",  Name:" + item.name + ",  Details :" + item.description + ",  Qty :" + item.quantity);
		}
		
	}

}
