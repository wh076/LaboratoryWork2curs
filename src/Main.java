import lsp.*;
import ocp.*;
import srp.ReportManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        System.out.println("1. ПРИНЦИП SRP (Единственной ответственности):");
        ReportManager manager = new ReportManager(List.of(5, 10, 15, 20));
        manager.generateReport();
        System.out.println();


        System.out.println("2. ПРИНЦИП OCP (Открытости/закрытости):");
        DiscountCalculator calculator = new DiscountCalculator();
        System.out.println("Regular скидка (1000 руб): " + calculator.calculateDiscount(new RegularDiscount(), 1000) + " руб");
        System.out.println("VIP скидка (1000 руб): " + calculator.calculateDiscount(new VIPDiscount(), 1000) + " руб");
        System.out.println("Super VIP скидка (1000 руб): " + calculator.calculateDiscount(new SuperVIPDiscount(), 1000) + " руб");
        System.out.println("Student скидка (1000 руб): " + calculator.calculateDiscount(new StudentDiscount(), 1000) + " руб");
        System.out.println();


        System.out.println("3. ПРИНЦИП LSP (Подстановки Лисков):");

        System.out.println("Летающая птица (Sparrow):");
        displayFlyingBird(new Sparrow());

        System.out.println("\nНелетающая птица (Penguin):");
        displayNonFlyingBird(new Penguin());

        System.out.println("\nРЕФАКТОРИНГ ЗАВЕРШЕН УСПЕШНО");
        System.out.println("Все принципы SOLID соблюдены:");
        System.out.println("- SRP: Каждый класс имеет одну ответственность");
        System.out.println("- OCP: Система расширяется без изменения существующего кода");
        System.out.println("- LSP: Подклассы полностью заменяемы с базовыми классами");
    }

    public static void displayFlyingBird(FlyingBird bird) {
        bird.eat();
        bird.fly();
    }

    public static void displayNonFlyingBird(NonFlyingBird bird) {
        bird.eat();
        bird.swim();
    }
}

