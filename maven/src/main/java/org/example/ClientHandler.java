package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private static final Logger chatLogger = LoggerFactory.getLogger("chat");
    private static final Logger errorLogger = LoggerFactory.getLogger("errors");

    private final Socket socket;
    private final ChatServer server;
    private final String clientInfo;
    private BufferedReader reader;
    private PrintWriter writer;
    private String nickname;
    private boolean isRegistered;

    // Паттерны для команд
    private static final Pattern PRIVATE_MSG_PATTERN =
            Pattern.compile("^/pm\\s+(\\w+)\\s+(.+)$");
    private static final Pattern BROADCAST_PATTERN =
            Pattern.compile("^/broadcast\\s+(.+)$");

    public ClientHandler(Socket socket, ChatServer server, String clientInfo) {
        this.socket = socket;
        this.server = server;
        this.clientInfo = clientInfo;
        this.isRegistered = false;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            logger.info("Обработка нового клиента: {}", clientInfo);

            // Этап 1: Регистрация
            if (!registerUser()) {
                logger.warn("Регистрация не удалась для клиента: {}", clientInfo);
                return;
            }

            // Этап 2: Приветствие и инструкции
            sendWelcomeMessage();

            // Этап 3: Основной цикл обработки сообщений
            processMessages();

        } catch (IOException e) {
            errorLogger.error("Ошибка в ClientHandler для клиента {}", clientInfo, e);
        } finally {
            closeConnection();
        }
    }

    private boolean registerUser() throws IOException {
        writer.println("=== ДОБРО ПОЖАЛОВАТЬ В ЧАТ ===");
        writer.println("Введите ваш никнейм (только буквы и цифры, без пробелов):");

        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            nickname = reader.readLine();

            if (nickname == null) {
                logger.warn("Клиент отключился во время регистрации: {}", clientInfo);
                return false;
            }

            nickname = nickname.trim();

            // Проверка на пустой никнейм
            if (nickname.isEmpty()) {
                writer.println("Никнейм не может быть пустым. Попробуйте снова:");
                attempts++;
                continue;
            }

            // Проверка на допустимые символы
            if (!nickname.matches("^[a-zA-Z0-9_]+$")) {
                writer.println("Никнейм может содержать только буквы, цифры и подчеркивания. Попробуйте снова:");
                attempts++;
                continue;
            }

            // Проверка на уникальность
            if (server.userExists(nickname)) {
                writer.println("Никнейм '" + nickname + "' уже занят. Выберите другой:");
                attempts++;
                continue;
            }

            // Регистрация успешна
            User user = new User(nickname, writer, clientInfo);
            if (server.registerUser(nickname, user, this)) {
                isRegistered = true;
                logger.info("Успешная регистрация: {}", nickname);
                return true;
            } else {
                writer.println("Ошибка регистрации. Попробуйте снова:");
                attempts++;
            }
        }

        writer.println("Превышено количество попыток регистрации. Соединение закрыто.");
        return false;
    }

    private void sendWelcomeMessage() {
        writer.println("\n=== РЕГИСТРАЦИЯ УСПЕШНА ===");
        writer.println("Добро пожаловать, " + nickname + "!");
        writer.println("Сейчас онлайн: " + server.getOnlineCount() + " пользователь(ей)");
        writer.println("\n=== КОМАНДЫ ЧАТА ===");
        writer.println("/help - показать эту справку");
        writer.println("/users - список пользователей онлайн");
        writer.println("/pm <ник> <сообщение> - личное сообщение");
        writer.println("/broadcast <сообщение> - сообщение всем");
        writer.println("/stats - статистика сервера");
        writer.println("/exit - выход из чата");
        writer.println("========================\n");
    }

    private void processMessages() throws IOException {
        String message;
        while ((message = reader.readLine()) != null) {
            if (!isRegistered) {
                break;
            }

            // Логируем входящее сообщение
            logger.debug("[{}] прислал: {}", nickname, message);

            // Обработка команд
            if (message.startsWith("/")) {
                if (!processCommand(message)) {
                    break; // Команда /exit
                }
            } else {
                // Обычное сообщение - отправляем как broadcast
                server.broadcastMessage(nickname, message);
            }
        }
    }

    private boolean processCommand(String command) {
        command = command.trim();

        switch (command.toLowerCase()) {
            case "/exit":
                writer.println("До свидания, " + nickname + "!");
                logger.info("Пользователь {} запросил выход", nickname);
                return false;

            case "/help":
                sendHelp();
                return true;

            case "/users":
                writer.println(server.getUsersList());
                return true;

            case "/stats":
                writer.println(server.getStats());
                return true;

            default:
                // Проверяем команды с параметрами
                if (command.startsWith("/pm ")) {
                    return processPrivateMessage(command);
                } else if (command.startsWith("/broadcast ")) {
                    return processBroadcastMessage(command);
                } else {
                    writer.println("Неизвестная команда. Введите /help для списка команд.");
                    return true;
                }
        }
    }

    private void sendHelp() {
        writer.println("\n=== СПРАВКА ПО КОМАНДАМ ===");
        writer.println("/help - показать эту справку");
        writer.println("/users - показать список пользователей онлайн");
        writer.println("/pm <никнейм> <сообщение> - отправить личное сообщение");
        writer.println("  Пример: /pm Иван Привет, как дела?");
        writer.println("/broadcast <сообщение> - отправить сообщение всем");
        writer.println("  Пример: /broadcast Всем привет!");
        writer.println("/stats - показать статистику сервера");
        writer.println("/exit - выйти из чата");
        writer.println("============================\n");
    }

    private boolean processPrivateMessage(String command) {
        Matcher matcher = PRIVATE_MSG_PATTERN.matcher(command);
        if (!matcher.matches()) {
            writer.println("Неверный формат команды. Используйте: /pm <никнейм> <сообщение>");
            return true;
        }

        String recipient = matcher.group(1);
        String message = matcher.group(2);

        if (recipient.equalsIgnoreCase(nickname)) {
            writer.println("Нельзя отправлять сообщение самому себе!");
            return true;
        }

        boolean success = server.sendPrivateMessage(nickname, recipient, message);
        if (!success) {
            writer.println("Пользователь '" + recipient + "' не найден или не в сети.");
        }

        return true;
    }

    private boolean processBroadcastMessage(String command) {
        Matcher matcher = BROADCAST_PATTERN.matcher(command);
        if (!matcher.matches()) {
            writer.println("Неверный формат команды. Используйте: /broadcast <сообщение>");
            return true;
        }

        String message = matcher.group(1);
        server.broadcastMessage(nickname, message);
        return true;
    }

    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }

    public void closeConnection() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            if (isRegistered && nickname != null) {
                server.removeUser(nickname);
                logger.info("Соединение закрыто: {}", nickname);
            }

        } catch (IOException e) {
            errorLogger.error("Ошибка при закрытии соединения для клиента {}", clientInfo, e);
        }
    }

    // Геттеры
    public String getNickname() {
        return nickname;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}