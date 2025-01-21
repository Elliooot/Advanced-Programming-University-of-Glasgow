import java.util.Objects;

public abstract class AbstractPet {
    protected String name;
    protected int age;
    public AbstractPet(String n, int a) {
        name = n;
        age = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(Objects.equals(name, "")){
            System.out.println("Error name");
        }else{
            this.name = name;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age < 1){
            System.out.println("Age Error");
        }else{
            this.age = age;
        }
    }

    public String toString() {
        return name + " is aged " + age;
    }
}