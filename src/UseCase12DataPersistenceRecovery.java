import java.io.*;
import java.util.*;

/**
 * Book My Stay Application
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates:
 * - Serialization & Deserialization
 * - File handling
 * - System recovery after restart
 *
 * @author Riddhima
 * @version 12.0
 */

// Reservation must be Serializable
class Reservation implements Serializable {
    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " (" + roomType + ")";
    }
}

// Wrapper class to persist full system state
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nState saved successfully ✅");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("State loaded successfully ✅");
            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh...");

            // Return default state if file missing/corrupt
            Map<String, Integer> defaultInventory = new HashMap<>();
            defaultInventory.put("Single", 2);
            defaultInventory.put("Double", 1);

            return new SystemState(defaultInventory, new ArrayList<>());
        }
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Persistence & Recovery =====");

        // Load previous state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> history = state.bookingHistory;

        // Display recovered state
        System.out.println("\nRecovered Inventory: " + inventory);
        System.out.println("Recovered Bookings: " + history);

        // Simulate new booking
        System.out.println("\nAdding new booking...");
        Reservation r = new Reservation("R" + (history.size() + 1), "Single");

        if (inventory.get("Single") > 0) {
            inventory.put("Single", inventory.get("Single") - 1);
            history.add(r);
            System.out.println("Booking SUCCESS: " + r);
        } else {
            System.out.println("Booking FAILED: No rooms available");
        }

        // Save updated state
        PersistenceService.save(new SystemState(inventory, history));

        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("Booking History: " + history);
    }
}