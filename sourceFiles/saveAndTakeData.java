import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class saveAndTakeData {
    private final File fileName = new File("DATA.csv");;
    private Scanner reader;

    public void takeDataUsingID(String IDChoice){
        ArrayList<String[]> returnEntries = new ArrayList<>();

        try {
            reader = new Scanner(fileName);

            while (reader.hasNextLine()){
                String fullRow = reader.nextLine();
                String[] rowContents = fullRow.split(",");
                if (rowContents[1].equals(IDChoice)){
                    returnEntries.add(rowContents);
                }
            }
            reader.close();

        }
        catch (FileNotFoundException e) {
            System.out.print("Wahhhhhh :'( ");
            e.printStackTrace();
        }

    }



}
