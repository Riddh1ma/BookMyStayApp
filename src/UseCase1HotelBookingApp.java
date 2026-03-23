import java.util.*;

/**
 * Book My Stay Application
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates:
 * - Map + List (One-to-Many relationship)
 * - Optional services without affecting core booking
 *
 * @author Riddhima
 * @version 7.0
 */

// Service class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// Manager class
class AddOnServiceManager {

    // reservationId -> list of services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service to " + reservationId + ": " + service.getServiceName());
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main class
class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Add-On Services =====");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation IDs (from UC6)
        String res1 = "S101";
        String res2 = "D202";

        // Adding services
        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("WiFi", 200));
        manager.addService(res1, new AddOnService("Airport Pickup", 1000));

        manager.addService(res2, new AddOnService("Breakfast", 500));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));

        System.out.println("\nCore booking and inventory remain unchanged ✅");
    }
}