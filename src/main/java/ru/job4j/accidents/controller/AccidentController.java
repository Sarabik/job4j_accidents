package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        model.addAttribute("rules", ruleService.getAllRules());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ruleIds = req.getParameterValues("rIds");
        accidentService.addAccident(
                accidentService.addRulesAndAccidentType(accident, ruleIds));
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
        Optional<Accident> optional = accidentService.getAccident(id);
        if (optional.isEmpty()) {
            model.addAttribute("message", "Accident is not found");
            return "errors/404";
        }
        model.addAttribute("accident", accidentService.getAccident(id).get());
        model.addAttribute("rules", ruleService.getAllRules());
        return "accidents/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
        String[] ruleIds = req.getParameterValues("rIds");
        boolean changed = accidentService.editAccident(
                accidentService.addRulesAndAccidentType(accident, ruleIds));
        if (!changed) {
            model.addAttribute("message", "Accident is not found");
            return "errors/404";
        }
        return "redirect:/index";
    }
}