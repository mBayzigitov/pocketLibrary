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

}
