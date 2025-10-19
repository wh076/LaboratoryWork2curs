public class Main {
    public static void main(String[] args) {
        System.out.println("=== ВЫБЕРИТЕ ЗАДАНИЕ ===");
        System.out.println("1 - Задание 1 (Создание потоков)");
        System.out.println("2 - Задание 2 (Producer-Consumer)");

        // Запускаем оба задания по порядку
        runAssignment1();

    }

    // ЗАДАНИЕ 1 - Создание потоков
    private static void runAssignment1() {
        System.out.println("\n=== ЗАДАНИЕ 1 - СОЗДАНИЕ ПОТОКОВ ===");

        // Поток для чётных чисел (наследник Thread)
        class EvenThread extends Thread {
            @Override
            public void run() {
                for (int i = 2; i <= 10; i += 2) {
                    System.out.println("Чётный поток: " + i);
                    try { Thread.sleep(500); } catch (InterruptedException e) { return; }
                }
            }
        }

        // Поток для нечётных чисел (реализация Runnable)
        class OddRunnable implements Runnable {
            @Override
            public void run() {
                for (int i = 1; i <= 9; i += 2) {
                    System.out.println("Нечётный поток: " + i);
                    try { Thread.sleep(500); } catch (InterruptedException e) { return; }
                }
            }
        }

        Thread evenThread = new EvenThread();
        Thread oddThread = new Thread(new OddRunnable());

        evenThread.start();
        oddThread.start();

        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Оба потока завершили работу");
    }


}