package HOTELBOOKING;

import java.util.*;

/**
 * =========================================================
 * Use Case 7: Add-On Service Selection (Single File Version)
 * =========================================================
 */

public class UseCase7AddOnService {

    /* ==============================
       CLASS: AddOnService
       ============================== */
    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    /* ==============================
       CLASS: AddOnServiceManager
       ============================== */
    static class AddOnServiceManager {

        private Map<String, List<AddOnService>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, AddOnService service) {
            servicesByReservation
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            List<AddOnService> services =
                    servicesByReservation.getOrDefault(reservationId, new ArrayList<>());

            double total = 0;
            for (AddOnService s : services) {
                total += s.getCost();
            }
            return total;
        }

        public void displayServices(String reservationId) {
            List<AddOnService> services =
                    servicesByReservation.getOrDefault(reservationId, new ArrayList<>());

            System.out.println("\nSelected Services:");
            for (AddOnService s : services) {
                System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
            }
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("=== Add-On Service Selection ===");

        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();

        // Sample services
        AddOnService breakfast = new AddOnService("Breakfast", 200);
        AddOnService spa = new AddOnService("Spa", 1000);
        AddOnService pickup = new AddOnService("Airport Pickup", 500);

        // Menu
        System.out.println("\nChoose services:");
        System.out.println("1. Breakfast (₹200)");
        System.out.println("2. Spa (₹1000)");
        System.out.println("3. Airport Pickup (₹500)");
        System.out.println("4. Done");

        while (true) {
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, breakfast);
                    break;
                case 2:
                    manager.addService(reservationId, spa);
                    break;
                case 3:
                    manager.addService(reservationId, pickup);
                    break;
                case 4:
                    System.out.println("Selection completed.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            if (choice == 4) break;
        }

        // Display results
        manager.displayServices(reservationId);
        double total = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + total);

        scanner.close();
    }
}