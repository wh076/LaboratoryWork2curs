public class Main {
    public static void main(String[] args) {
        Table table = new Table(3, 3);

        table.setValue(0, 0, 1);
        table.setValue(0, 1, 2);
        table.setValue(0, 2, 3);
        table.setValue(1, 0, 4);
        table.setValue(1, 1, 5);
        table.setValue(1, 2, 6);
        table.setValue(2, 0, 7);
        table.setValue(2, 1, 8);
        table.setValue(2, 2, 9);

        // Выводим таблицу
        System.out.println("таблица:");
        System.out.println(table.toString());

        // Получаем значение
        System.out.println("значение в [1,1]: " + table.getValue(1, 1));

        // Размеры таблицы
        System.out.println("строк: " + table.rows());
        System.out.println("столбцов: " + table.cols());

        // Среднее значение
        System.out.println("среднее: " + table.average());
    }
}