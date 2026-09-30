//Austin Radloff

public class InventoryItem {
    private int itemID;
    private String partName;
    private String category;
    private int quantity;
    private double price;
    private int reorderLevel;

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

    public double getInventoryValue() {
        return quantity * price;
    }

    public boolean isLowStock() {
        if (quantity <= reorderLevel)
            return true;
        else
            return false;
    }
}