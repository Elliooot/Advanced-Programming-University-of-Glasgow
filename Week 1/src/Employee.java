public class Employee extends Person implements Payable{
//    protected String name;
    private int ID;
    private double salary;
    private String department;
    private static int employeeNumber = 0;

    public Employee(String n, double s, String d){
        this.name = n;
        this.salary = s;
        this.department = d;
        employeeNumber++;
        this.ID = employeeNumber;
    }

    public double calcPaymentAmount(){
        return this.salary;
    }

    public int getIDcount(){
        return employeeNumber;
    }

    public void editName(String newName){
        this.name = newName;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
