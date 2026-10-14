//Austin Radloff

import java.io.*;
import java.nio.file.*;

public class InventoryManager {

    private InventoryItem[] items;
    private int itemCount;
    private final int MAX_ITEMS = 20;

    // Relative file path
    private final Path inventoryFile = Paths.get("inventory.txt");

    public InventoryManager() {
        items = new InventoryItem[MAX_ITEMS];
        itemCount = 0;
    }

    public boolean addItem(InventoryItem item) {

        if (itemCount < MAX_ITEMS) {
            items[itemCount] = item;
            itemCount++;
            return true;
        }

        return false;
    }

    public InventoryItem searchByID(int id) {

        for (int x = 0; x < itemCount; x++) {

            if (items[x].getItemID() == id) {
                return items[x];
            }
        }

        return null;
    }

    public boolean updateQuantity(int id, int newQuantity) {

        InventoryItem item = searchByID(id);

        if (item != null) {
            item.setQuantity(newQuantity);
            return true;
        }

        return false;
    }

    public boolean updatePrice(int id, double newPrice) {

        InventoryItem item = searchByID(id);

        if (item != null) {
            item.setPrice(newPrice);
            return true;
        }

        return false;
    }

    public boolean removeItem(int id) {

        for (int x = 0; x < itemCount; x++) {

            if (items[x].getItemID() == id) {

                for (int y = x; y < itemCount - 1; y++) {
                    items[y] = items[y + 1];
                }

                items[itemCount - 1] = null;
                itemCount--;

                return true;
            }
        }

        return false;
    }

    public double getTotalInventoryValue() {

        double total = 0;

        for (int x = 0; x < itemCount; x++) {
            total += items[x].getInventoryValue();
        }

        return total;
    }

    public int getItemCount() {
        return itemCount;
    }

    public InventoryItem getItem(int index) {
        return items[index];
    }

    public void displayInventory() {

        for (int x = 0; x < itemCount; x++) {

            System.out.println(
                    "Item ID: " + items[x].getItemID());

            System.out.println(
                    "Part Name: " + items[x].getPartName());

            System.out.println(
                    "Category: " + items[x].getCategory());

            System.out.println(
                    "Quantity: " + items[x].getQuantity());

            System.out.println(
                    "Price: $" + items[x].getPrice());

            System.out.println(
                    "Reorder Level: " +
                            items[x].getReorderLevel());

            System.out.println(
                    "Inventory Value: $" +
                            items[x].getInventoryValue());

            if (items[x].isLowStock()) {
                System.out.println("LOW STOCK");
            }

            System.out.println();
        }

        System.out.println(
                "Total Inventory Value: $" +
                        getTotalInventoryValue());
    }

    public boolean saveInventoryToFile() {

        try {

            BufferedWriter writer = Files.newBufferedWriter(inventoryFile);

            for (int x = 0; x < itemCount; x++) {

                InventoryItem item = items[x];

                writer.write(
                        item.getItemID() + "," +
                                item.getPartName() + "," +
                                item.getCategory() + "," +
                                item.getQuantity() + "," +
                                item.getPrice() + "," +
                                item.getReorderLevel());

                writer.newLine();
            }

            writer.close();

            System.out.println(
                    "Inventory saved successfully.");

            System.out.println(
                    "Inventory file location: " +
                            inventoryFile.toAbsolutePath());

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error saving inventory file.");

            return false;
        }
    }

    public boolean loadInventoryFromFile() {

        // Shows exactly where Java is looking
        System.out.println(
                "Looking for inventory file at: " +
                        inventoryFile.toAbsolutePath());

        if (!Files.exists(inventoryFile)) {

            System.out.println(
                    "inventory.txt could not be found.");

            return false;
        }

        try {

            BufferedReader reader = Files.newBufferedReader(inventoryFile);

            itemCount = 0;

            String line;

            while ((line = reader.readLine()) != null) {

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != 6) {
                    System.out.println(
                            "Invalid inventory line: " + line);

                    continue;
                }

                int id = Integer.parseInt(
                        data[0].trim());

                String partName = data[1].trim();

                String category = data[2].trim();

                int quantity = Integer.parseInt(
                        data[3].trim());

                double price = Double.parseDouble(
                        data[4].trim());

                int reorderLevel = Integer.parseInt(
                        data[5].trim());

                InventoryItem item = new InventoryItem(
                        id,
                        partName,
                        category,
                        quantity,
                        price,
                        reorderLevel);

                addItem(item);
            }

            reader.close();

            System.out.println(
                    "Inventory loaded successfully.");

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error reading inventory file.");

            return false;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid number found in inventory file.");

            return false;
        }
    }
}