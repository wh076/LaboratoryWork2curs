import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Balance balance = new Balance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("левая чаша:");
        int left = scanner.nextInt();
        balance.addLeft(left);

        System.out.println("правая чаша:");
        int right = scanner.nextInt();
        balance.addRight(right);

        String result = balance.result();
        System.out.println(result);

        scanner.close();
    }
}