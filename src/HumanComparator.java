import java.util.Comparator;

// Класс-компаратор для сравнения людей только по фамилии
// Реализует интерфейс Comparator<Human>
public class HumanComparator implements Comparator<Human> {

    // Метод compare сравнивает двух людей
    @Override
    public int compare(Human h1, Human h2) {
        // Сравниваем только фамилии, используя встроенный метод compareTo для строк
        return h1.getLastName().compareTo(h2.getLastName());
    }
}