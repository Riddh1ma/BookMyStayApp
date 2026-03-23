import java.util.*;

/**
 * Book My Stay Application
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates:
 * - List (ordered storage)
 * - History tracking
 * - Reporting without modifying data
 *
 * @author Riddhima
 * @version 8.0
 */

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// Booking History
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }
}

// Report Service
class BookingReportService {

    // Display all bookings
    public void displayAll(List<Reservation> bookings) {
        System.out.println("\n=== Booking History ===");

        for (Reservation r : bookings) {
            r.display();
        }
    }

    // Summary report
    public void generateSummary(List<Reservation> bookings) {
        System.out.println("\n=== Booking Summary Report ===");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : bookings) {
            countMap.put(r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Booking History =====");

        BookingHistory history = new BookingHistory();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("S101", "Alice", "Single"));
        history.addReservation(new Reservation("D202", "Bob", "Double"));
        history.addReservation(new Reservation("S102", "Charlie", "Single"));
        history.addReservation(new Reservation("SU301", "David", "Suite"));

        BookingReportService reportService = new BookingReportService();

        // Display full history
        reportService.displayAll(history.getAllBookings());

        // Generate summary
        reportService.generateSummary(history.getAllBookings());

        System.out.println("\nData is read-only. No modifications done ✅");
    }
}