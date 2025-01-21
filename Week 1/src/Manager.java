public class Manager extends Employee{
    private double bonus;

    public Manager(String n,  int s, String d, double b){
        super(n, s, d);
        this.bonus = b;
    }

    public double calcTotalEarnings(){
        return getSalary() + bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
