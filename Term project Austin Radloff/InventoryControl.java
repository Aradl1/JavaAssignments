/*Austin Radloff
 *
 * This is the main GUI program for the inventory system.
 * The user can add, search, update, remove, display,
 * save, and load computer repair inventory.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Main program: Swing inventory application.
 * Chapter 2: print/println and GUI input.
 * Chapter 5: nested if statements.
 * Chapter 7: String.equals, String.length and number conversion.
 * Chapter 14: JFrame, JLabel, JTextField, JButton, JCheckBox,
 *             JRadioButton, and action listeners.
 */
public class InventoryControl extends JFrame {
    private final InventoryManager manager = new InventoryManager();

    private final JTextField idField = new JTextField(13);
    private final JTextField nameField = new JTextField(13);
    private final JTextField categoryField = new JTextField(13);
    private final JTextField quantityField = new JTextField(13);
    private final JTextField priceField = new JTextField(13);
    private final JTextField reorderField = new JTextField(13);
    private final JTextField brandField = new JTextField(13);
    private final JTextField warrantyField = new JTextField(13);

    private final JTextArea outputArea = new JTextArea(18, 78);
    private final JCheckBox lowStockOnly = new JCheckBox("Show low-stock items only");
    private final JRadioButton searchID = new JRadioButton("Search by ID", true);
    private final JRadioButton searchName = new JRadioButton("Search by name");

