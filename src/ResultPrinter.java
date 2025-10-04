import java.util.HashMap;
import java.util.List;

public class ResultPrinter {

    /**
     * Показать заголовок и исходный текст
     * @param counter счетчик слов
     */
    public void showHeader(WordCounter counter) {
        System.out.println(counter.getText());
        System.out.println();
        System.out.println(" ");
        System.out.println();
    }

    /**
     * Показать все слова и их количество
     * @param counter счетчик слов
     */
    public void showWords(WordCounter counter) {
        System.out.println("СЛОВА И ИХ КОЛИЧЕСТВО:");


        HashMap<String, Integer> allWords = counter.getAllWords();

        for (HashMap.Entry<String, Integer> entry : allWords.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " раз");
        }
        System.out.println();
    }

    /**
     * Показать статистику
     * @param counter счетчик слов
     */
    public void showStats(WordCounter counter) {
        System.out.println("СТАТИСТИКА:");

        System.out.println("Всего разных слов: " + counter.getDifferentWordsCount());
        System.out.println("Самое частое слово: " + counter.getMostFrequentWord());
        System.out.println();
    }



    /**
     * Показать все результаты
     * @param counter счетчик слов
     */
    public void showAll(WordCounter counter) {
        showHeader(counter);
        showWords(counter);
        showStats(counter);

    }
}