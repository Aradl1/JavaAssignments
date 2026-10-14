//Austin Radloff

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryControlGUI
        extends JFrame
        implements ActionListener {

    private InventoryManager manager;

    private JTextField idField;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField reorderField;

    private JButton addButton;
    private JButton searchButton;
    private JButton updateQuantityButton;
    private JButton updatePriceButton;
    private JButton removeButton;
    private JButton displayButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton lowStockButton;
    private JButton clearButton;

    private JTextArea outputArea;

    public InventoryControlGUI() {

        manager = new InventoryManager();

        setTitle("Inventory Control System");
        setSize(800, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();

        topPanel.setLayout(
                new BorderLayout(10, 10));

        // Input area
        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(
                new GridLayout(6, 2, 5, 5));

        inputPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Inventory Item Information"));

        inputPanel.add(
                new JLabel("Item ID:"));

        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(
                new JLabel("Part Name:"));

        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(
                new JLabel("Category:"));

        categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(
                new JLabel("Quantity:"));

        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(
                new JLabel("Price:"));

        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(
                new JLabel("Reorder Level:"));

        reorderField = new JTextField();
        inputPanel.add(reorderField);

        topPanel.add(
                inputPanel,
                BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
                new GridLayout(2, 5, 5, 5));

        buttonPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Inventory Options"));

        addButton = new JButton("Add Item");

        searchButton = new JButton("Search");

        updateQuantityButton = new JButton("Update Quantity");

        updatePriceButton = new JButton("Update Price");

        removeButton = new JButton("Remove Item");

        displayButton = new JButton("Display Inventory");

        saveButton = new JButton("Save Inventory");

        loadButton = new JButton("Load Inventory");

        lowStockButton = new JButton("Low Stock");

        clearButton = new JButton("Clear Fields");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateQuantityButton);
        buttonPanel.add(updatePriceButton);
        buttonPanel.add(removeButton);

        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(lowStockButton);
        buttonPanel.add(clearButton);

        topPanel.add(
                buttonPanel,
                BorderLayout.CENTER);

        add(
                topPanel,
                BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();

        outputArea.setEditable(false);

        outputArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Inventory Information"));

        add(
                scrollPane,
                BorderLayout.CENTER);

        // Action listeners
        addButton.addActionListener(this);
        searchButton.addActionListener(this);

        updateQuantityButton.addActionListener(
                this);

        updatePriceButton.addActionListener(
                this);

        removeButton.addActionListener(this);
        displayButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        lowStockButton.addActionListener(this);
        clearButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            if (e.getSource() == addButton) {
                addItem();
            }

            if (e.getSource() == searchButton) {
                searchItem();
            }

            if (e.getSource() == updateQuantityButton) {

                updateQuantity();
            }

            if (e.getSource() == updatePriceButton) {

                updatePrice();
            }

            if (e.getSource() == removeButton) {

                removeItem();
            }

            if (e.getSource() == displayButton) {

                displayInventory();
            }

            if (e.getSource() == saveButton) {

                saveInventory();
            }

            if (e.getSource() == loadButton) {

                loadInventory();
            }

            if (e.getSource() == lowStockButton) {

                displayLowStock();
            }

            if (e.getSource() == clearButton) {

                clearFields();
                outputArea.setText("");
            }

        } catch (NumberFormatException exception) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter numbers in the correct fields.");
        }
    }

    private void addItem() {

        if (idField.getText().isEmpty()
                || nameField.getText().isEmpty()
                || categoryField.getText().isEmpty()
                || quantityField.getText().isEmpty()
                || priceField.getText().isEmpty()
                || reorderField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.");

            return;
        }

        int id = Integer.parseInt(
                idField.getText());

        String name = nameField.getText();

        String category = categoryField.getText();

        int quantity = Integer.parseInt(
                quantityField.getText());

        double price = Double.parseDouble(
                priceField.getText());

        int reorderLevel = Integer.parseInt(
                reorderField.getText());

        if (quantity < 0
                || price < 0
                || reorderLevel < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity, price, and reorder level cannot be negative.");

            return;
        }

        if (manager.searchByID(id) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "An item with that ID already exists.");

            return;
        }

        InventoryItem item = new InventoryItem(
                id,
                name,
                category,
                quantity,
                price,
                reorderLevel);

        if (manager.addItem(item)) {

            outputArea.setText(
                    "Item added successfully.\n\n"
                            + formatItem(item));

            clearFields();

        } else {

            outputArea.setText(
                    "Inventory is full. Item could not be added.");
        }
    }

    private void searchItem() {

        if (idField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter an Item ID to search.");

            return;
        }

        int id = Integer.parseInt(
                idField.getText());

        InventoryItem item = manager.searchByID(id);

        if (item != null) {

            outputArea.setText(
                    formatItem(item));

        } else {

            outputArea.setText(
                    "No inventory item was found with ID "
                            + id + ".");
        }
    }

    private void updateQuantity() {

        if (idField.getText().isEmpty()
                || quantityField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter the Item ID and new quantity.");

            return;
        }

        int id = Integer.parseInt(
                idField.getText());

        int quantity = Integer.parseInt(
                quantityField.getText());

        if (quantity < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity cannot be negative.");

            return;
        }

        if (manager.updateQuantity(
                id,
                quantity)) {

            outputArea.setText(
                    "Quantity updated successfully for item "
                            + id + ".");

        } else {

            outputArea.setText(
                    "Item was not found.");
        }
    }

    private void updatePrice() {

        if (idField.getText().isEmpty()
                || priceField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter the Item ID and new price.");

            return;
        }

        int id = Integer.parseInt(
                idField.getText());

        double price = Double.parseDouble(
                priceField.getText());

        if (price < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price cannot be negative.");

            return;
        }

        if (manager.updatePrice(
                id,
                price)) {

            outputArea.setText(
                    "Price updated successfully for item "
                            + id + ".");

        } else {

            outputArea.setText(
                    "Item was not found.");
        }
    }

    private void removeItem() {

        if (idField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter an Item ID to remove.");

            return;
        }

        int id = Integer.parseInt(
                idField.getText());

        InventoryItem item = manager.searchByID(id);

        if (item == null) {

            outputArea.setText(
                    "Item was not found.");

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

            outputArea.setText(
                    "Item " + id
                            + " was removed from inventory.");

            clearFields();
        }
    }

    private void displayInventory() {

        outputArea.setText("");

        if (manager.getItemCount() == 0) {

            outputArea.setText(
                    "There are no items in inventory.");

            return;
        }

        for (int x = 0; x < manager.getItemCount(); x++) {

            InventoryItem item = manager.getItem(x);

            outputArea.append(
                    formatItem(item));

            outputArea.append(
                    "\n------------------------------------\n");
        }

        outputArea.append(
                "\nTotal Inventory Value: $"
                        + String.format(
                                "%.2f",
                                manager.getTotalInventoryValue()));
    }

    private void displayLowStock() {

        outputArea.setText(
                "LOW STOCK ITEMS\n");

        outputArea.append(
                "------------------------------------\n");

        boolean found = false;

        for (int x = 0; x < manager.getItemCount(); x++) {

            InventoryItem item = manager.getItem(x);

            if (item.isLowStock()) {

                outputArea.append(
                        formatItem(item));

                outputArea.append(
                        "\n------------------------------------\n");

                found = true;
            }
        }

        if (!found) {

            outputArea.setText(
                    "There are currently no low stock items.");
        }
    }

    private void saveInventory() {

        if (manager.saveInventoryToFile()) {

            outputArea.setText(
                    "Inventory was saved successfully to inventory.txt.");

        } else {

            outputArea.setText(
                    "There was an error saving inventory.txt.");
        }
    }

    private void loadInventory() {

        if (manager.loadInventoryFromFile()) {

            displayInventory();

        } else {

            outputArea.setText(
                    "inventory.txt could not be found.\n\n"
                            + "Check the VS Code terminal to see "
                            + "the exact folder Java is looking in.");
        }
    }

    private String formatItem(
            InventoryItem item) {

        String information = "Item ID: "
                + item.getItemID() + "\n"

                + "Part Name: "
                + item.getPartName() + "\n"

                + "Category: "
                + item.getCategory() + "\n"

                + "Quantity: "
                + item.getQuantity() + "\n"

                + "Price: $"
                + String.format(
                        "%.2f",
                        item.getPrice())
                + "\n"

                + "Reorder Level: "
                + item.getReorderLevel()
                + "\n"

                + "Inventory Value: $"
                + String.format(
                        "%.2f",
                        item.getInventoryValue())
                + "\n";

        if (item.isLowStock()) {

            information += "Status: LOW STOCK\n";

        } else {

            information += "Status: In Stock\n";
        }

        return information;
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
        reorderField.setText("");
    }
}