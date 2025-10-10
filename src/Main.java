
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Парсим JSON с помощью Gson
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/books.json");
            Type visitorListType = new TypeToken<List<Visitor>>(){}.getType();
            List<Visitor> visitors = gson.fromJson(reader, visitorListType);

            // ЗАДАНИЕ 1: Список посетителей и количество (Stream API, способ работать с коллекциями в Java (списками, массивами и т.д.))
            System.out.println("ЗАДАНИЕ 1");
            System.out.println("Список посетителей:");
            visitors.stream()
                    .map(v -> v.getName() + " " + v.getSurname())
                    .forEach(System.out::println);
            System.out.println("Количество посетителей: " + visitors.size());

            // ЗАДАНИЕ 2: Уникальные книги (Stream API)
            System.out.println("\nЗАДАНИЕ 2");
            List<Book> uniqueBooks = visitors.stream()
                    .flatMap(v -> v.getFavoriteBooks().stream())
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(Book::getName, b -> b, (b1, b2) -> b1),
                            map -> new ArrayList<>(map.values())
                    ));

            System.out.println("Уникальные книги:");
            uniqueBooks.forEach(b -> System.out.println("- " + b.getName() + " (" + b.getAuthor() + ")"));
            System.out.println("Количество книг без повторений: " + uniqueBooks.size());

            // ЗАДАНИЕ 3: Сортировка по году (Stream API)
            System.out.println("\nЗАДАНИЕ 3");
            System.out.println("Книги отсортированные по году издания:");
            uniqueBooks.stream()
                    .sorted(Comparator.comparingInt(Book::getPublishingYear))
                    .forEach(b -> System.out.println("- " + b.getPublishingYear() + ": " + b.getName()));

            // ЗАДАНИЕ 4: Поиск Jane Austen (Stream API)
            System.out.println("\nЗАДАНИЕ 4");
            boolean hasJaneAusten = visitors.stream()
                    .flatMap(v -> v.getFavoriteBooks().stream())
                    .anyMatch(b -> "Jane Austen".equals(b.getAuthor()));
            System.out.println("Есть ли книги Jane Austen: " + (hasJaneAusten ? "да" : "нет"));

            // ЗАДАНИЕ 5: Максимум книг (Stream API)
            System.out.println("\nЗАДАНИЕ 5");
            int maxBooks = visitors.stream()
                    .mapToInt(v -> v.getFavoriteBooks().size())
                    .max()
                    .orElse(0);
            System.out.println("Максимальное количество книг у одного посетителя: " + maxBooks);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

