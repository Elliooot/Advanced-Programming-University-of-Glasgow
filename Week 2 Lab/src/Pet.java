public class Pet extends AbstractPet{
//    protected String name;
//    protected int age;
    public Pet(String n, int a){
        super(n, a);
    }
    public Pet(String n){
        super(n, 0); // 1. Create another constructor in the parent class, will make an effect in a larger range
        // 2. Method overloading: this(n, 0), the concept can apply to here
//        age = 0;
    }
    public String toString(){
        return this.name + " is my pet and is aged " + age;
    }
}
