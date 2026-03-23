import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for managing room availability.
 *
 * @author Riddhima
 * @version 3.0
 */

// Inventory class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Main class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Inventory Management =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Double Room availability...");
        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully!");
    }
}