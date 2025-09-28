import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Button button = new Button();
        Scanner scanner = new Scanner(System.in);

        System.out.println("click, введите 0 для выхода");

        while (true) {
            System.out.print(" ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                break;
            }
            button.click();
        }
    }
}
