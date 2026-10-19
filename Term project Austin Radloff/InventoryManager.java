/*Austin Radloff
 * This class manages the inventory.
 * It uses an array to store items and contains methods
 * for adding, searching, updating, removing, saving,
 * loading, and displaying inventory information.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Stores inventory items in an array and handles all inventory operations.
 * Chapter 8: array and two-dimensional array with nested loops.
 * Chapter 11: save/load text files using a relative path.
 * Chapter 13: generic method joinValues(T[], String).
 */
public class InventoryManager {
    public static final int MAX_ITEMS = 20;
    private final InventoryItem[] items = new InventoryItem[MAX_ITEMS];
    private int itemCount = 0;
    private final Path inventoryFile = Paths.get("inventory.txt");

    // Chapter 4: a nested class for the calculated inventory summary.
    public static class InventorySummary {
        private final int totalItems;
        private final int lowStockItems;
        private final double totalValue;

        public InventorySummary(int totalItems, int lowStockItems, double totalValue) {
            this.totalItems = totalItems;
            this.lowStockItems = lowStockItems;
            this.totalValue = totalValue;
        }

        public int getTotalItems() { return totalItems; }
        public int getLowStockItems() { return lowStockItems; }
        public double getTotalValue() { return totalValue; }
    }

    public int getItemCount() { return itemCount; }

    public InventoryItem getItem(int index) {
        if (index < 0 || index >= itemCount) {
            throw new IndexOutOfBoundsException("Invalid inventory index.");
        }
        return items[index];
    }

    public boolean addItem(InventoryItem item) {
        if (item == null || itemCount >= MAX_ITEMS ||
            searchByID(item.getItemID()) != null) {
            return false;
        }
        items[itemCount] = item;
        itemCount++;
        return true;
    }

    public InventoryItem searchByID(String itemID) {
        if (itemID == null) return null;
        for (int i = 0; i < itemCount; i++) {
            // Chapter 7: compare strings using equals(), not ==.
            if (items[i].getItemID().equals(itemID.trim())) {
                return items[i];
            }
        }
        return null;
    }

