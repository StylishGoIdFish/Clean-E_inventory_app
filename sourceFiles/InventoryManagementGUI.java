import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class InventoryManagementGUI extends JFrame {

    //Declaring text fields and buttons for the UI
    private JButton addButton,  refreshButton;
    private JTextField usernameField, passwordField;
    private JTextField timeField, typeField, quantityField, orderIDField;
    private JTextArea displayArea;
    private saveAndTakeData dataHandler;
    private JTabbedPane tabbedPane;;

    public InventoryManagementGUI() {

        // Average set up for a GUI :P
        super("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        //Sets up the dataHandler using the saveAndTakeData class
        dataHandler = new saveAndTakeData();

        //Creates a input panel to put all of our fields to make the things look nice!
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

        //Fields for the username and password
        usernameField = new JTextField();
        passwordField = new JTextField();

        //Adding them to the input panel
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        //Creating the Add to Invenory Button
        addButton = new JButton("Add to Inventory");


        //Creating a login Button
        JButton loginButton = new JButton("Login");
        inputPanel.add(loginButton);

        //Creating a button panel for buttons. adds the add to inventory button in it
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        //Creates a scrollPane text area for output and recording activity
        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Item", inputPanel);
        tabbedPane.addTab("View Items", scrollPane);

        JPanel inventoryInfoPanel = new InventoryInfoTab();
        tabbedPane.addTab("Inventory Info", inventoryInfoPanel);

        JPanel editInventoryTab = new EditInventoryInfoTab();
        tabbedPane.addTab("Edit Inventory", editInventoryTab);

        // Create a login screen and add it as the first tab
        //LoginScreen loginScreen = new LoginScreen(tabbedPane);
        //tabbedPane.addTab("Login", loginScreen);

        // Initially, hide the main tabbed pane
        //tabbedPane.setVisible(false);

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

    /**
     * @return - function
     * */
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

        if (authenticateUser()) {
            String time = timeField.getText();
            String type = typeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            int orderID = Integer.parseInt(orderIDField.getText());

            String user = usernameField.getText();

            // Create a new inventory item and add it to the CSV file
            String[] rawData = {time, type, String.valueOf(quantity), String.valueOf(orderID), user};
            saveData(rawData);

            // Clear input fields
            timeField.setText("");
            typeField.setText("");
            quantityField.setText("");
            orderIDField.setText("");

            displayArea.append("Item added to inventory: " + time + ", " + type + ", " + quantity + ", " + orderID + "\n");
        }
        else {
            JOptionPane.showMessageDialog(this, "Authentication failed. Please check your username and password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void saveData (String[] data) {
        try (FileWriter writer = new FileWriter("DATA.csv", true);
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
