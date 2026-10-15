/*Austin Radloff
 *
 * This is the main GUI program for the inventory system.
 * The user can add, search, update, remove, display,
 * save, and load computer repair inventory.
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class InventoryControl extends JFrame
                implements ActionListener {

        private InventoryManager manager;

        // Text fields used for user input.
        private JTextField idField;
        private JTextField nameField;
        private JTextField categoryField;
        private JTextField quantityField;
        private JTextField priceField;
        private JTextField reorderField;
        private JTextField brandField;
        private JTextField warrantyField;

        // Buttons used by the program.
        private JButton addButton;
        private JButton searchButton;
        private JButton quantityButton;
        private JButton priceButton;
        private JButton removeButton;
        private JButton displayButton;
        private JButton saveButton;
        private JButton loadButton;
        private JButton clearButton;

        private JTextArea outputArea;

        public InventoryControl() {

                manager = new InventoryManager();

                setTitle("Computer Repair Shop Inventory Control");

                setDefaultCloseOperation(
                                JFrame.EXIT_ON_CLOSE);

                setSize(1000, 700);

                setLocationRelativeTo(null);

                setLayout(new BorderLayout(10, 10));

                createInputPanel();
                createButtonPanel();
                createOutputArea();

                setVisible(true);
        }

        // Creates the text fields and labels.
        private void createInputPanel() {

                JPanel inputPanel = new JPanel(new GridLayout(
                                4, 4, 10, 10));

                inputPanel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Inventory Item Information"));

                idField = new JTextField();
                nameField = new JTextField();
                categoryField = new JTextField();
                quantityField = new JTextField();
                priceField = new JTextField();
                reorderField = new JTextField();
                brandField = new JTextField();
                warrantyField = new JTextField();

                inputPanel.add(new JLabel("Item ID:"));
                inputPanel.add(idField);

                inputPanel.add(new JLabel("Part Name:"));
                inputPanel.add(nameField);

                inputPanel.add(new JLabel("Category:"));
                inputPanel.add(categoryField);

                inputPanel.add(new JLabel("Quantity:"));
                inputPanel.add(quantityField);

                inputPanel.add(new JLabel("Price:"));
                inputPanel.add(priceField);

                inputPanel.add(new JLabel("Reorder Level:"));
                inputPanel.add(reorderField);

                inputPanel.add(new JLabel("Brand:"));
                inputPanel.add(brandField);

                inputPanel.add(new JLabel("Warranty Months:"));
                inputPanel.add(warrantyField);

                add(inputPanel, BorderLayout.NORTH);
        }

        // Creates the buttons and registers ActionListeners.
        private void createButtonPanel() {

                JPanel buttonPanel = new JPanel(new GridLayout(
                                3, 3, 8, 8));

                buttonPanel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Inventory Controls"));

                addButton = new JButton("Add Item");

                searchButton = new JButton("Search");

                quantityButton = new JButton("Update Quantity");

                priceButton = new JButton("Update Price");

                removeButton = new JButton("Remove Item");

                displayButton = new JButton("Display Inventory");

                saveButton = new JButton("Save Inventory");

                loadButton = new JButton("Load Inventory");

                clearButton = new JButton("Clear Fields");

                addButton.addActionListener(this);
                searchButton.addActionListener(this);
                quantityButton.addActionListener(this);
                priceButton.addActionListener(this);
                removeButton.addActionListener(this);
                displayButton.addActionListener(this);
                saveButton.addActionListener(this);
                loadButton.addActionListener(this);
                clearButton.addActionListener(this);

                buttonPanel.add(addButton);
                buttonPanel.add(searchButton);
                buttonPanel.add(quantityButton);
                buttonPanel.add(priceButton);
                buttonPanel.add(removeButton);
                buttonPanel.add(displayButton);
                buttonPanel.add(saveButton);
                buttonPanel.add(loadButton);
                buttonPanel.add(clearButton);

                add(buttonPanel, BorderLayout.WEST);
        }

        // Creates the scrolling inventory display area.
        private void createOutputArea() {

                outputArea = new JTextArea();

                outputArea.setEditable(false);

                outputArea.setFont(
                                new Font(
                                                Font.MONOSPACED,
                                                Font.PLAIN,
                                                14));

                outputArea.setText(
                                "Computer Repair Shop Inventory\n"
                                                + "Use the fields and buttons to manage inventory.");

                JScrollPane scrollPane = new JScrollPane(outputArea);

                scrollPane.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Inventory Display"));

                add(scrollPane, BorderLayout.CENTER);
        }

        /*
         * Determines which button the user selected
         * and calls the correct method.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();

                if (source == addButton) {
                        addItem();

                } else if (source == searchButton) {
                        searchItem();

                } else if (source == quantityButton) {
                        updateQuantity();

                } else if (source == priceButton) {
                        updatePrice();

                } else if (source == removeButton) {
                        removeItem();

                } else if (source == displayButton) {
                        displayInventory();

                } else if (source == saveButton) {
                        saveInventory();

                } else if (source == loadButton) {
                        loadInventory();

                } else if (source == clearButton) {
                        clearFields();
                }
        }

        // Adds a new computer part from information entered by the user.
        private void addItem() {

                try {

                        int id = getInteger(idField, "Item ID");

                        String name = getText(nameField, "Part Name");

                        String category = getText(categoryField, "Category");

                        int quantity = getInteger(
                                        quantityField,
                                        "Quantity");

                        double price = getDouble(
                                        priceField,
                                        "Price");

                        int reorder = getInteger(
                                        reorderField,
                                        "Reorder Level");

                        String brand = getText(
                                        brandField,
                                        "Brand");

                        int warranty = getInteger(
                                        warrantyField,
                                        "Warranty Months");

                        if (id <= 0) {
                                throw new IllegalArgumentException(
                                                "Item ID must be greater than 0.");
                        }

                        if (quantity < 0) {
                                throw new IllegalArgumentException(
                                                "Quantity cannot be negative.");
                        }

                        if (price < 0) {
                                throw new IllegalArgumentException(
                                                "Price cannot be negative.");
                        }

                        if (reorder < 0) {
                                throw new IllegalArgumentException(
                                                "Reorder level cannot be negative.");
                        }

                        if (warranty < 0) {
                                throw new IllegalArgumentException(
                                                "Warranty cannot be negative.");
                        }

                        /*
                         * Commas are not allowed because the inventory
                         * file uses commas to separate stored data.
                         */
                        if (name.contains(",")
                                        || category.contains(",")
                                        || brand.contains(",")) {

                                throw new IllegalArgumentException(
                                                "Names, categories, and brands "
                                                                + "cannot contain commas.");
                        }

                        ComputerPart part = new ComputerPart(
                                        id,
                                        name,
                                        category,
                                        quantity,
                                        price,
                                        reorder,
                                        brand,
                                        warranty);

                        boolean added = manager.addItem(part);

                        if (added) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Item added successfully.");

                                displayInventory();

                                clearFields();

                        } else {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "The item could not be added.\n"
                                                                + "The ID may already exist "
                                                                + "or the inventory may be full.",
                                                "Add Item",
                                                JOptionPane.WARNING_MESSAGE);
                        }

                } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Please enter valid numbers "
                                                        + "in all numeric fields.",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        e.getMessage(),
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Searches for an item using the ID entered by the user.
        private void searchItem() {

                try {

                        int id = getInteger(
                                        idField,
                                        "Item ID");

                        InventoryItem item = manager.searchByID(id);

                        if (item == null) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "No item was found with ID "
                                                                + id + ".",
                                                "Search",
                                                JOptionPane.INFORMATION_MESSAGE);

                                return;
                        }

                        // Fill the GUI fields with the item's information.
                        idField.setText(
                                        String.valueOf(
                                                        item.getItemID()));

                        nameField.setText(
                                        item.getPartName());

                        categoryField.setText(
                                        item.getCategory());

                        quantityField.setText(
                                        String.valueOf(
                                                        item.getQuantity()));

                        priceField.setText(
                                        String.format(
                                                        "%.2f",
                                                        item.getPrice()));

                        reorderField.setText(
                                        String.valueOf(
                                                        item.getReorderLevel()));

                        if (item instanceof ComputerPart) {

                                ComputerPart part = (ComputerPart) item;

                                brandField.setText(
                                                part.getBrand());

                                warrantyField.setText(
                                                String.valueOf(
                                                                part.getWarrantyMonths()));

                        } else {

                                brandField.setText("");
                                warrantyField.setText("");
                        }

                        outputArea.setText(
                                        "ITEM FOUND\n"
                                                        + "====================================\n\n"
                                                        + item.toString());

                        if (item.isLowStock()) {

                                outputArea.append(
                                                "\n\n*** LOW STOCK - REORDER NEEDED ***");
                        }

                } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Enter a valid Item ID.",
                                        "Search Error",
                                        JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        e.getMessage(),
                                        "Search Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Updates the quantity of an existing item.
        private void updateQuantity() {

                try {

                        int id = getInteger(
                                        idField,
                                        "Item ID");

                        int quantity = getInteger(
                                        quantityField,
                                        "Quantity");

                        if (quantity < 0) {

                                throw new IllegalArgumentException(
                                                "Quantity cannot be negative.");
                        }

                        boolean updated = manager.updateQuantity(
                                        id,
                                        quantity);

                        if (updated) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Quantity updated successfully.");

                                displayInventory();

                        } else {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Item ID was not found.",
                                                "Update Quantity",
                                                JOptionPane.WARNING_MESSAGE);
                        }

                } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Enter a valid Item ID and quantity.",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        e.getMessage(),
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Updates the price of an existing item.
        private void updatePrice() {

                try {

                        int id = getInteger(
                                        idField,
                                        "Item ID");

                        double price = getDouble(
                                        priceField,
                                        "Price");

                        if (price < 0) {

                                throw new IllegalArgumentException(
                                                "Price cannot be negative.");
                        }

                        boolean updated = manager.updatePrice(
                                        id,
                                        price);

                        if (updated) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Price updated successfully.");

                                displayInventory();

                        } else {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Item ID was not found.",
                                                "Update Price",
                                                JOptionPane.WARNING_MESSAGE);
                        }

                } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Enter a valid Item ID and price.",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        e.getMessage(),
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Removes an item after asking the user for confirmation.
        private void removeItem() {

                try {

                        int id = getInteger(
                                        idField,
                                        "Item ID");

                        InventoryItem item = manager.searchByID(id);

                        if (item == null) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Item ID was not found.",
                                                "Remove Item",
                                                JOptionPane.WARNING_MESSAGE);

                                return;
                        }

                        int choice = JOptionPane.showConfirmDialog(
                                        this,
                                        "Are you sure you want to remove "
                                                        + item.getPartName()
                                                        + "?",
                                        "Confirm Removal",
                                        JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {

                                manager.removeItem(id);

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Item removed successfully.");

                                clearFields();
                                displayInventory();
                        }

                } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Enter a valid Item ID.",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        e.getMessage(),
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Displays all current inventory in the JTextArea.
        private void displayInventory() {

                outputArea.setText(
                                manager.getInventoryText());
        }

        // Saves inventory data to the relative inventory.txt file.
        private void saveInventory() {

                try {

                        manager.saveInventoryToFile();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Inventory saved successfully "
                                                        + "to inventory.txt.");

                } catch (IOException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "The inventory could not be saved.\n"
                                                        + e.getMessage(),
                                        "File Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Loads inventory data from inventory.txt.
        private void loadInventory() {

                try {

                        manager.loadInventoryFromFile();

                        displayInventory();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Inventory loaded successfully.\n"
                                                        + manager.getItemCount()
                                                        + " item(s) loaded.");

                } catch (IOException e) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "The inventory could not be loaded.\n"
                                                        + e.getMessage(),
                                        "File Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        // Clears all user input fields.
        private void clearFields() {

                idField.setText("");
                nameField.setText("");
                categoryField.setText("");
                quantityField.setText("");
                priceField.setText("");
                reorderField.setText("");
                brandField.setText("");
                warrantyField.setText("");

                idField.requestFocus();
        }

        // Reads a required String from a JTextField.
        private String getText(
                        JTextField field,
                        String fieldName) {

                String value = field.getText().trim();

                if (value.isEmpty()) {

                        throw new IllegalArgumentException(
                                        fieldName + " cannot be blank.");
                }

                return value;
        }

        // Converts a required JTextField value to an integer.
        private int getInteger(
                        JTextField field,
                        String fieldName) {

                String value = getText(field, fieldName);

                return Integer.parseInt(value);
        }

        // Converts a required JTextField value to a double.
        private double getDouble(
                        JTextField field,
                        String fieldName) {

                String value = getText(field, fieldName);

                return Double.parseDouble(value);
        }

        // Main method starts the GUI program.
        public static void main(String[] args) {

                SwingUtilities.invokeLater(
                                new Runnable() {

                                        @Override
                                        public void run() {
                                                new InventoryControl();
                                        }
                                });
        }
}