import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PrimesGenerator implements Iterator<Integer> {
    private final int count;
    private int generated;
    private int currentNumber;

    public PrimesGenerator(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Количество чисел должно быть положительным");
        }
        this.count = N;
        this.generated = 0;
        this.currentNumber = 2;
    }

    @Override
    public boolean hasNext() {
        return generated < count;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Больше нет простых чисел");
        }

        while (true) {
            if (isPrime(currentNumber)) {
                int prime = currentNumber;
                currentNumber++;
                generated++;
                return prime;
            }
            currentNumber++;
        }
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> generateAll() {
        List<Integer> primes = new ArrayList<>();
        while (this.hasNext()) {
            primes.add(this.next());
        }
        return primes;
    }
}