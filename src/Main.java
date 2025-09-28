public class Main {
    public static void main(String[] args) {
        OddEvenSeparator separator = new OddEvenSeparator();

        separator.addNumber(1);
        separator.addNumber(2);
        separator.addNumber(3);
        separator.addNumber(4);

        System.out.print("четные: ");
        separator.even();

        System.out.print("нечетные: ");
        separator.odd();
    }
}