import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class EditInventoryInfoTab extends JPanel {
    private JTextField textField1, orderField;
    private JButton Editbutton1, findButton;
    private JTextArea displayArea;

    private InventoryItem editable;

    private saveAndTakeData dataHandler = new saveAndTakeData();
    public EditInventoryInfoTab() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        textField1 = new JTextField();
        inputPanel.add(textField1);

        Editbutton1 = new JButton("Submit Field 1");
        inputPanel.add(Editbutton1);

        orderField = new JTextField();
        inputPanel.add(orderField);

        findButton = new JButton("Choose order to edit (use order ID)");
        inputPanel.add(findButton);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        Editbutton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                String[] input =text.toUpperCase().split(" ");

                if (input[0].equals("QUANTITY")) {
                    editable.setQuantityAddedInOrder(Integer.parseInt(input[1]));
                    displayArea.append("CHANGED QUANTITY ADDED IN ORDER #" + editable.getID() + "TO " + input[1]);
                }

                // time to EDIIIIITTTT

                String temp = "tempFile.txt";
                File oldFile = new File("DATA.csv");
                File tempFile = new File("tempFile.txt");


                try{

                    FileWriter fw = new FileWriter(tempFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);

                    Scanner scan = new Scanner(oldFile);

                    while (scan.hasNextLine()){

                        if (scan.nextLine().split(",")[3].equals(editable.getID().toString())){
                            pw.println(editable.getTime() + "," + editable.getType() + "," + editable.getOrderQuantity()+ "," + editable.getID(), )
                        }//START HERE
                        else{
                            pw.println(scan.nextLine());
                        }
                    }
                }
                catch(Exception ex){}


                textField1.setText("");

            }
        });


        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    editable = new InventoryItem(dataHandler.takeDataUsingOrderID(orderField.getText()).getFirst());

                    displayArea.append("EDITABLE ORDER FOUND; YOU CAN NOW EDIT CONTENTS OF ORDER");
                }
                catch (FileNotFoundException p){
                    p.printStackTrace();
                }
            }
        });


    }
}
