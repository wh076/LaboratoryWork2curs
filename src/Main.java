public class Main {
    public static void main(String[] args) {
        // Текст для анализа
        String text = "The quick brown fox jumps over the lazy dog. " +
                "The dog was lazy but the fox was quick. " +
                "Quick and brown, the fox jumps again.";

        // Создаем счетчик слов
        WordCounter counter = new WordCounter(text);

        // Создаем печатальщик результатов
        ResultPrinter printer = new ResultPrinter();

        // Показываем все результаты
        printer.showAll(counter);






    }
}