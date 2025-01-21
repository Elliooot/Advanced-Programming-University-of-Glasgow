public class Invoice implements Payable{
    double value;
    String description;

//    public Invoice(double value, String description){
//        this.value = value;
//        this.description = description;
//    }

    public double calcPaymentAmount(){
        return this.value;
    }

    public double getValue() {
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
