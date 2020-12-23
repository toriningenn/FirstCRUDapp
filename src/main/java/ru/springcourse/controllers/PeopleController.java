package ru.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.dao.PersonDAO;
import ru.springcourse.models.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.SQLException;


@Controller
@RequestMapping("/people")
public class PeopleController {
    //это чтобы мы могли обращаться к personDAO в этом методе
    private final PersonDAO personDAO;

    /*можно писать @Autowired а можно не писать, спринг всё равно сам внедрит зависимость. контроллер всегда будет
        создаваться с этим дао*/
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        //получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    //в URL можно будет вставить любой айди, он будет извлечен и вставлен в параметры метода
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person",personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        //возвращает форму для создания человека, для этого ему нужен пустой объект чтобы посмотреть какие там поля
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) throws SQLException {
        //создастся из данных формы
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        //в самой форме редактирования будут значения этого человека
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
personDAO.update(id,person);
        return "redirect:/people";}

 @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        personDAO.delete(id);
        return "redirect:/people"; }
}