    public InventoryControl() {
        super("Computer Repair Shop - Inventory Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel fields = new JPanel(new GridLayout(4, 4, 7, 7));
        fields.setBorder(BorderFactory.createTitledBorder("Inventory Information"));
        fields.add(new JLabel("Item ID:")); fields.add(idField);
        fields.add(new JLabel("Part Name:")); fields.add(nameField);
        fields.add(new JLabel("Category:")); fields.add(categoryField);
        fields.add(new JLabel("Quantity:")); fields.add(quantityField);
        fields.add(new JLabel("Price:")); fields.add(priceField);
        fields.add(new JLabel("Reorder Level:")); fields.add(reorderField);
        fields.add(new JLabel("Brand (optional):")); fields.add(brandField);
        fields.add(new JLabel("Warranty Months (optional):")); fields.add(warrantyField);

        JPanel searchControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(searchID);
        searchGroup.add(searchName);
        searchControls.add(searchID);
        searchControls.add(searchName);
        searchControls.add(lowStockOnly);

        JPanel top = new JPanel(new BorderLayout());
        top.add(fields, BorderLayout.CENTER);
        top.add(searchControls, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(2, 5, 5, 5));
        addButton(buttons, "Add Item", () -> addItem());
        addButton(buttons, "Search", () -> searchItem());
        addButton(buttons, "Update Quantity", () -> updateQuantity());
        addButton(buttons, "Update Price", () -> updatePrice());
        addButton(buttons, "Remove Item", () -> removeItem());
        addButton(buttons, "Display Inventory", () -> displayInventory());
        addButton(buttons, "Low Stock", () -> showLowStock());
        addButton(buttons, "Save Inventory", () -> saveInventory());
        addButton(buttons, "Load Inventory", () -> loadInventory());
        addButton(buttons, "Clear Fields", () -> clearFields());

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(buttons, BorderLayout.NORTH);
        center.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        center.setBorder(BorderFactory.createEmptyBorder(5, 9, 9, 9));
        add(center, BorderLayout.CENTER);

        lowStockOnly.addActionListener(e -> displayInventory());
        outputArea.setText("Welcome to Inventory Control! Add an item or load inventory.txt.\n" +
                           "Use the radio buttons to choose how you want to search.\n" +
                           "Current working folder: " + System.getProperty("user.dir"));

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 610));
    }

    private void addButton(JPanel panel, String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run()); // Chapter 14: event listener.
        panel.add(button);
    }

    private void addItem() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            int reorder = Integer.parseInt(reorderField.getText().trim());
            InventoryItem item;

            // A blank brand means a regular inventory item; a brand creates a ComputerPart.
            String brand = brandField.getText().trim();
            String warranty = warrantyField.getText().trim();
            if (brand.length() > 0) {
                int warrantyMonths = warranty.length() == 0 ? 0 : Integer.parseInt(warranty);
                item = new ComputerPart(id, name, category, quantity, price,
                                        reorder, brand, warrantyMonths);
            } else {
                if (warranty.length() > 0) {
                    // Chapter 5: nested if inside the else branch.
                    throw new IllegalArgumentException("Enter a brand if you enter a warranty.");
                }
                item = new InventoryItem(id, name, category, quantity, price, reorder);
            }

            if (manager.addItem(item)) {
                outputArea.setText("Added: " + item + "\n\n" + manager.getInventoryText());
                System.out.println("Added item " + id + ".");
            } else {
                showError("The item ID already exists, or the inventory has reached " +
                          InventoryManager.MAX_ITEMS + " items.");
            }
        } catch (NumberFormatException e) {
            showError("Quantity, price, reorder level and warranty must be numbers.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void searchItem() {
        String idOrName;
        InventoryItem found;
        if (searchID.isSelected()) {
            idOrName = idField.getText().trim();
            if (idOrName.length() == 0) { // Chapter 7: String.length()
                showError("Please enter an item ID first.");
                return;
            }
            found = manager.searchByID(idOrName);
        } else {
            idOrName = nameField.getText().trim();
            if (idOrName.length() == 0) {
                showError("Please enter a part name first.");
                return;
            }
            found = manager.searchByName(idOrName);
        }

        // Chapter 5: nested if statements used to check a result and its stock level.
        if (found != null) {
            String result = found.toString();
            if (found.isLowStock()) {
                result += "\nWARNING: This item is low on stock.";
            }
            outputArea.setText(result);
            fillFields(found);
        } else {
            outputArea.setText("No item was found for: " + idOrName);
        }
    }

    private String requiredID() {
        String id = idField.getText().trim();
        if (id.length() == 0) {
            throw new IllegalArgumentException("Please enter the item ID first.");
        }
        return id;
    }

    private void updateQuantity() {
        try {
            String id = requiredID();
            int value = Integer.parseInt(quantityField.getText().trim());
            if (manager.updateQuantity(id, value)) {
                outputArea.setText("Updated quantity for " + id + ".\n\n" + manager.getInventoryText());
            } else {
                showError("Item ID not found.");
            }
        } catch (NumberFormatException e) {
            showError("Enter a whole number for quantity.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void updatePrice() {
        try {
            String id = requiredID();
            double value = Double.parseDouble(priceField.getText().trim());
            if (manager.updatePrice(id, value)) {
                outputArea.setText("Updated price for " + id + ".\n\n" + manager.getInventoryText());
            } else {
                showError("Item ID not found.");
            }
        } catch (NumberFormatException e) {
            showError("Enter a number for price.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void removeItem() {
        try {
            String id = requiredID();
            if (manager.searchByID(id) == null) {
                showError("Item ID not found.");
                return;
            }
            int answer = JOptionPane.showConfirmDialog(this,
                "Remove item " + id + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                manager.removeItem(id);
                outputArea.setText("Removed " + id + ".\n\n" + manager.getInventoryText());
            }
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void displayInventory() {
        outputArea.setText(manager.getInventoryText(lowStockOnly.isSelected()));
    }

    private void showLowStock() {
        lowStockOnly.setSelected(true);
        displayInventory();
    }

    private void saveInventory() {
        if (manager.saveInventoryToFile()) {
            outputArea.setText("Saved " + manager.getItemCount() +
                " items to inventory.txt.\n\n" + manager.getInventoryText());
            System.out.println("Inventory saved to inventory.txt.");
        } else {
            showError("Unable to save inventory.txt. Check the folder permissions.");
        }
    }

    private void loadInventory() {
        if (manager.loadInventoryFromFile()) {
            outputArea.setText("Loaded " + manager.getItemCount() +
                " items from inventory.txt.\n\n" + manager.getInventoryText());
            System.out.println("Inventory loaded from inventory.txt.");
        } else {
            showError("Could not load inventory.txt. Check that it exists and contains valid data " +
                      "in your project folder. See the terminal for details.");
        }
    }

    private void fillFields(InventoryItem item) {
        idField.setText(item.getItemID());
        nameField.setText(item.getPartName());
        categoryField.setText(item.getCategory());
        quantityField.setText(String.valueOf(item.getQuantity()));
        priceField.setText(String.valueOf(item.getPrice()));
        reorderField.setText(String.valueOf(item.getReorderLevel()));
        if (item instanceof ComputerPart) {
            ComputerPart part = (ComputerPart) item;
            brandField.setText(part.getBrand());
            warrantyField.setText(String.valueOf(part.getWarrantyMonths()));
        } else {
            brandField.setText("");
            warrantyField.setText("");
        }
    }

    private void clearFields() {
        for (JTextField field : new JTextField[] {
                idField, nameField, categoryField, quantityField, priceField,
                reorderField, brandField, warrantyField }) {
            field.setText("");
        }
        idField.requestFocusInWindow();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Inventory Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        System.out.print("Inventory Control "); // Chapter 2: print()
        System.out.println("is starting...");  // Chapter 2: println() + concatenation elsewhere.
        SwingUtilities.invokeLater(() -> new InventoryControl().setVisible(true));
    }
}