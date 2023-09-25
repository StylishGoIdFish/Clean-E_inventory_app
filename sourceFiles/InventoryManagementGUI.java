import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class InventoryManagementGUI extends JFrame {
    private JButton addButton, refreshButton;
    private JTextField timeField, typeField, quantityField, orderIDField;
    private JTextArea displayArea;
    private saveAndTakeData dataHandler;

    public InventoryManagementGUI() {
        super("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        dataHandler = new saveAndTakeData();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Time:"));
        timeField = new JTextField();
        inputPanel.add(timeField);
        inputPanel.add(new JLabel("Item Type:"));
        typeField = new JTextField();
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Order ID:"));
        orderIDField = new JTextField();
        inputPanel.add(orderIDField);

        addButton = new JButton("Add to Inventory");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToInventory();
            }
        });

    }

    private void addToInventory() {
        String time = timeField.getText();
        String type = typeField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        int orderID = Integer.parseInt(orderIDField.getText());

        // Create a new inventory item and add it to the CSV file
        String[] rawData = {time, type, String.valueOf(quantity), String.valueOf(orderID)};
        saveData(rawData);

        // Clear input fields
        timeField.setText("");
        typeField.setText("");
        quantityField.setText("");
        orderIDField.setText("");

        displayArea.append("Item added to inventory: " + time + ", " + type + ", " + quantity + ", " + orderID + "\n");
    }

    private void refreshInventory() {
        displayArea.setText("");
        try {
            ArrayList<String[]> inventory = dataHandler.takeDataUsingType("");
            for (String[] item : inventory) {
                displayArea.append(String.join(", ", item) + "\n");
            }
        } catch (FileNotFoundException e) {
            displayArea.append("Error reading inventory data.\n");
        }
    }

    private void saveData(String[] data) {
        try (FileWriter writer = new FileWriter("sourceFiles/DATA.csv", true);
             PrintWriter printWriter = new PrintWriter(writer)) {
            printWriter.println(String.join(",", data));
        } catch (IOException e) {
            displayArea.append("Error saving data.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagementGUI gui = new InventoryManagementGUI();
            gui.setVisible(true);
        });
    }
}
