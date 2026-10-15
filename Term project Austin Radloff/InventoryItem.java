/*Austin Radloff
 *
 * This class stores the basic information for one inventory item.
 * It contains fields, a constructor, getters, setters, calculations,
 * and a method for checking whether an item is low in stock.
 */

public class InventoryItem {

    private int itemID;
    private String partName;
    private String category;
    private int quantity;
    private double price;
    private int reorderLevel;

    // Constructor used to create an inventory item.
    public InventoryItem(int id, String name, String cat,
            int qty, double itemPrice, int reorder) {

        itemID = id;
        partName = name;
        category = cat;
        quantity = qty;
        price = itemPrice;
        reorderLevel = reorder;
    }

    public int getItemID() {
        return itemID;
    }

    public String getPartName() {
        return partName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setQuantity(int qty) {
        quantity = qty;
    }

    public void setPrice(double itemPrice) {
        price = itemPrice;
    }

    // Calculates the total value of this inventory item.
    public double getInventoryValue() {
        return quantity * price;
    }

    // Returns true when the quantity reaches the reorder level.
    public boolean isLowStock() {
        if (quantity <= reorderLevel) {
            return true;
        } else {
            return false;
        }
    }

    // Overrides the Object toString() method.
    @Override
    public String toString() {
        return "ID: " + itemID
                + " | Part: " + partName
                + " | Category: " + category
                + " | Qty: " + quantity
                + " | Price: $" + String.format("%.2f", price)
                + " | Reorder Level: " + reorderLevel;
    }
}