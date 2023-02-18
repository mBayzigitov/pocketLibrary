package controllers;

import dao.BooksDAO;
import dao.PersonDAO;
import models.Book;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/books")
@Controller
public class BooksController {

    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksDAO booksDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
        this.booksDAO = booksDAO;
    }

    @GetMapping()
    public String booksList(Model model) {
        model.addAttribute("books", booksDAO.getListOfBooks());
        return "books/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String passToDatabase(@ModelAttribute("book") @Valid Book book,
                                 BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksDAO.saveBook(book);

        return "redirect:/books";

    }

    @GetMapping("/{id}/edit")
    public String editReaderProfile(@PathVariable("id") int id, Model model) {
        Optional<Book> bookWithSpecifiedId = booksDAO.findBookById(id);

        if (bookWithSpecifiedId.isPresent()) {
            model.addAttribute("book", bookWithSpecifiedId.get());
            return "books/bookEdit";
        } else {
            return "books/bookNotFound";
        }

    }

    @PatchMapping("/{id}/assign")
    public String giveBook(@PathVariable("id") int book_id, @ModelAttribute("newReader") Person newReader) {
        System.out.println("book_id : " + book_id + "; client_id : " + newReader.getClientId());
        booksDAO.assignBookToPerson(book_id, newReader.getClientId());
        return "redirect:/books/" + book_id;
    }

    @DeleteMapping("/{id}")
    public String removeBook(@PathVariable("id") int book_id) {
        booksDAO.removeBook(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int book_id) {
        System.out.println(book_id);
        booksDAO.releaseBook(book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult,
                               @PathVariable("id") int book_id) {

        book.setBook_id(book_id);
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/bookEdit";
        }

        booksDAO.updateBook(book_id, book);

        return "redirect:/books";

    }

    @GetMapping("/{id}")
    public String showBookInfo(Model model, @PathVariable("id") int book_id,
                               @ModelAttribute("newReader") Person newReader) {

        Optional<Book> isThereBookWithSpecifiedId = booksDAO.findBookById(book_id);

        if (!isThereBookWithSpecifiedId.isPresent()) {
            return "books/bookNotFound";
        }

        Optional<Person> owner = booksDAO.clientByBookId(book_id);

        if (owner.isPresent())
            model.addAttribute("owner", owner.get());
        else
            model.addAttribute("people", personDAO.getListOfReaders());

        model.addAttribute("book", isThereBookWithSpecifiedId.get());

        return "books/showBook";

    }

}
