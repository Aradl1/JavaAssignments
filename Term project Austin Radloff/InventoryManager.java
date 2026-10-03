//Austin Radloff

public class InventoryManager {
    private InventoryItem[] items;
    private int itemCount;
    private final int MAX_ITEMS = 20;

    public InventoryManager() {
        items = new InventoryItem[MAX_ITEMS];
        itemCount = 0;
    }

    public boolean addItem(InventoryItem item) {
        if (itemCount < MAX_ITEMS) {
            items[itemCount] = item;
            itemCount++;
            return true;
        } else {
            return false;
        }
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
            total = total + items[x].getInventoryValue();
        }

        return total;
    }

    public void displayInventory() {
        System.out.println("COMPUTER REPAIR SHOP INVENTORY");
        System.out.println("--------------------------------");

        for (int x = 0; x < itemCount; x++) {
            System.out.println("Item ID: " +
                    items[x].getItemID());

            System.out.println("Part Name: " +
                    items[x].getPartName());

            System.out.println("Category: " +
                    items[x].getCategory());

            System.out.println("Quantity: " +
                    items[x].getQuantity());

            System.out.println("Price: $" +
                    items[x].getPrice());

            System.out.println("Inventory Value: $" +
                    items[x].getInventoryValue());

            if (items[x].isLowStock()) {
                System.out.println(
                        "LOW STOCK - REORDER NEEDED");
            }

            System.out.println();
        }

        System.out.println("Total Inventory Value: $" +
                getTotalInventoryValue());
    }
}