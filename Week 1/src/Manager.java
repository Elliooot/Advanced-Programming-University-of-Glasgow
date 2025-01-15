public class Manager extends Employee{
    private int bonus;

    public Manager(String name,  int salary, int department, int bonus){
        super(name, salary, department);
        this.bonus = bonus;
    }

    public int calcTotalEarnings(){
        return salary + bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
