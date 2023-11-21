import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class saveAndTakeData {
    private static final File fileName = new File("DATA.csv");;
    private static Scanner reader;

    /**
     * USE-CASE: This method is used when we want to collect all records of a specific type of item
     * @param Choice - This variable the type of item we want to find
     * @return - function returns an ArrayList of with a String[] as each element. Each element represents 1 row from DATA.csv
     */
    public ArrayList<String[]> takeDataUsingType(String Choice){
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
    public MyPairedResult takeDataUsingOrderID(String OrderID) throws FileNotFoundException{

        MyPairedResult bruh = new MyPairedResult(new String[0], 0);

        reader = new Scanner(fileName);
        int rowNum = 0;
        while (reader.hasNextLine()){
            String fullRow = reader.nextLine();
            String[] rowContents = fullRow.split(",");
            if (rowContents[3].equals(OrderID)){
                reader.close();
                return new MyPairedResult(rowContents,rowNum);
            }
            rowNum += 1;
        }


        System.out.println("Order: "+ OrderID+ " Does not exist." );
        return bruh;
    }

    /**
     * USE-CASE: When you want to find how many of an item is on hand
     * @param itemType - This variable is item we want to find
     * @return - returns an Integer object
     * @throws FileNotFoundException - Throws when the DATA.csv file is missing
     */
    public static Integer countQuantity(String itemType) throws FileNotFoundException {
        reader = new Scanner(fileName);
        int quantity = 0;
        while (reader.hasNextLine()) {
            String fullRow = reader.nextLine();
            String[] rowContents = fullRow.split(",");
            if (rowContents[1].equals(itemType))
                quantity += Integer.parseInt(rowContents[2]);
        }
        reader.close();
        return quantity;


    }

    /**
     * Edit a line in a CSV file.
     *
     * @param oldfileName   The name of the CSV file.
     * @param lineNumber The line number to edit (0-based index).
     * @param newData    The new data for the line as a string.
     * @return True if the line was successfully edited, false otherwise.
     */
    public static void editLineInCSV(String oldfileName, int lineNumber, String newData) {
        String tempFile = "temp.txt";
        File oldFile = new File(oldfileName);
        File newFile = new File(tempFile);

        try {
            FileWriter fw = new FileWriter(newFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            BufferedReader reader = new BufferedReader(new FileReader(oldFile));

            String currentLine;
            int lineCount = 0;
            while ((currentLine = reader.readLine()) != null){
                if (lineCount == lineNumber){
                    pw.write(newData+"\n");
                }
                else{
                    pw.write(currentLine+"\n");
                }
                lineCount++;
            }

            reader.close();
            pw.flush();
            pw.close();
            bw.close();
            fw.close();

            System.out.println(oldFile.delete());
            File dump = new File(oldfileName);
            newFile.renameTo(dump);

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }

    }
}
final class MyPairedResult {
    private final String[] first;
    private final int second;

    public MyPairedResult(String[] first, int second) {
        this.first = first;
        this.second = second;
    }

    public String[] getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}