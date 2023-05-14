package ru.shulbaeva.springcourse.controllers;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shulbaeva.springcourse.dao.PersonDAO;
import ru.shulbaeva.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    //final - не может быть переопределен в наследниках
    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    //@PathVariable("id")
    //анн. указывающая на то, что данный параметр получается из адресной строки.
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        //возвращаем thymeleaf (html форму) где у нас лежит код  для созд человека
        return "people/new";
    }

    //@ModelAttribute в параметре аннатирует аргумент метода:
    //1. создает нового Person
    //2. добавляет параметры которые пришли из формы в поля объекта с помощью setters
    //3. добавляем в model наш созданный person (чтобы отобразить его на странице)
    //4. если параметров в POST запросе нет, создасться new Person с начальными параметрами
    //   в model положится пустой person с полями по умолчпнию
    @PostMapping()
    //@Valid from Hibernate
    //BindingResult устан после valid класса
    //здесь лежат все ошибки валидациии
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    //@GetMapping("{id:\\d+}") - исп рег выр для того чтобы не спутать стр
    //person/create и /person/{id},

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
