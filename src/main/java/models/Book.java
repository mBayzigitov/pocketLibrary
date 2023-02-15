package models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;

    private int client_id;

    @NotEmpty
    @Size(max = 60, message = "Название слишком длинное")
    private String title;

    @NotEmpty
    @Size(max = 100, message = "Имя автора слишком длинное")
    private String author;

    @Max(value = 2024, message = "Введите корректную дату")
    private int year_of_creation;

    public Book() {}

    public Book(int book_id, int client_id, String title, String author, int year_of_creation) {
        this.book_id = book_id;
        this.client_id = client_id;
        this.title = title;
        this.author = author;
        this.year_of_creation = year_of_creation;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_of_creation() {
        return year_of_creation;
    }

    public void setYear_of_creation(int year_of_creation) {
        this.year_of_creation = year_of_creation;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", client_id=" + client_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year_of_creation=" + year_of_creation +
                '}';
    }
}
