public class Invoice implements Payable{
    protected int value;
    protected String description;

    public Invoice(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int calcPaymentAmount(){
        return value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
