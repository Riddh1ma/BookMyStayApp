import java.util.*;

/**
 * Book My Stay Application
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates:
 * - Input validation
 * - Custom exceptions
 * - Fail-fast design
 *
 * @author Riddhima
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 0); // intentionally 0 to test error
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        checkAvailability(roomType);

        // Safe update
        inventory.put(roomType, inventory.get(roomType) - 1);

        if (inventory.get(roomType) < 0) {
            throw new InvalidBookingException("Inventory went negative!");
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Validation & Errors =====");

        InventoryService inventory = new InventoryService();

        // Test cases
        String[] testRequests = {
                "Single",
                "Suite",     // should fail (0 available)
                "Luxury",    // invalid type
                "Double"
        };

        for (String roomType : testRequests) {
            try {
                System.out.println("\nProcessing booking for: " + roomType);

                inventory.allocateRoom(roomType);

                System.out.println("Booking SUCCESS for " + roomType);

            } catch (InvalidBookingException e) {
                System.out.println("Booking FAILED: " + e.getMessage());
            }
        }

        System.out.println("\nSystem continues running safely ✅");
        inventory.displayInventory();
    }
}