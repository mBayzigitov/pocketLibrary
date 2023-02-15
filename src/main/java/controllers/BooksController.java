package controllers;

import dao.BooksDAO;
import models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import util.BookValidator;

import javax.naming.Binding;
import javax.validation.Valid;

@RequestMapping("/books")
@Controller
public class BooksController {

    private final BooksDAO booksDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksDAO booksDAO, BookValidator bookValidator) {
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

}
