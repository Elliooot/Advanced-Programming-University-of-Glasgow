public class AlloyDecorator extends CarDecorator {
    public AlloyDecorator(Car decoratedCar){
        super(decoratedCar);
    }
    public Double getPrice(){
        return super.getPrice() + 250;
    }
    public String getDescription(){
        return super.getDescription() + " + Alloys";
    }
}
