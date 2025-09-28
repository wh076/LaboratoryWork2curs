import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.Cylinder;

public class Main {
    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println("фигуры");
        System.out.println(" ");
        // Создаем круг и прямоугольник
        Circle circle = new Circle(5.0);
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        System.out.println("Круг:");
        System.out.println("Площадь: " + circle.area());
        System.out.println("Периметр: " + circle.perimeter());
        System.out.println(circle.toString());
        System.out.println(" ");
        System.out.println("Прямоугольник:");

        System.out.println("Площадь: " + rectangle.area());
        System.out.println("Периметр: " + rectangle.perimeter());
        System.out.println(rectangle.toString());
        System.out.println(" ");
        System.out.println("цилиндры");

        // Создаем цилиндры
        Cylinder cylinder1 = new Cylinder(circle, 10.0);
        Cylinder cylinder2 = new Cylinder(rectangle, 8.0);
        System.out.println(" ");
        System.out.println("Цилиндр 1:");
        System.out.println(cylinder1.toString());
        System.out.println("Объем: " + cylinder1.volume());
        System.out.println(" ");
        System.out.println("Цилиндр 2:");

        System.out.println(cylinder2.toString());
        System.out.println("Объем: " + cylinder2.volume());

        System.out.println("\nисключения");
        // Проверяем исключения
        try {
            Circle badCircle = new Circle(-2.0);
        } catch (Exception e) {
            System.out.println("Ошибка с кругом: " + e.getMessage());
        }

        try {
            Cylinder badCylinder = new Cylinder(circle, -5.0);
        } catch (Exception e) {
            System.out.println("Ошибка с цилиндром: " + e.getMessage());
        }

        System.out.println("\nПрограмма завершена!");
    }
}
