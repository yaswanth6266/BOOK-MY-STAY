package HOTELBOOKING;

import java.util.LinkedList;
import java.util.Queue;



/* Main class */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subbu", "Double");
        Reservation r3 = new Reservation("Vamshi", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        System.out.println("Booking Request Queue");

        // Process requests FIFO
        while (bookingQueue.hasPendingRequests()) {

            Reservation r = bookingQueue.getNextRequest();

            System.out.println(
                    "Processing booking for Guest: "
                            + r.getGuestName()
                            + ", Room Type: "
                            + r.getRoomType()
            );
        }
    }
}