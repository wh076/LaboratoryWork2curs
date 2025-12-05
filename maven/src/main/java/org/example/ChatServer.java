package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    // Основной логгер для сервера
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    // Логгер для сообщений чата
    private static final Logger chatLogger = LoggerFactory.getLogger("chat");
    // Логгер для ошибок
    private static final Logger errorLogger = LoggerFactory.getLogger("errors");

    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private volatile boolean isRunning;

    // Храним подключенных пользователей: никнейм -> User
    private final Map<String, User> connectedUsers = new ConcurrentHashMap<>();
    // Храним обработчики клиентов: никнейм -> ClientHandler
    private final Map<String, ClientHandler> clientHandlers = new ConcurrentHashMap<>();

    public ChatServer() {
        this.threadPool = Executors.newCachedThreadPool();
        this.isRunning = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            logger.info("=== Сервер запущен на порту {} ===", PORT);
            logger.info("Ожидание подключений...");
            chatLogger.info("Сервер запущен");

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    String clientInfo = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                    logger.info("Новое подключение: {}", clientInfo);

                    ClientHandler clientHandler = new ClientHandler(clientSocket, this, clientInfo);
                    threadPool.execute(clientHandler);

                } catch (IOException e) {
                    if (isRunning) {
                        errorLogger.error("Ошибка при принятии подключения", e);
                    }
                }
            }
        } catch (IOException e) {
            errorLogger.error("Ошибка при запуске сервера", e);
        } finally {
            stop();
        }
    }

    // Регистрация нового пользователя
    public synchronized boolean registerUser(String nickname, User user, ClientHandler handler) {
        if (connectedUsers.containsKey(nickname)) {
            return false; // Никнейм уже занят
        }

        connectedUsers.put(nickname, user);
        clientHandlers.put(nickname, handler);

        logger.info("Пользователь зарегистрирован: {}", nickname);
        logger.info("Всего пользователей онлайн: {}", connectedUsers.size());
        chatLogger.info("Пользователь подключился: {}", nickname);

        // Уведомляем всех о новом пользователе
        broadcastSystemMessage(nickname + " присоединился к чату");
        return true;
    }

    // Удаление пользователя
    public synchronized void removeUser(String nickname) {
        if (connectedUsers.remove(nickname) != null) {
            clientHandlers.remove(nickname);
            logger.info("Пользователь отключен: {}", nickname);
            logger.info("Осталось пользователей онлайн: {}", connectedUsers.size());
            chatLogger.info("Пользователь отключился: {}", nickname);

            // Уведомляем всех об отключении
            broadcastSystemMessage(nickname + " покинул чат");
        }
    }

    // Получение пользователя по никнейму
    public User getUser(String nickname) {
        return connectedUsers.get(nickname);
    }

    // Получение обработчика клиента по никнейму
    public ClientHandler getClientHandler(String nickname) {
        return clientHandlers.get(nickname);
    }

    // Получение списка всех пользователей
    public String getUsersList() {
        if (connectedUsers.isEmpty()) {
            return "Сейчас нет других пользователей онлайн";
        }

        StringBuilder sb = new StringBuilder("=== Пользователи онлайн (" + connectedUsers.size() + ") ===\n");
        int i = 1;
        for (String nickname : connectedUsers.keySet()) {
            sb.append(i).append(". ").append(nickname).append("\n");
            i++;
        }
        return sb.toString();
    }

    // Широковещательное сообщение всем пользователям
    public void broadcastMessage(String fromNickname, String message) {
        String formattedMessage = String.format("[%s -> ALL]: %s", fromNickname, message);
        logger.info("Broadcast от {}: {}", fromNickname, message);
        chatLogger.info(formattedMessage);

        for (User user : connectedUsers.values()) {
            try {
                user.getWriter().println(formattedMessage);
            } catch (Exception e) {
                errorLogger.error("Ошибка отправки сообщения пользователю", e);
            }
        }
    }

    // Системное сообщение всем
    private void broadcastSystemMessage(String message) {
        String formattedMessage = "[SYSTEM]: " + message;
        for (User user : connectedUsers.values()) {
            try {
                user.getWriter().println(formattedMessage);
            } catch (Exception e) {
                errorLogger.error("Ошибка отправки системного сообщения", e);
            }
        }
    }

    // Личное сообщение
    public boolean sendPrivateMessage(String fromNickname, String toNickname, String message) {
        User recipient = connectedUsers.get(toNickname);
        if (recipient == null) {
            return false; // Получатель не найден
        }

        String formattedMessage = String.format("[%s -> %s]: %s", fromNickname, toNickname, message);
        logger.info("Private от {} к {}: {}", fromNickname, toNickname, message);
        chatLogger.info(formattedMessage);

        try {
            // Отправляем получателю
            recipient.getWriter().println(formattedMessage);

            // Отправляем отправителю (чтобы он видел свое сообщение)
            User sender = connectedUsers.get(fromNickname);
            if (sender != null) {
                sender.getWriter().println(formattedMessage);
            }

            return true;
        } catch (Exception e) {
            errorLogger.error("Ошибка отправки личного сообщения", e);
            return false;
        }
    }

    // Проверка, существует ли пользователь
    public boolean userExists(String nickname) {
        return connectedUsers.containsKey(nickname);
    }

    // Получение количества пользователей
    public int getOnlineCount() {
        return connectedUsers.size();
    }

    // Остановка сервера
    public void stop() {
        isRunning = false;

        // Отправляем сообщение всем о закрытии сервера
        broadcastSystemMessage("Сервер останавливается...");

        // Закрываем все соединения
        for (ClientHandler handler : clientHandlers.values()) {
            handler.closeConnection();
        }

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            errorLogger.error("Ошибка при закрытии ServerSocket", e);
        }

        threadPool.shutdown();
        logger.info("Сервер остановлен");
        chatLogger.info("Сервер остановлен");
    }

    // Получение статистики
    public String getStats() {
        return String.format("Сервер работает. Пользователей онлайн: %d", connectedUsers.size());
    }
}