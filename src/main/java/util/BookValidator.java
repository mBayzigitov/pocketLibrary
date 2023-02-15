package util;

import dao.BooksDAO;
import models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class BookValidator implements Validator {

    @Autowired
    public BookValidator(BooksDAO booksDao) {
        this.booksDAO = booksDao;
    }

    private final BooksDAO booksDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Book book = (Book) o;

        Optional<Book> comparingBook = booksDAO.findBookByTitle(book.getTitle());

        // check if there is a book in database with similar name and author
        if (comparingBook.isPresent()) {

            if (comparingBook.get().getAuthor().equals(book.getAuthor())) {
                if (comparingBook.get().getBook_id() == book.getBook_id()) {
                    return;
                }
                errors.rejectValue("title", "", "Такая книга уже существует");
            }

        }

    }

}
