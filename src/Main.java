import java.sql.*;

public class Main {
    private static final String URL = "jdbc:h2:~/musicdb";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Подключение к базе данных установлено!");

            createMusicTable(conn);

            System.out.println("\nЗадание 1: Все музыкальные композиции");
            getAllMusic(conn);

            System.out.println("\nЗадание 2: Композиции без букв m и t");
            getMusicWithoutMT(conn);

            System.out.println("\nЗадание 3: Добавление любимой композиции");
            addFavoriteMusic(conn, "My Favorite Song");

            System.out.println("\nЗадание 4: Создание таблиц для книг и посетителей");
            createBookTables(conn);
            addSampleBooks(conn);

            System.out.println("\nЗадание 5: Книги отсортированные по год");
            getBooksSortedByYear(conn);

            System.out.println("\nЗадание 6: Книги младше 2000 года");
            getBooksBefore2000(conn);

            System.out.println("\nЗадание 7: Добавление информации о себе");
            addPersonalInfo(conn);

            System.out.println("\nЗадание 8: Удаление таблиц");
            dropBookTables(conn);

            conn.close();
            System.out.println("Программа завершена!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMusicTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS music (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL" +
                ")";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

        String checkSql = "SELECT COUNT(*) FROM music";
        ResultSet rs = stmt.executeQuery(checkSql);
        if (rs.next() && rs.getInt(1) == 0) {
            String[] songs = {
                    "Bohemian Rhapsody", "Stairway to Heaven", "Imagine",
                    "Sweet Child O Mine", "Hey Jude", "Hotel California",
                    "Billie Jean", "Wonderwall", "Smells Like Teen Spirit",
                    "Let It Be", "I Want It All", "November Rain",
                    "Losing My Religion", "One", "With or Without You",
                    "Sweet Caroline", "Yesterday", "Dont Stop Believin",
                    "Crazy Train", "Always"
            };

            String insertSql = "INSERT INTO music (title) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(insertSql);

            for (String song : songs) {
                ps.setString(1, song);
                ps.executeUpdate();
            }
            System.out.println("Таблица music создана и заполнена (20 записей)");
        }
    }

