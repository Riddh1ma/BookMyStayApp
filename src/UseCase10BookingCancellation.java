import java.util.*;

/**
 * Book My Stay Application
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates:
 * - Stack (LIFO rollback)
 * - Validation before cancellation
 * - Inventory restoration
 *
 * @author Riddhima
 * @version 10.0
 */

// Reservation class
class Reservation {
    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 0);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void increaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Cancellation Service
class CancellationService {

    // Track active bookings
    private Map<String, Reservation> activeBookings = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds = new Stack<>();

    private InventoryService inventoryService;

    public CancellationService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Add booking (simulate confirmed booking)
    public void addBooking(Reservation r) {
        activeBookings.put(r.reservationId, r);
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Cancellation FAILED: Reservation not found");
            return;
        }

        Reservation r = activeBookings.get(reservationId);

        // Push to stack (rollback tracking)
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventoryService.increaseRoom(r.roomType);

        // Remove booking
        activeBookings.remove(reservationId);

        System.out.println("Cancellation SUCCESS for " + reservationId +
                " | Room Type: " + r.roomType);
    }

    // Display rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + releasedRoomIds);
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Cancellation & Rollback =====");

        InventoryService inventory = new InventoryService();
        CancellationService cancellationService = new CancellationService(inventory);

        // Simulate confirmed bookings
        cancellationService.addBooking(new Reservation("S101", "Single"));
        cancellationService.addBooking(new Reservation("D202", "Double"));
        cancellationService.addBooking(new Reservation("SU301", "Suite"));

        // Perform cancellations
        cancellationService.cancelBooking("D202");   // valid
        cancellationService.cancelBooking("X999");   // invalid
        cancellationService.cancelBooking("S101");   // valid

        // Display results
        cancellationService.displayRollbackStack();
        inventory.displayInventory();

        System.out.println("\nSystem state restored consistently ✅");
    }
}