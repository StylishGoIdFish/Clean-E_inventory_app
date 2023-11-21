import java.io.FileNotFoundException;

public class InventoryItem {
    private String time;
    private String itemType;
    private Integer quantityAddedInOrder;
    private Integer orderID;

    private String personAdded;

    public InventoryItem(String[] rawData){
        time = rawData[0];
        itemType = rawData[1];
        quantityAddedInOrder = Integer.parseInt(rawData[2]);
        orderID = Integer.parseInt(rawData[3]);
        personAdded = rawData[4];

    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getType(){
        return itemType;
    }

    public void setType(String type){
        this.itemType = type;
    }

    public Integer getID(){
        return orderID;
    }

    public Integer getOrderQuantity(){
        return quantityAddedInOrder;
    }

    public void setQuantityAddedInOrder( Integer input){
        quantityAddedInOrder = input;
    }

    public Integer getTotalQuantity() throws FileNotFoundException {
        return saveAndTakeData.countQuantity(this.getType());
    }

    public String getPerson(){
        return personAdded;
    }

    @Override
    public String toString() {
        try{getTotalQuantity().toString();
        return getTime() + "," + getType() + "," + getOrderQuantity() + "," + getID() + "," +  getPerson();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
