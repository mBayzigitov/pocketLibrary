package dao;

import models.Book;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getListOfBooks() {
        return jdbcTemplate.query("SELECT book_id, title, author, year_of_creation FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year_of_creation) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear_of_creation());
    }

    public Optional<Book> findBookByTitle(String title) {
        return jdbcTemplate.query("SELECT book_id, title, author, year_of_creation FROM Book WHERE title = ?", new Object[] {title},
                new BeanPropertyRowMapper<>(Book.class))
                    .stream().findAny();
    }

    public void updateBook(int book_id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year_of_creation = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getYear_of_creation(), book_id);
    }

    public Optional<Book> findBookById(int id) {
        return jdbcTemplate.query("SELECT title, author, year_of_creation FROM Book WHERE book_id = ?", new Object[] {id},
                new BeanPropertyRowMapper<>(Book.class))
                    .stream().findAny();
    }

    public Optional<Person> clientByBookId(int book_id) {
        return jdbcTemplate.query("SELECT * FROM Person JOIN book on person.client_id = book.client_id " +
                                "WHERE book_id = ?", new Object[] {book_id},
                new BeanPropertyRowMapper<>(Person.class))
                    .stream().findAny();
    }

    public List<Book> getListOfBooksOfSpecifiedReader(int reader_id) {
        return jdbcTemplate.query("SELECT book_id, title, author, year_of_creation FROM Book WHERE client_id = ?",
                new Object[] {reader_id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void assignBookToPerson(int book_id, int client_id) {
        jdbcTemplate.query("UPDATE Book SET client_id = ? WHERE book_id = ?",
                new Object[] {book_id, client_id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void freeBook(int book_id) {
        jdbcTemplate.query("UPDATE Book SET client_id = null WHERE book_id = ?",
                new Object[] {book_id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void deleteBook(int book_id) {
        jdbcTemplate.query("DELETE FROM Book WHERE book_id = ?",
                new Object[] {book_id}, new BeanPropertyRowMapper<>(Book.class));
    }

}
