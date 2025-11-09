package ocp;

public class SuperVIPDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.2;
    }
}