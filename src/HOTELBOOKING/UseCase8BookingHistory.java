package HOTELBOOKING;

import java.util.*;

/**
 * =========================================================
 * Use Case 8: Booking History & Reporting (Single File)
 * =========================================================
 */

public class UseCase8BookingHistory {

    /* ==============================
       CLASS: Reservation
       ============================== */
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    /* ==============================
       CLASS: BookingHistory
       ============================== */
    static class BookingHistory {

        private List<Reservation> confirmedReservations;

        public BookingHistory() {
            confirmedReservations = new ArrayList<>();
        }

        public void addReservation(Reservation reservation) {
            confirmedReservations.add(reservation);
        }

        public List<Reservation> getReservations() {
            return confirmedReservations;
        }
    }

    /* ==============================
       CLASS: BookingReportService
       ============================== */
    static class BookingReportService {

        public void generateReport(BookingHistory history) {

            System.out.println("\n===== Booking Report =====");

            for (Reservation r : history.getReservations()) {
                System.out.println("Guest: " + r.getGuestName() +
                        ", Room Type: " + r.getRoomType());
            }

            System.out.println("==========================");
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Sample confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vamshi", "Suite"));

        // Generate report
        reportService.generateReport(history);
    }
}