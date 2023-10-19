import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InventoryInfoTab extends JPanel {
    private final JTextField textField1;
    private final JTextField textField2;
    private final JTextArea displayArea;

    saveAndTakeData dataHandler = new saveAndTakeData();

    public InventoryInfoTab(){
        setLayout(new BorderLayout());



        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        textField1 = new JTextField();
        inputPanel.add(textField1);
        JButton buttonOrder = new JButton("Search For a Specific Entry here using order #");
        inputPanel.add(buttonOrder);
        textField2 = new JTextField();
        inputPanel.add(textField2);
        JButton button2 = new JButton("Search for a set of all entries for type of object");
        inputPanel.add(button2);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        buttonOrder.addActionListener(e -> {
            String text = textField1.getText();
            try {
                String[] entry = (dataHandler.takeDataUsingOrderID(text)).getFirst();
                InventoryItem returnItem = new InventoryItem(entry);
                displayArea.append("---ORDER FOUND-- " + returnItem.toString() + "\n");
            }
            catch (FileNotFoundException error) {
                error.printStackTrace();
            }
            textField1.setText("");
        });

        button2.addActionListener(e -> {
            String text = textField2.getText();

            ArrayList<String[]> entries = dataHandler.takeDataUsingType(text);

            for (String[] entry : entries){
                InventoryItem returnItem = new InventoryItem(entry);
                displayArea.append("---ORDER FOUND--- " + returnItem.toString() + "\n");
            }

            textField2.setText("");
        });
    }
}
