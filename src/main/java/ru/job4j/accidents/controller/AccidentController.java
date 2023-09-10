package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.addAccident(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.getAccident(id));
        return "accidents/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident) {
        accidentService.editAccident(accident);
        return "redirect:/index";
    }
}