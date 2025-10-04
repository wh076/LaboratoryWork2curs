import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public final class Collection {

    private static final int UPPER_BOUND = 101;
    private static final Random RANDOM = new Random();
    
    private Collection() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }
    
    // 1. Создаем ArrayList случайных чисел
    public static List<Integer> createRandomList(int size) {
        if (size < 0) {
            return new ArrayList<>();
        }
        
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // sonarignore: start
            list.add(RANDOM.nextInt(UPPER_BOUND)); // числа от 0 до 100
            // sonarignore: end
        }
        return list;
    }

    // 2. Уникальные элементы
    public static List<Integer> getUniqueElements(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Integer> unique = new ArrayList<>();
        for (Integer num : list) {
            if (!unique.contains(num)) { // если еще нет в списке - добавляем
                unique.add(num);
            }
        }
        return unique;
    }

    // 3. Дублирующиеся элементы
    public static List<Integer> getDuplicateElements(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int currentNum = list.get(i);
            // Проверяем, есть ли это число где-то еще в списке
            for (int j = i + 1; j < list.size(); j++) {
                if (currentNum == list.get(j) && !duplicates.contains(currentNum)) {
                    duplicates.add(currentNum);
                    break; // нашли повтор, выходим из внутреннего цикла
                }
            }
        }
        return duplicates;
    }

    // 4. из списка в массив
    public static int[] listToArray(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new int[0];
        }
        
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    // 5. Подсчет повторений
    public static void printFrequency(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("   Список пуст");
            return;
        }
        
        // Просто идем по всем числам от 0 до 100 и считаем
        for (int number = 0; number < UPPER_BOUND; number++) {
            int count = 0;
            for (Integer num : list) {
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
