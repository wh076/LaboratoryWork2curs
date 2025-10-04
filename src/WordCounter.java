import java.util.*;

public class WordCounter {
    private String text;
    private HashMap<String, Integer> words;

    /**
     * Конструктор - создает счетчик слов
     * @param text текст для анализа
     */
    public WordCounter(String text) {
        this.text = text;
        this.words = new HashMap<>();
        countWords();
    }

    /**
     * Основной метод подсчета слов
     */
    private void countWords() {
        // Очищаем предыдущие результаты
        words.clear();

        // Удаляем знаки препинания и делаем все буквы маленькими
        String cleanText = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();

        // Разбиваем текст на слова по пробелам
        String[] wordArray = cleanText.split("\\s+");

        // Считаем каждое слово
        for (String word : wordArray) {
            if (!word.isEmpty()) {
                // Если слово уже есть - увеличиваем счетчик
                // Если слова нет - добавляем со счетчиком 1
                words.put(word, words.getOrDefault(word, 0) + 1);
            }
        }
    }

    /**
     * Получить все слова и их количество
     * @return HashMap со словами и количеством
     */
    public HashMap<String, Integer> getAllWords() {
        return new HashMap<>(words);
    }

    /**
     * Получить количество разных слов
     * @return число уникальных слов
     */
    public int getDifferentWordsCount() {
        return words.size();
    }

    /**
     * Найти самое частое слово
     * @return самое частое слово и сколько раз встретилось
     */
    public String getMostFrequentWord() {
        String mostFrequent = "";
        int maxCount = 0;

        for (HashMap.Entry<String, Integer> entry : words.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        return mostFrequent + " (" + maxCount + " раз)";
    }


    /**
     * Получить исходный текст
     * @return исходный текст
     */
    public String getText() {
        return text;
    }
}