import java.nio.file.*;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileSystemManager {

    public static void createTestStructure() throws IOException {
        // Создаем основную директорию TROFIMOV
        Path mainDir = Paths.get("Trofimov");
        Files.createDirectories(mainDir);
        System.out.println("Создана директория: " + mainDir.toAbsolutePath());

        // Создаем файл в основной директории
        Path mainFile = mainDir.resolve("Trofim.txt");
        Files.writeString(mainFile, "Это файл Трофима\nСоздан через Java NIO");
        System.out.println("Создан файл: " + mainFile);

        // Создаем вложенные директории
        Path deepDir = mainDir.resolve("dir1/dir2/dir3");
        Files.createDirectories(deepDir);
        System.out.println("Созданы вложенные директории: " + deepDir);

        // Копируем файл во вложенную директорию
        Path copiedFile = deepDir.resolve("Trofim_copy.txt");
        Files.copy(mainFile, copiedFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Файл скопирован в: " + copiedFile);

        // Создаем еще несколько тестовых файлов
        Files.writeString(deepDir.resolve("test1.txt"), "Тестовый файл 1");
        Files.writeString(deepDir.resolve("test2.txt"), "Тестовый файл 2");
        Files.writeString(mainDir.resolve("data.txt"), "Данные");
    }

    public static void recursiveWalk() throws IOException {
        System.out.println("\n=== РЕКУРСИВНЫЙ ОБХОД ДИРЕКТОРИИ Trofimov ===");

        Files.walk(Paths.get("Trofimov"))
                .forEach(path -> {
                    if (Files.isDirectory(path)) {
                        System.out.println("ДИРЕКТОРИЯ: " + path);
                    } else {
                        System.out.println("ФАЙЛ: " + path + " (размер: " + getFileSize(path) + " байт)");
                    }
                });
    }

    public static void recursiveFileOperations() throws IOException {
        System.out.println("\n=== ОПЕРАЦИИ С ФАЙЛАМИ ===");

        List<Path> textFiles = new ArrayList<>();

        // Рекурсивный поиск всех .txt файлов
        Files.walkFileTree(Paths.get("Trofimov"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".txt")) {
                    textFiles.add(file);
                    System.out.println("Найден txt файл: " + file.getFileName());

                    try {
                        String content = Files.readString(file);
                        System.out.println("  Содержимое: " + content.substring(0, Math.min(20, content.length())) + "...");//больше 20 символов пишет "..."
                    } catch (IOException e) {
                        System.out.println("  Ошибка чтения файла");
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Всего найдено txt файлов: " + textFiles.size());
    }

    private static String getFileSize(Path path) {
        try {
            return String.valueOf(Files.size(path));
        } catch (IOException e) {
            return "?";
        }
    }

    public static void main(String[] args) {
        try {
            createTestStructure();
            recursiveWalk();
            recursiveFileOperations();

        } catch (IOException e) {
            System.out.println("Ошибка файловой системы: " + e.getMessage());
        }
    }
}