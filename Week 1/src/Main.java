public class Main {
    public static void main(String[] args) {
//        Vec2d v1 = new Vec2d(1, 2);
//        Vec2d v2 = new Vec2d(3, 4);
//        Vec2d v3 = v1.add(v2);
//        System.out.println(v3.x + ", " + v3.y);
//
//        Vec2d scaled = v1.scale(2);
//        System.out.println(scaled.x + ", " + scaled.y);
//
//        Vec2d diff = v2.minus(v1);
//        System.out.println(diff.x + ", " + diff.y);

        Employee employee1 = new Employee("David", 5000, "A");
        System.out.println(employee1);

        Manager manager = new Manager("Cole", 10000, "C",2000);
        System.out.println(manager.calcTotalEarnings());
    }
}