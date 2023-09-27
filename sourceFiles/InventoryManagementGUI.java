import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class InventoryManagementGUI extends JFrame {
    private JButton addButton,  refreshButton;

    private JTextField usernameField, passwordField;

    private JTextField timeField, typeField, quantityField, orderIDField;
    private JTextArea displayArea;
    private saveAndTakeData dataHandler;
    private JTabbedPane tabbedPane;

    public InventoryManagementGUI() {
        super("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
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


        usernameField = new JTextField();
        passwordField = new JTextField();

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        addButton = new JButton("Add to Inventory");

        JButton loginButton = new JButton("Login");
        inputPanel.add(loginButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Item", inputPanel);
        tabbedPane.addTab("View Items", scrollPane);

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToInventory();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private boolean authenticateUser() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText(); // Get the password as a String

        // Check if the username and password match a pair in the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader("sourceFiles\\user_credentials.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && enteredUsername.equals(credentials[0]) && enteredPassword.equals(credentials[1])) {
                    // Authentication successful, enable the "Add to Inventory" button
                    addButton.setEnabled(true);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Authentication failed, disable the "Add to Inventory" button
        addButton.setEnabled(false);
        return false;
    }
    private void addToInventory() {

        if (authenticateUser()){
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

        else{
            JOptionPane.showMessageDialog(this, "Authentication failed. Please check your username and password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void saveData(String[] data) {
        try (FileWriter writer = new FileWriter("sourceFiles\\DATA.csv", true);
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
