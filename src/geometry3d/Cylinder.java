package geometry3d;
import geometry2d.Figure;
import exceptions.NegativeValueException;
import exceptions.ZeroValueException;

public class Cylinder {
    private Figure base;
    private double height;

    public Cylinder(Figure base, double height) {
        if (height < 0) {
            throw new NegativeValueException("Высота не может быть отрицательной");
        }
        if (height == 0) {
            throw new ZeroValueException("Высота не может быть нулевой");
        }
        this.base = base;
        this.height = height;
    }

    public double volume() {
        return base.area() * height;
    }

    public String toString() {
        return "Цилиндр (основание: " + base.toString() + ", высота: " + height + ")";
    }
}