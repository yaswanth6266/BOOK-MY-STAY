package HOTELBOOKING;

import java.util.Scanner;

/**
 * =========================================================
 * Use Case 9: Error Handling & Validation (Single File)
 * =========================================================
 */

public class UseCase9ErrorHandlingValidation {

    /* ==============================
       Custom Exception
       ============================== */
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    /* ==============================
       Room Inventory (for validation)
       ============================== */
    static class RoomInventory {

        public boolean hasRoom(String roomType) {
            return roomType.equalsIgnoreCase("Single") ||
                    roomType.equalsIgnoreCase("Double") ||
                    roomType.equalsIgnoreCase("Suite");
        }
    }

    /* ==============================
       Validator Class
       ============================== */
    static class ReservationValidator {

        public void validate(String guestName,
                             String roomType,
                             RoomInventory inventory)
                throws InvalidBookingException {

            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            if (!inventory.hasRoom(roomType)) {
                throw new InvalidBookingException("Invalid or unavailable room type");
            }
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        System.out.println("=== Booking Validation System ===");

        try {
            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Validate input
            validator.validate(name, type, inventory);

            System.out.println("Booking validated successfully!");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}