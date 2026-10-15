/*Austin Radloff
 *
 * ComputerPart is a child class of InventoryItem.
 * It adds a brand and warranty to the normal inventory information.
 */

public class ComputerPart extends InventoryItem {

    private String brand;
    private int warrantyMonths;

    // Constructor for a computer part.
    public ComputerPart(int id, String name, String category,
            int quantity, double price, int reorderLevel,
            String brand, int warrantyMonths) {

        super(id, name, category, quantity, price, reorderLevel);

        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    // Overrides the InventoryItem toString() method.
    @Override
    public String toString() {
        return super.toString()
                + " | Brand: " + brand
                + " | Warranty: " + warrantyMonths + " months";
    }
}