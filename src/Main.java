import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Button button = new Button();
        Scanner scanner = new Scanner(System.in);

        System.out.println("click");

        while (true) {
            System.out.print(" ");
            scanner.nextLine(); // ждет нажатия Enter

            button.click();
        }
    }
}

