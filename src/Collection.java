import java.util.*;

public class Collection {

    // 1. Создаем Vector случайных чисел
    public static Vector<Integer> createRandomVector(int size) {
        Vector<Integer> vector = new Vector<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            vector.add(random.nextInt(101)); // числа от 0 до 100
        }
        return vector;
    }

    // 2. Уникальные элементы
    public static Vector<Integer> getUniqueElements(Vector<Integer> vector) {
        Vector<Integer> unique = new Vector<>();
        for (Integer num : vector) {
            if (!unique.contains(num)) { // если еще нет в списке - добавляем
                unique.add(num);
            }
        }
        return unique;
    }

    // 3. Дублирующиеся элементы
    public static Vector<Integer> getDuplicateElements(Vector<Integer> vector) {
        Vector<Integer> duplicates = new Vector<>();
        for (int i = 0; i < vector.size(); i++) {
            int currentNum = vector.get(i);
            // Проверяем, есть ли это число где-то еще в списке
            for (int j = i + 1; j < vector.size(); j++) {
                if (currentNum == vector.get(j) && !duplicates.contains(currentNum)) {
                    duplicates.add(currentNum);
                    break; // нашли повтор, выходим из внутреннего цикла
                }
            }
        }
        return duplicates;
    }

    // 4. из вектора в массив
    public static int[] vectorToArray(Vector<Integer> vector) {
        int[] array = new int[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            array[i] = vector.get(i);
        }
        return array;
    }

    // 5. Подсчет повторений
    public static void printFrequency(Vector<Integer> vector) {
        // Просто идем по всем числам от 0 до 100 и считаем
        for (int number = 0; number <= 100; number++) {
            int count = 0;
            for (Integer num : vector) {
                if (num == number) {
                    count++;
                }
            }
            if (count > 0) { // если число встречалось хоть раз
                System.out.println("   Число " + number + ": " + count + " раз(а)");
            }
        }
    }
}