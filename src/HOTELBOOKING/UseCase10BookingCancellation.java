package HOTELBOOKING;

import java.util.*;

/**
 * =========================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * =========================================================
 */

public class UseCase10BookingCancellation {

    /* ==============================
       Room Inventory
       ============================== */
    static class RoomInventory {
        private Map<String, Integer> availability = new HashMap<>();

        public RoomInventory() {
            availability.put("Single", 5);
            availability.put("Double", 3);
            availability.put("Suite", 2);
        }

        public void increaseRoom(String type) {
            availability.put(type, availability.getOrDefault(type, 0) + 1);
        }

        public void display() {
            System.out.println("Current Inventory:");
            for (String key : availability.keySet()) {
                System.out.println(key + ": " + availability.get(key));
            }
        }
    }

    /* ==============================
       Cancellation Service
       ============================== */
    static class CancellationService {

        // Stack for rollback (LIFO)
        private Stack<String> rollbackStack = new Stack<>();

        // Map to track booking → room type
        private Map<String, String> reservationMap = new HashMap<>();

        public void registerBooking(String reservationId, String roomType) {
            reservationMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationMap.containsKey(reservationId)) {
                System.out.println("Invalid reservation ID!");
                return;
            }

            String roomType = reservationMap.get(reservationId);

            // push to rollback stack
            rollbackStack.push(reservationId);

            // restore inventory
            inventory.increaseRoom(roomType);

            // remove booking
            reservationMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        public void showRollbackHistory() {
            System.out.println("\nRollback History (Most Recent First):");
            while (!rollbackStack.isEmpty()) {
                System.out.println("Released Reservation ID: " + rollbackStack.pop());
            }
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Simulate bookings
        service.registerBooking("Single-1", "Single");
        service.registerBooking("Double-1", "Double");

        System.out.println("=== Booking Cancellation ===");

        // Cancel booking
        service.cancelBooking("Single-1", inventory);

        // Show rollback
        service.showRollbackHistory();

        // Show updated inventory
        inventory.display();
    }
}