import java.util.List;
import java.util.Collections;
import java.util.ArrayList; // ДОБАВИТЬ ЭТУ СТРОКУ

public class PrimesGeneratorTest {

    public void testPrimesGenerator(int N) {
        System.out.println("Генерация первых " + N + " простых чисел");
        System.out.println("========================================");

        // Создаем генератор и получаем все простые числа
        PrimesGenerator generator = new PrimesGenerator(N);
        List<Integer> primes = generator.generateAll();

        // Выводим в прямом порядке
        System.out.println("Прямой порядок:");
        printNumbers(primes);

        // Выводим в обратном порядке
        System.out.println("Обратный порядок:");
        List<Integer> reversedPrimes = reverseList(primes);
        printNumbers(reversedPrimes);

        // Демонстрация работы итератора
        System.out.println("\nДемонстрация работы Iterator:");
        demonstrateIterator(5);
    }

    private void printNumbers(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i));
            if (i < numbers.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private List<Integer> reverseList(List<Integer> numbers) {
        List<Integer> reversed = new ArrayList<>(numbers); // ТЕПЕРЬ РАБОТАЕТ
        Collections.reverse(reversed);
        return reversed;
    }

    private void demonstrateIterator(int count) {
        PrimesGenerator generator = new PrimesGenerator(count);
        while (generator.hasNext()) {
            System.out.print(generator.next() + " ");
        }
        System.out.println();
    }
}