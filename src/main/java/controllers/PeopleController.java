package controllers;

import dao.BooksDAO;
import dao.PersonDAO;
import models.Book;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/readers")
@Controller
public class PeopleController {

    private final PersonDAO personDAO;
    private final BooksDAO booksDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String readersList(Model model) {
        model.addAttribute("people", personDAO.getListOfReaders());
        return "readers/readers";
    }

    @GetMapping("/new")
    public String createReader(@ModelAttribute("person") Person person) {
        return "readers/new";
    }

    @PostMapping("")
    public String passToDatabase(@ModelAttribute("person") @Valid Person person,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "readers/new";
        }

        personDAO.saveReader(person);

        return "redirect:/readers";

    }

    @GetMapping("/{id}/edit")
    public String editReaderProfile(@PathVariable("id") int id, Model model) {
        Optional<Person> personWithSpecifiedId = personDAO.findPersonById(id);

        if (personWithSpecifiedId.isPresent()) {
            model.addAttribute("person", personWithSpecifiedId.get());
            return "readers/readerEdit";
        } else {
            return "readers/readerNotFound";
        }

    }

    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "readers/readerEdit";
        }

        personDAO.updatePerson(id, person);

        return "redirect:/readers";

    }

    @GetMapping("/{id}")
    public String showReader(Model model, @PathVariable("id") int id) {
        Optional<Person> isTherePersonWithSpecifiedId = personDAO.findPersonById(id);
        List<Book> listOfReadersBooks = booksDAO.getListOfBooksOfSpecifiedReader(id);

        if (!isTherePersonWithSpecifiedId.isPresent()) {
            return "readers/readerNotFound";
        }

        boolean isListEmpty = (listOfReadersBooks.size() == 0);

        model.addAttribute("reader", isTherePersonWithSpecifiedId.get());
        model.addAttribute("hisBooks", listOfReadersBooks);
        model.addAttribute("isListEmpty", isListEmpty);

        return "readers/showReader";

    }

    @DeleteMapping("/{id}")
    public String removePerson(@PathVariable("id") int client_id) {
        personDAO.removePerson(client_id);
        return "redirect:/readers";
    }


}
