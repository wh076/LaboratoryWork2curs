package lsp;

public class Sparrow extends FlyingBird {
    @Override
    public void eat() {
        System.out.println("Чирик-чирик, клюю семечки, чирик-чирик");
    }

    @Override
    public void fly() {
        System.out.println("Чирик-чирик, я лечу, чирик-чирик");
    }
}