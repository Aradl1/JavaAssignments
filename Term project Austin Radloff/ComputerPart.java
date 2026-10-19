/*Austin Radloff
 * A computer part is a more specific type of inventory item.
 * Chapter 9: inheritance, super constructors and method overriding.
 */
public class ComputerPart extends InventoryItem {
    private String brand;
    private int warrantyMonths;

    public ComputerPart(String itemID, String partName, String category,
                        int quantity, double price, int reorderLevel,
                        String brand, int warrantyMonths) {
        super(itemID, partName, category, quantity, price, reorderLevel);
        setBrand(brand);
        setWarrantyMonths(warrantyMonths);
    }

    // Another overloaded constructor.
    public ComputerPart(String itemID, String partName, String category,
                        int quantity, double price, String brand) {
        this(itemID, partName, category, quantity, price,
             DEFAULT_REORDER_LEVEL, brand, 0);
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().length() == 0) {
            throw new IllegalArgumentException("Brand cannot be blank.");
        }
        if (brand.contains(",") || brand.contains("\n") || brand.contains("\r")) {
            throw new IllegalArgumentException("Brand cannot contain commas or new lines.");
        }
        this.brand = brand.trim();
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty cannot be negative.");
        }
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String toString() {
        return super.toString() + " | Brand: " + brand +
               " | Warranty: " + warrantyMonths + " months";
    }
}