    public InventoryItem searchByName(String name) {
        if (name == null) return null;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getPartName().equalsIgnoreCase(name.trim())) {
                return items[i];
            }
        }
        return null;
    }

    public boolean updateQuantity(String itemID, int quantity) {
        InventoryItem item = searchByID(itemID);
        if (item == null) return false;
        item.setQuantity(quantity);
        return true;
    }

    public boolean updatePrice(String itemID, double price) {
        InventoryItem item = searchByID(itemID);
        if (item == null) return false;
        item.setPrice(price);
        return true;
    }

    public boolean removeItem(String itemID) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getItemID().equals(itemID.trim())) {
                for (int j = i; j < itemCount - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[--itemCount] = null;
                return true;
            }
        }
        return false;
    }

    public double getTotalInventoryValue() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getInventoryValue();
        }
        return Math.round(total * 100.0) / 100.0; // Chapter 4: Math class.
    }

    public InventorySummary getSummary() {
        int low = 0;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].isLowStock()) low++;
        }
        return new InventorySummary(itemCount, low, getTotalInventoryValue());
    }

    // Chapter 13: generic method actually used when saving file records.
    public static <T> String joinValues(T[] values, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) result.append(separator);
            result.append(values[i]);
        }
        return result.toString();
    }

    // Chapter 8: builds a real two-dimensional array for the report.
    public String[][] createInventoryTable(boolean lowStockOnly) {
        int matching = 0;
        for (int i = 0; i < itemCount; i++) {
            if (!lowStockOnly || items[i].isLowStock()) matching++;
        }

        String[][] table = new String[matching][7];
        int row = 0;
        for (int i = 0; i < itemCount; i++) {
            InventoryItem item = items[i];
            if (!lowStockOnly || item.isLowStock()) {
                String[] columns = {
                    item.getItemID(), item.getPartName(), item.getCategory(),
                    String.valueOf(item.getQuantity()),
                    String.format("$%.2f", item.getPrice()),
                    String.valueOf(item.getReorderLevel()),
                    item.isLowStock() ? "LOW" : "OK"
                };
                for (int col = 0; col < columns.length; col++) {
                    table[row][col] = columns[col];
                }
                row++;
            }
        }
        return table;
    }

    public String getInventoryText() {
        return getInventoryText(false);
    }

    public String getInventoryText(boolean lowStockOnly) {
        String[][] table = createInventoryTable(lowStockOnly);
        if (table.length == 0) {
            return lowStockOnly ? "There are no low-stock items." : "No inventory items found.";
        }
        int[] widths = {12, 23, 17, 7, 11, 10, 8};
        String[] headers = {"ID", "Part Name", "Category", "Qty", "Price", "Reorder", "Status"};
        StringBuilder report = new StringBuilder();
        for (int c = 0; c < headers.length; c++) {
            report.append(pad(headers[c], widths[c]));
        }
        report.append("\n");
        report.append("-".repeat(88)).append("\n");

        // Nested looping: visit every row and column of the 2D array.
        for (int r = 0; r < table.length; r++) {
            for (int c = 0; c < table[r].length; c++) {
                report.append(pad(table[r][c], widths[c]));
            }
            report.append("\n");
        }
        if (!lowStockOnly) {
            InventorySummary summary = getSummary();
            report.append("\nItems: ").append(summary.getTotalItems());
            report.append(" | Low stock: ").append(summary.getLowStockItems());
            report.append(" | Total value: $").append(String.format("%.2f", summary.getTotalValue()));
        }
        return report.toString();
    }

    private String pad(String value, int width) {
        if (value.length() > width - 1) {
            value = value.substring(0, width - 2) + "~";
        }
        return String.format("%-" + width + "s", value);
    }

    public String getLowStockText() {
        return getInventoryText(true);
    }

    // Save as simple CSV. PART rows include brand and warranty.
    // Basic rows without PART stay compatible with older inventory.txt files.
    public boolean saveInventoryToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(inventoryFile, StandardCharsets.UTF_8)) {
            for (int i = 0; i < itemCount; i++) {
                InventoryItem item = items[i];
                String[] record;
                if (item instanceof ComputerPart) {
                    ComputerPart part = (ComputerPart) item;
                    record = new String[] {
                        "PART", item.getItemID(), item.getPartName(), item.getCategory(),
                        String.valueOf(item.getQuantity()), String.valueOf(item.getPrice()),
                        String.valueOf(item.getReorderLevel()), part.getBrand(),
                        String.valueOf(part.getWarrantyMonths())
                    };
                } else {
                    record = new String[] {
                        item.getItemID(), item.getPartName(), item.getCategory(),
                        String.valueOf(item.getQuantity()), String.valueOf(item.getPrice()),
                        String.valueOf(item.getReorderLevel())
                    };
                }
                writer.write(joinValues(record, ",")); // calls the generic method.
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
            return false;
        }
    }

    public boolean loadInventoryFromFile() {
        if (!Files.exists(inventoryFile)) {
            System.err.println("Could not find inventory.txt in the current project folder.");
            return false;
        }

        // Read to a temporary array first, so an invalid file cannot erase current data.
        InventoryItem[] loadedItems = new InventoryItem[MAX_ITEMS];
        int loadedCount = 0;
        try (BufferedReader reader = Files.newBufferedReader(inventoryFile, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().length() == 0 || line.startsWith("#")) continue;
                if (loadedCount >= MAX_ITEMS) {
                    throw new IOException("The file has more than " + MAX_ITEMS + " inventory items.");
                }
                InventoryItem item;
                try {
                    item = parseInventoryLine(line);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Invalid data on line " + lineNumber + ": " + e.getMessage());
                }
                for (int i = 0; i < loadedCount; i++) {
                    if (loadedItems[i].getItemID().equals(item.getItemID())) {
                        throw new IOException("Duplicate ID on line " + lineNumber);
                    }
                }
                loadedItems[loadedCount++] = item;
            }
            for (int i = 0; i < MAX_ITEMS; i++) {
                items[i] = i < loadedCount ? loadedItems[i] : null;
            }
            itemCount = loadedCount;
            return true;
        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
            return false;
        }
    }

    private InventoryItem parseInventoryLine(String line) {
        String[] fields = line.split(",", -1);
        int start = 0;
        boolean computerPart = false;

        if (fields.length == 9 && fields[0].equals("PART")) {
            computerPart = true;
            start = 1;
        } else if (fields.length == 8) {
            // Accept older extended records without the PART prefix.
            computerPart = true;
        } else if (fields.length != 6) {
            throw new IllegalArgumentException("Wrong number of fields.");
        }

        String id = fields[start];
        String name = fields[start + 1];
        String category = fields[start + 2];
        int quantity = Integer.parseInt(fields[start + 3].trim());
        double price = Double.parseDouble(fields[start + 4].trim());
        int reorder = Integer.parseInt(fields[start + 5].trim());

        if (computerPart) {
            String brand = fields[start + 6];
            int warranty = Integer.parseInt(fields[start + 7].trim());
            return new ComputerPart(id, name, category, quantity, price, reorder, brand, warranty);
        }
        return new InventoryItem(id, name, category, quantity, price, reorder);
    }
}