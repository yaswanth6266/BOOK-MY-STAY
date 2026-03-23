package HOTELBOOKING;

import java.util.*;

/**
 * =========================================================
 * Use Case 11: Concurrent Booking Simulation
 * =========================================================
 */

public class UseCase11ConcurrentBookingSimulation {

    /* ==============================
       Reservation Class
       ============================== */
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    /* ==============================
       Booking Queue
       ============================== */
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public synchronized void addRequest(Reservation r) {
            queue.add(r);
        }

        public synchronized Reservation getNextRequest() {
            return queue.poll();
        }

        public synchronized boolean hasRequests() {
            return !queue.isEmpty();
        }
    }

    /* ==============================
       Room Inventory
       ============================== */
    static class RoomInventory {
        private Map<String, Integer> availability = new HashMap<>();

        public RoomInventory() {
            availability.put("Single", 3);
            availability.put("Double", 2);
            availability.put("Suite", 1);
        }

        public synchronized boolean hasRoom(String type) {
            return availability.getOrDefault(type, 0) > 0;
        }

        public synchronized void reduceRoom(String type) {
            availability.put(type, availability.get(type) - 1);
        }

        public void display() {
            System.out.println("\nRemaining Inventory:");
            for (String key : availability.keySet()) {
                System.out.println(key + ": " + availability.get(key));
            }
        }
    }

    /* ==============================
       Room Allocation Service
       ============================== */
    static class RoomAllocationService {

        private Map<String, Integer> counter = new HashMap<>();

        public void allocateRoom(Reservation r, RoomInventory inventory) {

            String type = r.getRoomType();

            if (!inventory.hasRoom(type)) {
                System.out.println("No room available for " + r.getGuestName());
                return;
            }

            int id = counter.getOrDefault(type, 0) + 1;
            counter.put(type, id);

            inventory.reduceRoom(type);

            System.out.println("Booking confirmed for Guest: "
                    + r.getGuestName()
                    + ", Room ID: " + type + "-" + id);
        }
    }

    /* ==============================
       Thread Class
       ============================== */
    static class ConcurrentBookingProcessor implements Runnable {

        private BookingRequestQueue queue;
        private RoomInventory inventory;
        private RoomAllocationService service;

        public ConcurrentBookingProcessor(BookingRequestQueue queue,
                                          RoomInventory inventory,
                                          RoomAllocationService service) {
            this.queue = queue;
            this.inventory = inventory;
            this.service = service;
        }

        @Override
        public void run() {
            while (true) {

                Reservation r;

                synchronized (queue) {
                    if (!queue.hasRequests()) break;
                    r = queue.getNextRequest();
                }

                if (r != null) {
                    synchronized (inventory) {
                        service.allocateRoom(r, inventory);
                    }
                }
            }
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        System.out.println("=== Concurrent Booking Simulation ===");

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        inventory.display();
    }
}