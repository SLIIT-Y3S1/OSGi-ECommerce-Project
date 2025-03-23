package osgi.orderconsumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import osgi.orderproducer.Order;
import osgi.orderproducer.OrderProducer;

public class ConsumerUI {
    private JFrame frame;
    private JTextArea outputArea;
    private JTextField inputField;
    private ActionListener currentInputHandler;
    private OrderProducer orderProducer;
    
    public ConsumerUI(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
    
    public void show() {
        // Create UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> createAndShowConsole());
    }
    
    public boolean isVisible() {
        return frame != null && frame.isVisible();
    }
    
    public void dispose() {
        if (frame != null) {
            SwingUtilities.invokeLater(() -> frame.dispose());
        }
    }
    
    private void createAndShowConsole() {
        // Create main frame
        frame = new JFrame("Order Management Console");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        
        // Add window listener to detect when frame is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame = null; // Set to null when closed
            }
        });
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Output text area (console)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(580, 320));
        
        // Input field
        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Add components to panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputField, BorderLayout.SOUTH);
        
        // Setup input action
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = inputField.getText().trim();
                inputField.setText("");
                
                // Echo the command
                println("> " + command);
                
                // Process the command
                processCommand(command);
            }
        });
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        
        // Initial instructions
        println("Order Management Console");
        println("------------------------");
        println("Available commands:");
        println("create - Create a new order");
        println("view - View all orders");
        println("update <orderId> <status> - Update order status");
        println("cancel <orderId> - Cancel an order");
        println("search <orderId> - Search for an order");
        println("relaunch - Relaunch the UI if closed");
        println("exit - Close the console");
        println("");
        println("Enter a command:");
    }
    
    private void println(String text) {
        outputArea.append(text + "\n");
        // Scroll to the bottom
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    private void processCommand(String command) {
        if (command.isEmpty()) {
            return;
        }
        
        String[] parts = command.split("\\s+", 3); // Split by whitespace
        String operation = parts[0].toLowerCase();
        
        try {
            switch (operation) {
                case "create":
                    handleCreateCommand();
                    break;
                    
                case "view":
                    handleViewCommand();
                    break;
                    
                case "update":
                    if (parts.length >= 3) {
                        String orderId = parts[1];
                        String status = parts[2];
                        handleUpdateCommand(orderId, status);
                    } else {
                        println("Usage: update <orderId> <status>");
                    }
                    break;
                    
                case "cancel":
                    if (parts.length >= 2) {
                        String orderId = parts[1];
                        handleCancelCommand(orderId);
                    } else {
                        println("Usage: cancel <orderId>");
                    }
                    break;
                    
                case "search":
                    if (parts.length >= 2) {
                        String orderId = parts[1];
                        handleSearchCommand(orderId);
                    } else {
                        println("Usage: search <orderId>");
                    }
                    break;
                    
                case "relaunch":
                    if (!isVisible()) {
                        show();
                    }
                    break;
                    
                case "exit":
                    frame.dispose();
                    break;
                    
                default:
                    println("Unknown command: " + operation);
                    println("Available commands: create, view, update, cancel, search, relaunch, exit");
            }
        } catch (Exception e) {
            println("Error: " + e.getMessage());
        }
    }
    
    private void handleCreateCommand() {
        println("Enter customer ID:");
        setInputHandler(customerId -> {
            println("Customer ID: " + customerId);
            println("Enter number of items:");
            
            setInputHandler(numItemsStr -> {
                try {
                    int numItems = Integer.parseInt(numItemsStr);
                    println("Number of items: " + numItems);
                    
                    String[] itemIds = new String[numItems];
                    int[] quantities = new int[numItems];
                    
                    getItemDetails(0, numItems, itemIds, quantities);
                    
                } catch (NumberFormatException e) {
                    println("Invalid number. Please enter a valid integer.");
                    resetInputHandler();
                }
            });
        });
    }
    
    private void getItemDetails(int index, int total, String[] itemIds, int[] quantities) {
        if (index >= total) {
            // All items collected, create the order
            String orderId = orderProducer.createOrder(
                    itemIds[0], // Using first item as customer ID (fix this in real implementation)
                    itemIds,
                    quantities);
            println("Created Order ID: " + orderId);
            resetInputHandler();
            return;
        }
        
        println("Enter Item ID " + (index + 1) + ":");
        setInputHandler(itemId -> {
            itemIds[index] = itemId;
            println("Item ID: " + itemId);
            
            println("Enter Quantity:");
            setInputHandler(quantityStr -> {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    quantities[index] = quantity;
                    println("Quantity: " + quantity);
                    
                    // Get next item
                    getItemDetails(index + 1, total, itemIds, quantities);
                    
                } catch (NumberFormatException e) {
                    println("Invalid quantity. Please enter a valid integer.");
                    getItemDetails(index, total, itemIds, quantities); // Try again
                }
            });
        });
    }
    
    private void handleViewCommand() {
        Map<String, Order> orders = orderProducer.getOrders();
        if (orders.isEmpty()) {
            println("No orders found.");
        } else {
            println("All Orders:");
            for (Map.Entry<String, Order> entry : orders.entrySet()) {
                println("Order ID: " + entry.getKey() +
                        ", Customer: " + entry.getValue().getCustomerId() +
                        ", Status: " + entry.getValue().getStatus() +
                        ", Total: $" + entry.getValue().getTotalAmount());
            }
        }
    }
    
    private void handleUpdateCommand(String orderId, String status) {
        if (orderProducer.updateOrderStatus(orderId, status)) {
            println("Order status updated to " + status);
        } else {
            println("Order not found: " + orderId);
        }
    }
    
    private void handleCancelCommand(String orderId) {
        if (orderProducer.cancelOrder(orderId)) {
            println("Order canceled: " + orderId);
        } else {
            println("Order not found: " + orderId);
        }
    }
    
    private void handleSearchCommand(String orderId) {
        Order order = orderProducer.getOrder(orderId);
        if (order != null) {
            println("Order ID: " + orderId);
            println("Customer ID: " + order.getCustomerId());
            println("Status: " + order.getStatus());
            println("Total Amount: $" + order.getTotalAmount());
            println("Items:");
            for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                println("  - Item: " + entry.getKey() + ", Quantity: " + entry.getValue());
            }
        } else {
            println("Order not found: " + orderId);
        }
    }
    
    // Command input handling
    private void setInputHandler(InputHandler handler) {
        currentInputHandler = e -> {
            String input = inputField.getText().trim();
            inputField.setText("");
            println("> " + input);
            handler.handleInput(input);
        };
        
        inputField.removeActionListener(inputField.getActionListeners()[0]);
        inputField.addActionListener(currentInputHandler);
    }
    
    private void resetInputHandler() {
        inputField.removeActionListener(currentInputHandler);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = inputField.getText().trim();
                inputField.setText("");
                println("> " + command);
                processCommand(command);
            }
        });
    }
    
    // Simple functional interface for input handling
    @FunctionalInterface
    private interface InputHandler {
        void handleInput(String input);
    }
}