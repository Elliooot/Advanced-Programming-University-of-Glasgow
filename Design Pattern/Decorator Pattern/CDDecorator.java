public class CDDecorator extends CarDecorator{
    public CDDecorator(Car decoratedCar){
        super(decoratedCar);
    }
    public Double getPrice(){
        return super.getPrice() + 150;
    }
    public String getDescription(){
        return super.getDescription() + " + CD Player";
    }
}
