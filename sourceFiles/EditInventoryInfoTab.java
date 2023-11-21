import javax.swing.*;
import java.awt.*;
import java.io.*;

public class EditInventoryInfoTab extends JPanel {
    private final JTextField textField1;
    private final JTextField orderField;
    private final JTextArea displayArea;

    private InventoryItem editable;

    private Integer rowNum;
    private final saveAndTakeData dataHandler = new saveAndTakeData();
    public EditInventoryInfoTab() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        textField1 = new JTextField();
        inputPanel.add(textField1);

        JButton editButton = new JButton("Submit Field 1");
        inputPanel.add(editButton);

        orderField = new JTextField();
        inputPanel.add(orderField);

        JButton findButton = new JButton("Choose order to edit (use order ID)");
        inputPanel.add(findButton);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        editButton.addActionListener(e -> {
            String text = textField1.getText();
            String[] input =text.toUpperCase().split(" ");

            switch (input[0]) {
                case ("QUANTITY"):
                    editable.setQuantityAddedInOrder(Integer.parseInt(input[1]));
                    displayArea.append("CHANGED QUANTITY ADDED IN ORDER #" + editable.getID() + " TO " + input[1] + "\n");
                    break;
                case ("TIME"):
                    editable.setTime(input[1]);
                    displayArea.append("CHANGED TIME ADDED FOR ORDER #" + editable.getID() + " TO " + input[1] + "\n");
                    break;
                case ("TYPE"):
                    editable.setType(input[1]);
                    displayArea.append("CHANGED TYPE ORDER #" + editable.getID() + " TO " + input[1] + "\n");
                    break;
            }

            // time to EDIIIIITTTT
            dataHandler.editLineInCSV("DATA.csv", rowNum, editable.toString());

            textField1.setText("");

        });


        findButton.addActionListener(e -> {
            try{
                editable = new InventoryItem(dataHandler.takeDataUsingOrderID(orderField.getText()).getFirst());
                rowNum = dataHandler.takeDataUsingOrderID(orderField.getText()).getSecond();
                displayArea.append("EDITABLE ORDER FOUND; YOU CAN NOW EDIT CONTENTS OF ORDER\n");
            }
            catch (FileNotFoundException p){
                p.printStackTrace();
            }
        });


    }
}
