public class Employee extends Person implements Payable{
//    protected String name;
    protected long ID;
    protected int salary;
    protected int department;

    protected static int employeeNumber = 1;

    public Employee(String name, int salary, int department){
        super(name);
        this.salary = salary;
        this.department = department;
        this.ID = employeeNumber;
        employeeNumber++;
    }

    public int calcPaymentAmount(){
        return this.salary;
    }

    public int getIDcount(){
        return employeeNumber - 1;
    }

    public void editName(String newName){
        setName(newName);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public static int getEmployeeNumber() {
        return employeeNumber;
    }

    public static void setEmployeeNumber(int employeeNumber) {
        Employee.employeeNumber = employeeNumber;
    }
}
