package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "/index"})
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", accidentService.getAllAccidents());
        return "index";
    }
}
