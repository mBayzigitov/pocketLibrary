package controllers;

import dao.PersonDAO;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/readers")
@Controller
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
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


}
