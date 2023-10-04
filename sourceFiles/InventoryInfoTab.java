import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class InventoryInfoTab extends JPanel {
    private JTextField textField1, textField2;
    private JButton buttonOrder, button2;
    private JTextArea displayArea;

    saveAndTakeData dataHandler = new saveAndTakeData();

    public InventoryInfoTab(){
        setLayout(new BorderLayout());



        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        textField1 = new JTextField();
        inputPanel.add(textField1);
        buttonOrder = new JButton("Search For a Specific Entry here using order #");
        inputPanel.add(buttonOrder);
        textField2 = new JTextField();
        inputPanel.add(textField2);
        button2 = new JButton("Search for a set of all entries for type of object");
        inputPanel.add(button2);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        buttonOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();

                dataHandler.takeDataUsingOrderID(text);



                displayArea.append("Field 1: " + text + "\n");
                textField1.setText("");
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField2.getText();
                displayArea.append("Field 2: " + text + "\n");
                textField2.setText("");
            }
        });
    }
}
