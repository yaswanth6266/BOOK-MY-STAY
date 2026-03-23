package HOTELBOOKING;

import java.io.*;
import java.util.*;

/**
 * =========================================================
 * Use Case 12: Data Persistence & System Recovery
 * =========================================================
 */

public class UseCase12DataPersistenceRecovery {

    /* ==============================
       Room Inventory
       ============================== */
    static class RoomInventory {
        private Map<String, Integer> availability = new HashMap<>();

        public void setRoom(String type, int count) {
            availability.put(type, count);
        }

        public Map<String, Integer> getAll() {
            return availability;
        }

        public void display() {
            System.out.println("\nCurrent Inventory:");
            for (String key : availability.keySet()) {
                System.out.println(key + ": " + availability.get(key));
            }
        }
    }

    /* ==============================
       File Persistence Service
       ============================== */
    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                inventory.setRoom("Single", 5);
                inventory.setRoom("Double", 3);
                inventory.setRoom("Suite", 2);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
                }

                System.out.println("Inventory loaded successfully.");

            } catch (IOException e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    /* ==============================
       MAIN METHOD
       ============================== */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        String filePath = "inventory.txt";

        System.out.println("=== System Recovery ===");

        // Load previous data
        service.loadInventory(inventory, filePath);

        inventory.display();

        // Save current state
        service.saveInventory(inventory, filePath);
    }
}