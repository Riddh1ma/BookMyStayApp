import java.util.*;

/**
 * Book My Stay Application
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates:
 * - Queue (FIFO processing)
 * - HashMap (inventory)
 * - Set (unique room IDs)
 *
 * @author Riddhima
 * @version 6.0
 */

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void reduceRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory: " + inventory);
    }
}

// Booking Service
class BookingService {

    private Queue<Reservation> queue;
    private InventoryService inventoryService;

    // To prevent duplicates
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type -> allocated IDs
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    public BookingService(Queue<Reservation> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {
        System.out.println("\n=== Processing Bookings ===");

        while (!queue.isEmpty()) {
            Reservation r = queue.poll();

            if (inventoryService.isAvailable(r.roomType)) {

                String roomId = generateRoomId(r.roomType);

                allocatedRoomIds.add(roomId);

                allocationMap.putIfAbsent(r.roomType, new HashSet<>());
                allocationMap.get(r.roomType).add(roomId);

                inventoryService.reduceRoom(r.roomType);

                System.out.println("Booking CONFIRMED for " + r.guestName +
                        " | Room Type: " + r.roomType +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + r.guestName +
                        " | Room Type: " + r.roomType + " not available");
            }
        }
    }

    private String generateRoomId(String roomType) {
        String roomId;

        do {
            roomId = roomType.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {
        System.out.println("\n=== Allocated Rooms ===");
        System.out.println(allocationMap);
    }
}

// Main class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Room Allocation =====");

        // Step 1: Create queue (from UC5)
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single"));
        queue.add(new Reservation("Bob", "Double"));
        queue.add(new Reservation("Charlie", "Suite"));
        queue.add(new Reservation("David", "Single")); // extra request

        // Step 2: Inventory
        InventoryService inventoryService = new InventoryService();

        // Step 3: Booking service
        BookingService bookingService = new BookingService(queue, inventoryService);

        // Step 4: Process bookings
        bookingService.processBookings();

        // Step 5: Show results
        bookingService.displayAllocations();
        inventoryService.displayInventory();
    }
}