    public static void getAllMusic(Connection conn) throws SQLException {
        String sql = "SELECT * FROM music";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getInt("id") + ": " + rs.getString("title"));
        }
    }

    public static void getMusicWithoutMT(Connection conn) throws SQLException {
        String sql = "SELECT * FROM music WHERE LOWER(title) NOT LIKE '%m%' AND LOWER(title) NOT LIKE '%t%'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getInt("id") + ": " + rs.getString("title"));
        }
    }

    public static void addFavoriteMusic(Connection conn, String songTitle) throws SQLException {
        String sql = "INSERT INTO music (title) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, songTitle);
        ps.executeUpdate();
        System.out.println("Добавлена композиция: " + songTitle);
    }

    public static void createBookTables(Connection conn) throws SQLException {
        String booksSql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "author VARCHAR(255) NOT NULL, " +
                "publishing_year INT, " +
                "isbn VARCHAR(20) UNIQUE, " +
                "publisher VARCHAR(255))";
        Statement stmt = conn.createStatement();
        stmt.execute(booksSql);

        String visitorsSql = "CREATE TABLE IF NOT EXISTS visitors (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "surname VARCHAR(100) NOT NULL, " +
                "phone VARCHAR(20), " +
                "subscribed BOOLEAN)";
        stmt.execute(visitorsSql);

        String favoriteBooksSql = "CREATE TABLE IF NOT EXISTS favorite_books (" +
                "visitor_id INT, " +
                "book_id INT, " +
                "FOREIGN KEY (visitor_id) REFERENCES visitors(id), " +
                "FOREIGN KEY (book_id) REFERENCES books(id), " +
                "PRIMARY KEY (visitor_id, book_id))";
        stmt.execute(favoriteBooksSql);

        System.out.println("Таблицы books, visitors и favorite_books созданы");
    }

    public static void addSampleBooks(Connection conn) throws SQLException {
        String[][] books = {
                {"The Lord of the Rings", "J.R.R. Tolkien", "1954", "0395026468", "Allen & Unwin"},
                {"To Kill a Mockingbird", "Harper Lee", "1960", "0446310759", "HarperPerennial"},
                {"1984", "George Orwell", "1949", "0451534852", "Signet Classics"},
                {"Pride and Prejudice", "Jane Austen", "1813", "0525472125", "Penguin Classics"},
                {"The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "1979", "034539082X", "Del Rey"},
                {"The Great Gatsby", "F. Scott Fitzgerald", "1925", "0743273567", "Scribner"},
                {"Harry Potter and the Philosopher's Stone", "J.K. Rowling", "1997", "0747532735", "Bloomsbury"},
                {"Brave New World", "Aldous Huxley", "1932", "0060860495", "Harper Perennial"},
                {"The Catcher in the Rye", "J.D. Salinger", "1951", "0316769487", "Little, Brown"},
                {"The Hunger Games", "Suzanne Collins", "2008", "0439023483", "Scholastic"}
        };

        String sql = "INSERT INTO books (name, author, publishing_year, isbn, publisher) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        int addedCount = 0;
        for (String[] book : books) {
            String checkSql = "SELECT id FROM books WHERE isbn = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, book[3]);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                ps.setString(1, book[0]);
                ps.setString(2, book[1]);
                ps.setInt(3, Integer.parseInt(book[2]));
                ps.setString(4, book[3]);
                ps.setString(5, book[4]);
                ps.executeUpdate();
                addedCount++;
            }
        }
        System.out.println("Добавлено уникальных книг: " + addedCount);

        addSampleVisitors(conn);
    }

    public static void addSampleVisitors(Connection conn) throws SQLException {
        String[][] visitors = {
                {"John", "Doe", "123-456-7890", "true"},
                {"Jane", "Smith", "987-654-3210", "false"},
                {"Michael", "Johnson", "555-123-4567", "true"}
        };

        String sql = "INSERT INTO visitors (name, surname, phone, subscribed) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (String[] visitor : visitors) {
            ps.setString(1, visitor[0]);
            ps.setString(2, visitor[1]);
            ps.setString(3, visitor[2]);
            ps.setBoolean(4, Boolean.parseBoolean(visitor[3]));
            ps.executeUpdate();
        }
        System.out.println("Добавлены посетители");

        linkVisitorsToBooks(conn);
    }

    public static void linkVisitorsToBooks(Connection conn) throws SQLException {
        String sql = "INSERT INTO favorite_books (visitor_id, book_id) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 1; i <= 3; i++) {
            ps.setInt(1, 1);
            ps.setInt(2, i);
            ps.executeUpdate();
        }

        for (int i = 4; i <= 5; i++) {
            ps.setInt(1, 2);
            ps.setInt(2, i);
            ps.executeUpdate();
        }

        System.out.println("Созданы связи между посетителями и книгами");
    }

    public static void getBooksSortedByYear(Connection conn) throws SQLException {
        String sql = "SELECT name, author, publishing_year, publisher FROM books ORDER BY publishing_year";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Книги отсортированные по году:");
        while (rs.next()) {
            System.out.println(rs.getInt("publishing_year") + ": " +
                    rs.getString("name") + " - " +
                    rs.getString("author") + " (" +
                    rs.getString("publisher") + ")");
        }
    }

    public static void getBooksBefore2000(Connection conn) throws SQLException {
        String sql = "SELECT name, author, publishing_year FROM books WHERE publishing_year < 2000 ORDER BY publishing_year";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Книги младше 2000 года:");
        while (rs.next()) {
            System.out.println(rs.getInt("publishing_year") + ": " +
                    rs.getString("name") + " - " +
                    rs.getString("author"));
        }
    }

    public static void addPersonalInfo(Connection conn) throws SQLException {
        String visitorSql = "INSERT INTO visitors (name, surname, phone, subscribed) VALUES (?, ?, ?, ?)";
        PreparedStatement visitorStmt = conn.prepareStatement(visitorSql, Statement.RETURN_GENERATED_KEYS);
        visitorStmt.setString(1, "Иван");
        visitorStmt.setString(2, "Иванов");
        visitorStmt.setString(3, "555-999-8888");
        visitorStmt.setBoolean(4, true);
        visitorStmt.executeUpdate();

        int visitorId = -1;
        ResultSet rs = visitorStmt.getGeneratedKeys();
        if (rs.next()) {
            visitorId = rs.getInt(1);
        }

        System.out.println("Добавлена информация о себе: Иван Иванов");

        String[][] myBooks = {
                {"Clean Code", "Robert Martin", "2008", "9780132350884", "Prentice Hall"},
                {"Effective Java", "Joshua Bloch", "2018", "9780134686097", "Addison-Wesley"}
        };

        String bookSql = "INSERT INTO books (name, author, publishing_year, isbn, publisher) VALUES (?, ?, ?, ?, ?)";
        String linkSql = "INSERT INTO favorite_books (visitor_id, book_id) VALUES (?, ?)";

        PreparedStatement bookStmt = conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement linkStmt = conn.prepareStatement(linkSql);

        for (String[] book : myBooks) {
            String checkSql = "SELECT id FROM books WHERE isbn = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, book[3]);
            ResultSet checkRs = checkStmt.executeQuery();

            int bookId;
            if (checkRs.next()) {
                bookId = checkRs.getInt("id");
            } else {
                bookStmt.setString(1, book[0]);
                bookStmt.setString(2, book[1]);
                bookStmt.setInt(3, Integer.parseInt(book[2]));
                bookStmt.setString(4, book[3]);
                bookStmt.setString(5, book[4]);
                bookStmt.executeUpdate();

                ResultSet generatedKeys = bookStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bookId = generatedKeys.getInt(1);
                } else {
                    continue;
                }
                System.out.println("Добавлена моя книга: " + book[0]);
            }

            if (visitorId != -1) {
                linkStmt.setInt(1, visitorId);
                linkStmt.setInt(2, bookId);
                linkStmt.executeUpdate();
            }
        }

        System.out.println("Вывод добавленных данных:");
        String selectSql = "SELECT v.name, v.surname, b.name as book_name, b.author " +
                "FROM visitors v " +
                "JOIN favorite_books fb ON v.id = fb.visitor_id " +
                "JOIN books b ON fb.book_id = b.id " +
                "WHERE v.id = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setInt(1, visitorId);
        rs = selectStmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("name") + " " +
                    rs.getString("surname") + ": " +
                    rs.getString("book_name") + " - " +
                    rs.getString("author"));
        }
    }

    public static void dropBookTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DROP TABLE IF EXISTS favorite_books");
        stmt.execute("DROP TABLE IF EXISTS visitors");
        stmt.execute("DROP TABLE IF EXISTS books");
        System.out.println("Таблицы visitors, books и favorite_books удалены");
    }
}