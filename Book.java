public class Book {
    private int id;
    private String title;
    private String author;
    private boolean borrowed;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrowBook() {
        if (!borrowed) {
            borrowed = true;
            System.out.println("Book \"" + title + "\" has been borrowed.");
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnBook() {
        if (borrowed) {
            borrowed = false;
            System.out.println("Book \"" + title + "\" has been returned.");
        } else {
            System.out.println("Book was not borrowed.");
        }
    }

    public void displayItem() {
        System.out.println("ID: " + id + 
                         " | Title: " + title + 
                         " | Author: " + author + 
                         " | Status: " + (borrowed ? "Borrowed" : "Available"));
    }
}