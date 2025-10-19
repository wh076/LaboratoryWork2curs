public class Main {
    public static void main(String[] args) {
        System.out.println("Задание 1 (Создание потоков)");
        System.out.println("Задание 2 (Producer-Consumer)");

        runAssignment1();
        runAssignment2();
    }

    // ЗАДАНИЕ 1 - Создание потоков
    private static void runAssignment1() {
        System.out.println("\nЗАДАНИЕ 1 - СОЗДАНИЕ ПОТОКОВ");

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

    // ЗАДАНИЕ 2 - Producer-Consumer
    private static void runAssignment2() {
        System.out.println("\nЗАДАНИЕ 2 - PRODUCER-CONSUMER (СКЛАД ОБУВИ)");

        // Класс Order - заказ
        class Order {
            private final int orderId;
            private final String shoeType;
            private final int quantity;

            public Order(int orderId, String shoeType, int quantity) {
                this.orderId = orderId;
                this.shoeType = shoeType;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return "Order{id=" + orderId + ", type='" + shoeType + "', quantity=" + quantity + "}";
            }
        }

        // Класс ShoeWarehouse - склад
        class ShoeWarehouse {
            private final java.util.Queue<Order> orders = new java.util.LinkedList<>();
            private final int capacity;

            public ShoeWarehouse(int capacity) {
                this.capacity = capacity;
            }

            public synchronized void receiveOrder(Order order) {
                while (orders.size() >= capacity) {
                    try { wait(); } catch (InterruptedException e) { return; }
                }
                orders.offer(order);
                System.out.println("Добавлен заказ: " + order + " | Всего: " + orders.size());
                notifyAll();
            }

            public synchronized Order fulfillOrder() {
                while (orders.isEmpty()) {
                    try { wait(); } catch (InterruptedException e) { return null; }
                }
                Order order = orders.poll();
                System.out.println("Обработан заказ: " + order + " | Осталось: " + orders.size());
                notifyAll();
                return order;
            }
        }

        // Класс Producer - производитель
        class Producer implements Runnable {
            private final ShoeWarehouse warehouse;
            private final int orderCount;

            public Producer(ShoeWarehouse warehouse, int orderCount) {
                this.warehouse = warehouse;
                this.orderCount = orderCount;
            }

            @Override
            public void run() {
                java.util.List<String> productTypes = java.util.List.of("Кроссовки", "Туфли", "Ботинки", "Сапоги", "Сандалии");

                for (int i = 1; i <= orderCount; i++) {
                    String shoeType = productTypes.get(i % productTypes.size());
                    Order order = new Order(i, shoeType, (i % 5) + 1);
                    warehouse.receiveOrder(order);
                    try { Thread.sleep(200); } catch (InterruptedException e) { return; }
                }
                System.out.println("Producer завершил работу");
            }
        }

        // Класс Consumer - потребитель
        class Consumer implements Runnable {
            private final ShoeWarehouse warehouse;
            private final String name;
            private final int ordersToProcess;

            public Consumer(ShoeWarehouse warehouse, String name, int ordersToProcess) {
                this.warehouse = warehouse;
                this.name = name;
                this.ordersToProcess = ordersToProcess;
            }

            @Override
            public void run() {
                for (int i = 0; i < ordersToProcess; i++) {
                    Order order = warehouse.fulfillOrder();
                    if (order != null) {
                        System.out.println(name + " обработал: " + order);
                        try { Thread.sleep(300); } catch (InterruptedException e) { return; }
                    }
                }
                System.out.println(name + " завершил работу");
            }
        }

        ShoeWarehouse warehouse = new ShoeWarehouse(5);
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(4);

        executor.execute(new Producer(warehouse, 15));
        executor.execute(new Consumer(warehouse, "Consumer-1", 5));
        executor.execute(new Consumer(warehouse, "Consumer-2", 5));
        executor.execute(new Consumer(warehouse, "Consumer-3", 5));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Работа склада завершена");
    }
}