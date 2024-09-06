package org.o7planning.springproject.controller;

import org.o7planning.springproject.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
@Controller
public class MainController {
    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("Leo", "Messi"));
        persons.add(new Person("Theo", "Hernandez"));
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model){
        String mess = "Hello Spring Boot + JSP";
        model.addAttribute("mess", mess);
        return "index";
    }

    @RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
    public String viewPersonList(Model model){
        model.addAttribute("persons", persons);
        for(Person p : persons) System.out.println(p.getLastName());
        return "personList";
    }
}
