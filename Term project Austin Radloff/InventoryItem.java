/*Austin Radloff
 * Stores the information for one inventory item.
 * Chapter 3: constructors, private instance fields, getters, setters and methods.
 * Chapter 4: constructor overloading, this, a static field and constants.
 */
public class InventoryItem {
    public static final int DEFAULT_REORDER_LEVEL = 5;
    private static int itemsCreated = 0;

    private String itemID;
    private String partName;
    private String category;
    private int quantity;
    private double price;
    private int reorderLevel;

    // Constructor with all the information for an item.
    public InventoryItem(String itemID, String partName, String category,
                         int quantity, double price, int reorderLevel) {
        this.itemID = requireText(itemID, "Item ID");
        this.partName = requireText(partName, "Part name");
        this.category = requireText(category, "Category");
        setQuantity(quantity);
        setPrice(price);
        setReorderLevel(reorderLevel);
        itemsCreated++;
    }

    // Overloaded constructor: supplies a default reorder level.
    public InventoryItem(String itemID, String partName, String category,
                         int quantity, double price) {
        this(itemID, partName, category, quantity, price, DEFAULT_REORDER_LEVEL);
    }

    private String requireText(String value, String label) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException(label + " cannot be blank.");
        }
        if (value.contains(",") || value.contains("\n") || value.contains("\r")) {
            throw new IllegalArgumentException(label + " cannot contain commas or new lines.");
        }
        return value.trim();
    }

    public String getItemID() { return itemID; }
    public String getPartName() { return partName; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getReorderLevel() { return reorderLevel; }
    public static int getItemsCreated() { return itemsCreated; }

    public void setPartName(String partName) {
        this.partName = requireText(partName, "Part name");
    }

    public void setCategory(String category) {
        this.category = requireText(category, "Category");
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Price must be a valid, nonnegative number.");
        }
        this.price = price;
    }

    public void setReorderLevel(int reorderLevel) {
        if (reorderLevel < 0) {
            throw new IllegalArgumentException("Reorder level cannot be negative.");
        }
        this.reorderLevel = reorderLevel;
    }

    public double getInventoryValue() {
        return quantity * price;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    @Override
    public String toString() {
        return partName + " (ID: " + itemID + ", Qty: " + quantity +
               ", Price: $" + String.format("%.2f", price) + ")";
    }
}