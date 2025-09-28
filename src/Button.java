public class Button {
    private int clickCount = 0;

    public void click() {
        clickCount++;
        System.out.println("Кликов: " + clickCount);
    }

    public int getClickCount() {
        return clickCount;
    }
}
