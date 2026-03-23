import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates Queue for handling booking requests fairly (FIFO).
 *
 * @author Riddhima
 * @version 5.0
 */

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // Add request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added:");
        reservation.displayReservation();
    }

    // View all requests
    public void displayQueue() {
        System.out.println("\n=== Booking Request Queue ===");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Booking Requests =====");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in FIFO order. No allocation done yet.");
    }
}