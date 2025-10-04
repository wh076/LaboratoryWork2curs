import java.util.Objects;

// Класс Human представляет человека с именем, фамилией и возрастом
public class Human implements Comparable<Human> {
    // Поля класса (переменные, которые хранят данные)
    private String firstName;  // Имя
    private String lastName;   // Фамилия
    private int age;           // Возраст

    // Конструктор - специальный метод для создания объектов
    public Human(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Геттеры - методы для получения значений полей
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    // Реализация интерфейса Comparable - для сравнения объектов
    // Сравниваем сначала по фамилии, затем по имени
    @Override
    public int compareTo(Human other) {
        // Сначала сравниваем фамилии
        int lastNameCompare = this.lastName.compareTo(other.lastName);
        if (lastNameCompare != 0) {
            return lastNameCompare; // Если фамилии разные, возвращаем результат
        }
        // Если фамилии одинаковые, сравниваем имена
        return this.firstName.compareTo(other.firstName);
    }

    // Метод equals для сравнения объектов на равенство
    @Override
    public boolean equals(Object o) {
        // Если это тот же самый объект в памяти - они равны
        if (this == o) return true;
        // Если объект null или классы разные - не равны
        if (o == null || getClass() != o.getClass()) return false;

        // Приводим Object к Human
        Human human = (Human) o;

        // Два человека равны, если совпадают все поля
        return age == human.age &&
                Objects.equals(firstName, human.firstName) &&
                Objects.equals(lastName, human.lastName);
    }

    // Метод hashCode - необходим для корректной работы HashSet
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    // Метод toString - для красивого вывода объекта
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + age + " лет)";
    }
}