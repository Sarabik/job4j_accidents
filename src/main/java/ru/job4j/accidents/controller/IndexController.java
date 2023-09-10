package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "/index"})
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("user", "Lidija");
        accidentService.addAccident(new Accident(
                1, "Accident 1", "Accident description 1", "Address 1"));
        accidentService.addAccident(new Accident(
                2, "Accident 2", "Accident description 2", "Address 2"));
        accidentService.addAccident(new Accident(
                3, "Accident 3", "Accident description 3", "Address 3"));
        model.addAttribute("accidents", accidentService.getAllAccidents());
        return "index";
    }
}
