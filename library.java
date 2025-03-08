import java.io.*;
import java.util.*;
import java.util.Optional;

public class library {
    private static List<Book> books = new ArrayList<>();
    private static final String FILENAME = "books.txt";

    public static void main(String[] args) {
        loadBooks();
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Library System ---");
            System.out.println("1. View Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Add New Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1: viewBooks(); break;
                case 2: borrowBook(scanner); break;
                case 3: returnBook(scanner); break;
                case 4: addBook(scanner); break;
                case 5: deleteBook(scanner); break;
                case 6: saveBooks(); System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        int newId = generateNewId();
        Book newBook = new Book(newId, title, author);
        books.add(newBook);
        System.out.println("Book added! ID: " + newId);
    }

    private static void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        boolean removed = false;
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Book ID " + id + " deleted.");
        } else {
            System.out.println("Book not found!");
        }
    }

    private static int generateNewId() {
        if (books.isEmpty()) return 1;
        return books.get(books.size() - 1).getId() + 1;
    }

    private static void loadBooks() {
        File file = new File(FILENAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 4) {
                    int id = Integer.parseInt(data[0].trim());
                    String title = data[1].trim();
                    String author = data[2].trim();
                    boolean borrowed = Boolean.parseBoolean(data[3].trim());

                    Book book = new Book(id, title, author);
                    if (borrowed) book.borrowBook();
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    private static void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Book book : books) {
                writer.write(book.getId() + " | " +
                            book.getTitle() + " | " +
                            book.getAuthor() + " | " +
                            book.isBorrowed());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books found!");
        } else {
            for (Book book : books)
                book.displayItem();
        }
    } // ← Missing closing brace added here

    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter book ID to borrow: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        findBookById(id).ifPresent(Book::borrowBook);
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter book ID to return: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        findBookById(id).ifPresent(Book::returnBook);
    }

    private static Optional<Book> findBookById(int id) {
        return books.stream()
                    .filter(book -> book.getId() == id)
                    .findFirst();
    }
}