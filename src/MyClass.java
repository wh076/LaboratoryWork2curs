public class MyClass {
    private String name = "TestObject";
    private int value = 100;

    public void publicMethod1() {
        System.out.println("Public method 1 called");
    }

    public String publicMethod2(String input) {
        return "Public: " + input;
    }

    public int publicMethod3(int x, int y) {
        return x + y;
    }

    @Repeat(times = 2)
    protected void protectedMethod1() {
        System.out.println("Protected method 1 - value: " + value);
    }

    @Repeat(times = 3)
    protected String protectedMethod2(String prefix) {
        String result = prefix + "_protected";
        System.out.println("Protected method 2: " + result);
        return result;
    }

    @Repeat(times = 4)
    private void privateMethod1() {
        System.out.println("Private method 1 called");
    }

    @Repeat(times = 2)
    private String privateMethod2(String text, int number) {
        String result = text + " - " + number;
        System.out.println("Private method 2: " + result);
        return result;
    }

    @Repeat(times = 1)
    private void privateMethod3(boolean flag) {
        System.out.println("Private method 3 - flag: " + flag);
    }

    private void normalPrivateMethod() {
        System.out.println("This won't be called");
    }
}