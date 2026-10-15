/*Austin Radloff
 * This class manages the inventory.
 * It uses an array to store items and contains methods
 * for adding, searching, updating, removing, saving,
 * loading, and displaying inventory information.
 */

import java.io.*;
import java.nio.file.*;

public class InventoryManager {

    private InventoryItem[] items;
    private int itemCount;

    private final int MAX_ITEMS = 20;

    // Relative path used for the inventory data file.
    private final String FILE_NAME = "inventory.txt";

    public InventoryManager() {
        items = new InventoryItem[MAX_ITEMS];
        itemCount = 0;
    }

    // Adds an item if there is room and the ID is not already used.
    public boolean addItem(InventoryItem item) {

        if (searchByID(item.getItemID()) != null) {
            return false;
        }

        if (itemCount < MAX_ITEMS) {
            items[itemCount] = item;
            itemCount++;
            return true;
        }

        return false;
    }

    // Searches the inventory array for an item ID.
    public InventoryItem searchByID(int id) {

        for (int x = 0; x < itemCount; x++) {

            if (items[x].getItemID() == id) {
                return items[x];
            }
        }

        return null;
    }

    // Updates the quantity for an existing inventory item.
    public boolean updateQuantity(int id, int newQuantity) {

        InventoryItem item = searchByID(id);

        if (item != null) {
            item.setQuantity(newQuantity);
            return true;
        }

        return false;
    }

    // Updates the price for an existing inventory item.
    public boolean updatePrice(int id, double newPrice) {

        InventoryItem item = searchByID(id);

        if (item != null) {
            item.setPrice(newPrice);
            return true;
        }

        return false;
    }

    // Removes an item and shifts remaining array elements.
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

    // Calculates the value of all inventory currently stored.
    public double getTotalInventoryValue() {

        double total = 0;

        for (int x = 0; x < itemCount; x++) {
            total = total + items[x].getInventoryValue();
        }

        return total;
    }

    public int getItemCount() {
        return itemCount;
    }

    // Returns an item at a specific array location.
    public InventoryItem getItem(int index) {

        if (index >= 0 && index < itemCount) {
            return items[index];
        }

        return null;
    }

    // Clears all inventory from memory.
    public void clearInventory() {

        for (int x = 0; x < itemCount; x++) {
            items[x] = null;
        }

        itemCount = 0;
    }

    // Creates formatted inventory information for the GUI.
    public String getInventoryText() {

        StringBuilder output = new StringBuilder();

        output.append("COMPUTER REPAIR SHOP INVENTORY\n");
        output.append("============================================\n\n");

        if (itemCount == 0) {
            output.append("There are currently no inventory items.\n");
            return output.toString();
        }

        for (int x = 0; x < itemCount; x++) {

            output.append(items[x].toString());
            output.append("\n");

            output.append("Inventory Value: $");
            output.append(
                    String.format("%.2f",
                            items[x].getInventoryValue()));

            output.append("\n");

            if (items[x].isLowStock()) {
                output.append("*** LOW STOCK - REORDER NEEDED ***\n");
            }

            output.append("\n");
        }

        output.append("--------------------------------------------\n");

        output.append("Number of Items: ");
        output.append(itemCount);
        output.append("\n");

        output.append("Total Inventory Value: $");
        output.append(
                String.format("%.2f",
                        getTotalInventoryValue()));

        output.append("\n");

        return output.toString();
    }

    // Saves all inventory items to inventory.txt.
    public void saveInventoryToFile() throws IOException {

        Path file = Paths.get(FILE_NAME);

        BufferedWriter writer = Files.newBufferedWriter(file);

        for (int x = 0; x < itemCount; x++) {

            InventoryItem item = items[x];

            // ComputerPart objects contain two extra fields.
            if (item instanceof ComputerPart) {

                ComputerPart part = (ComputerPart) item;

                writer.write(
                        "PART,"
                                + part.getItemID() + ","
                                + part.getPartName() + ","
                                + part.getCategory() + ","
                                + part.getQuantity() + ","
                                + part.getPrice() + ","
                                + part.getReorderLevel() + ","
                                + part.getBrand() + ","
                                + part.getWarrantyMonths());

            } else {

                writer.write(
                        "ITEM,"
                                + item.getItemID() + ","
                                + item.getPartName() + ","
                                + item.getCategory() + ","
                                + item.getQuantity() + ","
                                + item.getPrice() + ","
                                + item.getReorderLevel());
            }

            writer.newLine();
        }

        writer.close();
    }

    // Loads inventory information from inventory.txt.
    public void loadInventoryFromFile() throws IOException {

        Path file = Paths.get(FILE_NAME);

        if (!Files.exists(file)) {
            throw new IOException(
                    "inventory.txt could not be found.");
        }

        clearInventory();

        BufferedReader reader = Files.newBufferedReader(file);

        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {

            lineNumber++;

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");

            try {

                /*
                 * New ComputerPart format:
                 * PART,id,name,category,qty,price,reorder,brand,warranty
                 */
                if (data[0].equals("PART")
                        && data.length >= 9) {

                    int id = Integer.parseInt(data[1]);

                    String name = data[2];

                    String category = data[3];

                    int quantity = Integer.parseInt(data[4]);

                    double price = Double.parseDouble(data[5]);

                    int reorder = Integer.parseInt(data[6]);

                    String brand = data[7];

                    int warranty = Integer.parseInt(data[8]);

                    ComputerPart part = new ComputerPart(
                            id,
                            name,
                            category,
                            quantity,
                            price,
                            reorder,
                            brand,
                            warranty);

                    if (!addItem(part)) {
                        throw new IOException(
                                "Could not add item on line "
                                        + lineNumber + ".");
                    }

                    /*
                     * Standard InventoryItem format:
                     * ITEM,id,name,category,qty,price,reorder
                     */
                } else if (data[0].equals("ITEM")
                        && data.length >= 7) {

                    int id = Integer.parseInt(data[1]);

                    String name = data[2];

                    String category = data[3];

                    int quantity = Integer.parseInt(data[4]);

                    double price = Double.parseDouble(data[5]);

                    int reorder = Integer.parseInt(data[6]);

                    InventoryItem item = new InventoryItem(
                            id,
                            name,
                            category,
                            quantity,
                            price,
                            reorder);

                    if (!addItem(item)) {
                        throw new IOException(
                                "Could not add item on line "
                                        + lineNumber + ".");
                    }

                    /*
                     * This section supports the older six-field
                     * inventory.txt format used earlier in the project.
                     */
                } else if (data.length == 6) {

                    int id = Integer.parseInt(data[0]);

                    String name = data[1];

                    String category = data[2];

                    int quantity = Integer.parseInt(data[3]);

                    double price = Double.parseDouble(data[4]);

                    int reorder = Integer.parseInt(data[5]);

                    InventoryItem item = new InventoryItem(
                            id,
                            name,
                            category,
                            quantity,
                            price,
                            reorder);

                    addItem(item);

                } else {

                    throw new IOException(
                            "Invalid inventory data on line "
                                    + lineNumber + ".");
                }

            } catch (NumberFormatException e) {

                reader.close();

                throw new IOException(
                        "Invalid number on line "
                                + lineNumber + ".",
                        e);
            }
        }

        reader.close();
    }
}