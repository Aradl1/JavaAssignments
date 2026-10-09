//Austin Radloff

public class InventoryControl {
        public static void main(String[] args) {

                InventoryManager manager = new InventoryManager();

                InventoryItem item1 = new InventoryItem(
                                101,
                                "RAM",
                                "Memory",
                                10,
                                39.99,
                                3);

                InventoryItem item2 = new InventoryItem(
                                102,
                                "SSD",
                                "Storage",
                                5,
                                69.99,
                                2);

                InventoryItem item3 = new InventoryItem(
                                103,
                                "Power Supply",
                                "Power",
                                2,
                                89.99,
                                2);

                manager.addItem(item1);
                manager.addItem(item2);
                manager.addItem(item3);

                System.out.println(
                                "CURRENT INVENTORY");
                System.out.println();

                manager.displayInventory();

                manager.saveInventoryToFile();

                System.out.println();
                System.out.println(
                                "LOADING INVENTORY FROM FILE");
                System.out.println();

                InventoryManager loadedManager = new InventoryManager();

                loadedManager.loadInventoryFromFile();

                loadedManager.displayInventory();
        }
}