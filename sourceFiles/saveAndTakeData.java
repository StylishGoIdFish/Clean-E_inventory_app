import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class saveAndTakeData {
    private final File fileName = new File("DATA.csv");;
    private Scanner reader;

    /**
     * USE-CASE: This method is used when we want to collect all records of a specific type of item
     * @param Choice - This variable the type of item we want to find
     * @throws FileNotFoundException - Throws when the DATA.csv file is missing
     * @return - function returns an ArrayList of with a String[] as each element. Each element represents 1 row from DATA.csv
     */
    public ArrayList<String[]> takeDataUsingType(String Choice) throws FileNotFoundException{
        ArrayList<String[]> returnEntries = new ArrayList<>();

        try {
            reader = new Scanner(fileName);

            while (reader.hasNextLine()){
                String fullRow = reader.nextLine();
                String[] rowContents = fullRow.split(",");
                if (rowContents[1].equals(Choice)){
                    returnEntries.add(rowContents);
                }
            }
            reader.close();

            return returnEntries;

        }
        catch (FileNotFoundException e) {
            System.out.print("Wahhhhhh :'( ");
            e.printStackTrace();
            return returnEntries;
        }

    }

    /**
     * USE-CASE: when you want to find a specific order and its attributes
     * @param OrderID - This variable is ID of the order we want to find
     * @return - function returns an Array of type String[]. Each element represents an attribute of the order
     * @throws FileNotFoundException - Throws when the DATA.csv file is missing
     */
    public String[] takeDataUsingOrderID(String OrderID) throws FileNotFoundException{

        String[] bruh = new String[0];

        reader = new Scanner(fileName);

        while (reader.hasNextLine()){
            String fullRow = reader.nextLine();
            String[] rowContents = fullRow.split(",");
            if (rowContents[3].equals(OrderID)){
                reader.close();
                return rowContents;
            }
        }


        System.out.println("Order: "+ OrderID+ " Does not exist." );
        return bruh;
    }

    /**
     * USE-CASE: When you want to find how many of an item is on hand
     * @param OrderID - This variable is ID of the order we want to find
     * @return - returns an Integer object
     * @throws FileNotFoundException - Throws when the DATA.csv file is missing
     */
    public Integer countQuantity(String OrderID) throws FileNotFoundException {
        reader = new Scanner(fileName);
        int quantity = 0;
        while (reader.hasNextLine()) {
            String fullRow = reader.nextLine();
            String[] rowContents = fullRow.split(",");
            if (rowContents[3].equals(OrderID))
                quantity += Integer.parseInt(rowContents[2]);
        }
        return quantity;
    }
}
