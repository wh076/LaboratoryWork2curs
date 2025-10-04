import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public final class Main {

    private Main() {
        // Private constructor
    }

    public static void main(String[] args) {
        System.out.println("=== ВЫПОЛНЕНИЕ ЗАДАНИЯ С ARRAYLIST ===\n");

        // 1. Создаем ArrayList из n случайных чисел от 0 до 100
        int n = 10;
        List<Integer> list = Collection.createRandomList(n);
        System.out.println("1. Array из " + n + " случайных чисел: " + list);

        // 2. ArrayList уже создан (пропускаем преобразование из массива)
        System.out.println("2. ArrayList из " + n + " случайных чисел: " + list);

        // 3. Отсортируйте ArrayList по возрастанию
        Collections.sort(list);
        System.out.println("3. После сортировки по возрастанию: " + list);

        // 4. Отсортируйте ArrayList в обратном порядке
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("4. После сортировки в обратном порядке: " + list);

        // 5. Перемешайте ArrayList
        Collections.shuffle(list);
        System.out.println("5. После перемешивания: " + list);

        // 6. Выполните циклический сдвиг на 1 элемент
        Collections.rotate(list, 1);
        System.out.println("6. После циклического сдвига на 1: " + list);

        // 7. Оставьте в ArrayList только уникальные элементы
        List<Integer> uniqueList = Collection.getUniqueElements(list);
        System.out.println("7. Только уникальные элементы: " + uniqueList);

        // 8. Оставляем в ArrayList только дублирующиеся элементы
        List<Integer> testList = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 4, 4, 4, 5));
        List<Integer> duplicates = Collection.getDuplicateElements(testList);
        System.out.println("8. Только дублирующиеся элементы: " + duplicates);

        // 9. Из ArrayList получите массив (нужное преобразование)
        int[] array = Collection.listToArray(list);
        System.out.println("9. Массив из ArrayList: " + Arrays.toString(array));

        // 10. Подсчитайте количество вхождений каждого числа
        System.out.println("10. Количество вхождений каждого числа:");
        Collection.printFrequency(list);
    }
}