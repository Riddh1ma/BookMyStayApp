import java.util.*;

/**
 * Book My Stay Application
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates:
 * - Multi-threading
 * - Synchronization
 * - Preventing race conditions
 *
 * @author Riddhima
 * @version 11.0
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

// Thread-safe Inventory
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        if (inventory.getOrDefault(roomType, 0) > 0) {

            int count = inventory.get(roomType);
            inventory.put(roomType, count - 1);

            System.out.println(Thread.currentThread().getName() +
                    " → Booking SUCCESS for " + guestName +
                    " (" + roomType + ")");

            return true;

        } else {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking FAILED for " + guestName +
                    " (" + roomType + ")");

            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// Shared Booking Queue
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // synchronized add
    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    // synchronized remove
    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Thread (Worker)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            inventory.allocateRoom(r.roomType, r.guestName);
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Concurrent Booking =====");

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Add requests (simulating multiple users)
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // extra
        queue.addRequest(new Reservation("David", "Double"));
        queue.addRequest(new Reservation("Eve", "Double")); // extra

        // Create threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(queue, inventory, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();

        System.out.println("\nNo double booking occurred ✅ (Thread-safe)");
    }
}