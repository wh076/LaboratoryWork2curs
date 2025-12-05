package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("=== Запуск чат-приложения ===");

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== КОНСОЛЬНЫЙ ЧАТ ===");
        System.out.println("1. Запустить сервер");
        System.out.println("2. Запустить клиент");
        System.out.println("3. Тестовый режим (сервер + клиент)");
        System.out.println("4. Выход");
        System.out.print("Выберите опцию: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                startServer();
                break;
            case "2":
                startClient();
                break;
            case "3":
                startTestMode();
                break;
            case "4":
                System.out.println("Выход...");
                break;
            default:
                System.out.println("Неверный выбор");
        }

        scanner.close();
    }

    private static void startServer() {
        System.out.println("\n=== ЗАПУСК СЕРВЕРА ===");
        System.out.println("Сервер будет запущен на порту 8080");
        System.out.println("Для остановки нажмите Ctrl+C\n");

        ChatServer server = new ChatServer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nОстановка сервера...");
            server.stop();
        }));

        server.start();
    }

    private static void startClient() {
        System.out.println("\n=== ЗАПУСК КЛИЕНТА ===");
        System.out.println("Подключение к localhost:8080");

        ChatClient client = new ChatClient();
        client.start();
    }

    private static void startTestMode() {
        System.out.println("\n=== ТЕСТОВЫЙ РЕЖИМ ===");

        Thread serverThread = new Thread(() -> {
            System.out.println("Запуск тестового сервера...");
            ChatServer server = new ChatServer();
            server.start();
        });

        serverThread.setDaemon(true);
        serverThread.start();

        try {
            Thread.sleep(2000); // Даем время серверу запуститься

            System.out.println("Запуск тестового клиента...");
            ChatClient client = new ChatClient();
            client.start();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Тестовый режим прерван", e);
        }
    }
}