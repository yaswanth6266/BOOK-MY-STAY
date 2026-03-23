package HOTELBOOKING;

import java.util.HashMap;
import java.util.Map;

/* =======================
   Base Room Class
======================= */
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

/* =======================
   Single Room
======================= */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/* =======================
   Double Room
======================= */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/* =======================
   Suite Room
======================= */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/* =======================
   Room Inventory
======================= */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    // ✅ REQUIRED FOR UC6
    public boolean hasRoom(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    // ✅ REQUIRED FOR UC6
    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("Room Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + " Rooms Available: " + availability.get(type));
        }
    }
}

/* =======================
   MAIN CLASS (UC3)
======================= */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();
    }
}