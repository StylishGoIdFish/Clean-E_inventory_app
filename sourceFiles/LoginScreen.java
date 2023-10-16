import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginScreen extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JTabbedPane mainTabbedPane;

    public LoginScreen(JTabbedPane mainPane) {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(300, 150);
        //setLocationRelativeTo(null); // Center the login screen
        //setLayout(new GridLayout(3, 2));

        mainTabbedPane = mainPane;

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                // Check the username and password (you can replace this with your own authentication logic)
                boolean authenticated = authenticateUser();

                if (authenticated) {
                    // Hide the login screen and show the main tabbed pane
                    setVisible(false);
                    mainTabbedPane.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Login failed. Please check your credentials.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                }
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
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
