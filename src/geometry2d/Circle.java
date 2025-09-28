package geometry2d;

public class Circle implements Figure {
    private double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть больше нуля");
        }
        this.radius = radius;
    }

    public double area() {
        return 3.14159 * radius * radius;
    }

    public double perimeter() {
        return 2 * 3.14159 * radius;
    }

    public String toString() {
        return "Круг с радиусом " + radius;
    }
}