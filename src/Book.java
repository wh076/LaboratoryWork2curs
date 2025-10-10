import lombok.Data;

@Data //Lombok
public class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;
}