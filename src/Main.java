import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(" ");

        // 1. СОЗДАЕМ СПИСОК ЛЮДЕЙ
        System.out.println("1. СОЗДАЕМ СПИСОК ЛЮДЕЙ:");
        List<Human> humans = Arrays.asList(
                new Human("Иван", "Петров", 25),
                new Human("Мария", "Иванова", 30),
                new Human("Алексей", "Сидоров", 22),
                new Human("Ольга", "Петрова", 28),
                new Human("Иван", "Иванов", 35),
                new Human("Мария", "Петрова", 26)
        );

        // Выводим исходный список
        System.out.println("Исходный список (порядок добавления):");
        printCollection(humans);

        // 2. HASHSET - хранит элементы в произвольном порядке
        System.out.println("\n2. HASHSET:");
        System.out.println("Элементы хранятся в произвольном порядке (определяется хэш-функцией)");
        Set<Human> hashSet = new HashSet<>(humans);
        printCollection(hashSet);

        // 3. LINKEDHASHSET - сохраняет порядок добавления
        System.out.println("\n3. LINKEDHASHSET:");
        System.out.println("Элементы хранятся в порядке добавления");
        Set<Human> linkedHashSet = new LinkedHashSet<>(humans);
        printCollection(linkedHashSet);

        // 4. TREESET с Comparable - автоматическая сортировка
        System.out.println("\n4. TREESET (использует Comparable из класса Human):");
        System.out.println("Элементы автоматически сортируются (по фамилии, затем по имени)");
        Set<Human> treeSet = new TreeSet<>(humans);
        printCollection(treeSet);

        // 5. TREESET с компаратором по фамилии
        System.out.println("\n5. TREESET с компаратором HumanComparatorByLastName:");
        System.out.println("Элементы сортируются только по фамилии");
        Set<Human> treeSetByLastName = new TreeSet<>(new HumanComparator());
        treeSetByLastName.addAll(humans);
        printCollection(treeSetByLastName);

        // 6. TREESET с АНОНИМНЫМ компаратором по возрасту
        System.out.println("\n6. TREESET с АНОНИМНЫМ компаратором по возрасту:");
        System.out.println("Элементы сортируются по возрасту (используем анонимный класс)");

        // Анонимный компаратор - создаем класс прямо здесь, без имени
        Set<Human> treeSetByAge = new TreeSet<>(new Comparator<Human>() {
            // Переопределяем метод compare для сравнения по возрасту
            @Override
            public int compare(Human h1, Human h2) {
                // Сравниваем возраст двух людей
                return Integer.compare(h1.getAge(), h2.getAge());
            }
        });
        treeSetByAge.addAll(humans);
        printCollection(treeSetByAge);

    }

    // Метод для вывода любой коллекции на экран
    private static void printCollection(Collection<Human> collection) {
        int counter = 1;
        for (Human human : collection) {
            System.out.println(counter + ". " + human);
            counter++;
        }
    }
}