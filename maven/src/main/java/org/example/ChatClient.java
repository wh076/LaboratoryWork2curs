package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;
    private boolean isRunning;
    private String nickname;

    public ChatClient() {
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    public void start() {
        try {
            connectToServer();
            startMessageReader();
            startUserInterface();

        } catch (Exception e) {
            logger.error("Ошибка в клиенте", e);
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            stop();
        }
    }

    private void connectToServer() throws Exception {
        System.out.println("Подключение к серверу " + SERVER_ADDRESS + ":" + SERVER_PORT);
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Подключение установлено!");
    }

    private void startMessageReader() {
        Thread readerThread = new Thread(() -> {
            try {
                String message;
                while (isRunning && (message = reader.readLine()) != null) {
                    System.out.println(message);

                    // Сохраняем никнейм из приветственного сообщения
                    if (message.contains("Добро пожаловать,") && nickname == null) {
                        extractNickname(message);
                    }
                }
            } catch (Exception e) {
                if (isRunning) {
                    System.out.println("\nСоединение с сервером разорвано");
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    private void extractNickname(String welcomeMessage) {
        // Ищем никнейм в сообщении "Добро пожаловать, Иван!"
        if (welcomeMessage.contains("Добро пожаловать,")) {
            String[] parts = welcomeMessage.split(",");
            if (parts.length > 1) {
                nickname = parts[1].trim().replace("!", "");
                logger.info("Никнейм определен: {}", nickname);
            }
        }
    }

    private void startUserInterface() {
        System.out.println("\n=== КОНСОЛЬНЫЙ ЧАТ КЛИЕНТ ===");
        System.out.println("Ожидание регистрации... (следуйте инструкциям сервера)");
        System.out.println("\nДоступные команды (после регистрации):");
        System.out.println("  /help - справка");
        System.out.println("  /users - список пользователей");
        System.out.println("  /pm <ник> <сообщение> - личное сообщение");
        System.out.println("  /broadcast <сообщение> - сообщение всем");
        System.out.println("  /stats - статистика сервера");
        System.out.println("  /exit - выход");
        System.out.println("==============================\n");

        while (isRunning) {
            try {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input == null || input.trim().isEmpty()) {
                    continue;
                }

                if ("/exit".equalsIgnoreCase(input.trim())) {
                    writer.println("/exit");
                    isRunning = false;
                    break;
                }

                writer.println(input);

            } catch (Exception e) {
                if (isRunning) {
                    System.err.println("Ошибка ввода: " + e.getMessage());
                }
            }
        }
    }

    public void stop() {
        isRunning = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Клиент отключен");
            logger.info("Клиент остановлен");
        } catch (Exception e) {
            logger.error("Ошибка при остановке клиента", e);
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}