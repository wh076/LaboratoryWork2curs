class Button {
    int clickCount = 0;

    public void click() {
        clickCount++;
        System.out.println("Клик #" + clickCount);
    }
}