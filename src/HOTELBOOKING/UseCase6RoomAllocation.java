
    package HOTELBOOKING;

import java.util.*;

    /* =======================
       Reservation Class
    ======================= */
    class Reservation {
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

    /* =======================
       Booking Request Queue
    ======================= */
    class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.add(r);
        }

        public Reservation getNextRequest() {
            return queue.poll();
        }

        public boolean hasPendingRequests() {
            return !queue.isEmpty();
        }
    }

    /* =======================
       Room Inventory
    ======================= */

    /* =======================
       Room Allocation Service
    ======================= */
    class RoomAllocationService {

        private Map<String, Set<String>> assignedRooms = new HashMap<>();

        public void allocateRoom(Reservation r, RoomInventory inventory) {

            String type = r.getRoomType();

            if (!inventory.hasRoom(type)) {
                System.out.println("No rooms available for " + r.getGuestName());
                return;
            }

            String roomId = generateRoomId(type);

            assignedRooms
                    .computeIfAbsent(type, k -> new HashSet<>())
                    .add(roomId);

            inventory.reduceRoom(type);

            System.out.println("Booking confirmed for Guest: "
                    + r.getGuestName()
                    + ", Room ID: " + roomId);
        }

        private String generateRoomId(String type) {
            int count = assignedRooms.getOrDefault(type, new HashSet<>()).size() + 1;
            return type + "-" + count;
        }
    }

    /* =======================
       MAIN CLASS (UC6)
    ======================= */
    public class UseCase6RoomAllocation {

        public static void main(String[] args) {

            BookingRequestQueue queue = new BookingRequestQueue();
            RoomInventory inventory = new RoomInventory();
            RoomAllocationService service = new RoomAllocationService();

            // Requests (same as PDF output)
            queue.addRequest(new Reservation("Abhi", "Single"));
            queue.addRequest(new Reservation("Subha", "Single"));
            queue.addRequest(new Reservation("Vanmathi", "Suite"));

            while (queue.hasPendingRequests()) {
                Reservation r = queue.getNextRequest();
                service.allocateRoom(r, inventory);
            }
        }
    }

