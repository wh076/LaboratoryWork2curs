import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ВЫПОЛНЕНИЕ ЗАДАНИЯ С VECTOR ===\n");

        // 1. Создаем Vector из N случайных чисел от 0 до 100
        int N = 10;
        Vector<Integer> vector = Collection.createRandomVector(N);
        System.out.println("1. Array из " + N + " случайных чисел: " + vector);

        // 2. Vector уже создан (пропускаем преобразование из массива)
        System.out.println("2. Vector из " + N + " случайных чисел: " + vector);

        // 3. Отсортируйте Vector по возрастанию
        Collections.sort(vector);
        System.out.println("3. После сортировки по возрастанию: " + vector);

        // 4. Отсортируйте Vector в обратном порядке
        Collections.sort(vector, Collections.reverseOrder());
        System.out.println("4. После сортировки в обратном порядке: " + vector);

        // 5. Перемешайте Vector
        Collections.shuffle(vector);
        System.out.println("5. После перемешивания: " + vector);

        // 6. Выполните циклический сдвиг на 1 элемент
        Collections.rotate(vector, 1);
        System.out.println("6. После циклического сдвига на 1: " + vector);

        // 7. Оставьте в Vector только уникальные элементы
        Vector<Integer> uniqueVector = Collection.getUniqueElements(vector);
        System.out.println("7. Только уникальные элементы: " + uniqueVector);

        // 8. Оставляем в Vector только дублирующиеся элементы
        Vector<Integer> testVector = new Vector<>(Arrays.asList(1, 2, 2, 3, 4, 4, 4, 5));
        Vector<Integer> duplicates = Collection.getDuplicateElements(testVector);
        System.out.println("8. Только дублирующиеся элементы: " + duplicates);

        // 9. Из Vector получите массив (нужное преобразование)
        int[] array = Collection.vectorToArray(vector);
        System.out.println("9. Массив из Vector: " + Arrays.toString(array));

        // 10. Подсчитайте количество вхождений каждого числа
        System.out.println("10. Количество вхождений каждого числа:");
        Collection.printFrequency(vector);
    }
}