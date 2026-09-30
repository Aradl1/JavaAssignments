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
    }
}