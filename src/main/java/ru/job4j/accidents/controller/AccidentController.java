package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        addSomeAccidentTypes();
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(typeId));
        accidentService.addAccident(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        model.addAttribute("accident", accidentService.getAccident(id));
        return "accidents/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident) {
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(typeId));
        accidentService.editAccident(accident);
        return "redirect:/index";
    }

    private void addSomeAccidentTypes() {
        accidentTypeService.addAccidentType(new AccidentType(1, "Two cars"));
        accidentTypeService.addAccidentType(new AccidentType(2, "Car and person"));
        accidentTypeService.addAccidentType(new AccidentType(3, "Car and bicycle"));
    }
}